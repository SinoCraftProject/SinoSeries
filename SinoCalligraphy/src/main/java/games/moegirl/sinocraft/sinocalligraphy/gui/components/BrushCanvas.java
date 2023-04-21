package games.moegirl.sinocraft.sinocalligraphy.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.PaperType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.DrawingDataVersion;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.ISimpleDrawing;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.traits.IHasInkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.traits.IHasPaperType;
import games.moegirl.sinocraft.sinocalligraphy.gui.screen.BrushScreen;
import games.moegirl.sinocraft.sinocore.client.GLSwitcher;
import games.moegirl.sinocraft.sinocore.client.TextureMapClient;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class BrushCanvas extends AbstractWidget {
    private AbstractContainerScreen<?> parent;
    private TextureMapClient atlas;
    private IntSupplier getColor;
    private IntConsumer setColor;

    private ISimpleDrawing drawing;

    private int canvasWidth = 128;
    private int canvasHeight = 128;

    private boolean enabled = false;

    public BrushCanvas(AbstractContainerScreen<?> parent, TextureMapClient atlas,
                       int x, int y, int width, int height,
                       IntSupplier getColor, IntConsumer setColor,
                       PaperType paperType, InkType inkType) {
        super(x, y, width, height, Component.empty());

        this.canvasWidth = width - 2;
        this.canvasHeight = height - 2;

        this.parent = parent;
        this.atlas = atlas;
        this.getColor = getColor;
        this.setColor = setColor;

        drawing = DrawingDataVersion.getLatest().create();

        if (drawing instanceof IHasPaperType hasPaperType) {
            hasPaperType.setPaperType(paperType);
        }

        if (drawing instanceof IHasInkType hasInkType) {
            hasInkType.setInkType(inkType);
        }
    }

    @Override
    public void renderWidget(PoseStack poseStack, int x, int y, float partialTick) {
        atlas.blitTexture(poseStack, "canvas", parent);
        drawing.getRenderer().draw(poseStack, getX() + 1, getY() + 1, canvasWidth, canvasHeight);
        if (!isEnabled()) {
            atlas.blitTexture(poseStack, "shadow", parent, GLSwitcher.blend().enable());
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, Component.translatable(SCAConstants.NARRATION_BRUSH_CANVAS));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
//        this.visible = enabled;
    }

    public void clear() {
        drawing.setPixels(new byte[drawing.getSize() * drawing.getSize()]);
    }

    public ISimpleDrawing getDrawing() {
        return drawing;
    }

    /// <editor-fold desc="Handle input">

    private boolean isDragging = false;
    private int draggingMouseButton = -1;
    private boolean isAltPressed = false;

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return super.isMouseOver(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            if (isValidClickButton(button)) {
                drawColor(mouseX, mouseY);
                return true;
            } else {
                if (isAltPressed) {
                    selectColor(mouseX, mouseY);
                } else {
                    removeColor(mouseX, mouseY);
                }
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isMouseOver(mouseX, mouseY)) {
            isDragging = true;
            draggingMouseButton = button;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (isMouseOver(mouseX, mouseY) && isDragging) {
            if (isValidClickButton(draggingMouseButton)) {
                drawColor(mouseX, mouseY);
            } else {
                if (isAltPressed) {
                    selectColor(mouseX, mouseY);
                } else {
                    removeColor(mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        draggingMouseButton = -1;
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT_ALT || keyCode == GLFW.GLFW_KEY_RIGHT_ALT) {
            isAltPressed = true;
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT_ALT || keyCode == GLFW.GLFW_KEY_RIGHT_ALT) {
            isAltPressed = false;
            return true;
        }

        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    /// </editor-fold>

    /// <editor-fold desc="Draw color">
    private void drawColor(double mouseX, double mouseY) {
        if (isEnabled()) {
            drawing.getPixels()[getPointIndex(mouseX, mouseY)] = (byte) getColor.getAsInt();
        }
    }

    private void selectColor(double mouseX, double mouseY) {
        if (isEnabled() && isAltPressed) {
            setColor.accept(drawing.getPixels()[getPointIndex(mouseX, mouseY)]);
            if (parent instanceof BrushScreen gui) {
                var list = gui.getColorSelection();
                list.setSelectedItem(list.getEntry(getColor.getAsInt()));
            }
        }
    }

    private void removeColor(double mouseX, double mouseY) {
        if (isEnabled()) {
            drawing.getPixels()[getPointIndex(mouseX, mouseY)] = (byte) 0;
        }
    }

    private int getPointIndex(double mouseX, double mouseY) {
        int cellWidth = canvasWidth / drawing.getSize();
        int cellHeight = canvasHeight / drawing.getSize();
        int x = Mth.clamp((int) ((Math.round(mouseX) - getX()) / cellWidth), 0, drawing.getSize() - 1);
        int y = Mth.clamp((int) ((Math.round(mouseY) - getY()) / cellHeight), 0, drawing.getSize() - 1);
        return x * drawing.getSize() + y;
    }
    /// </editor-fold>
}
