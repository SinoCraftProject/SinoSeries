package games.moegirl.sinocraft.sinobrush.gui.widget;

import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinobrush.gui.screen.BrushScreen;
import games.moegirl.sinocraft.sinobrush.client.DrawingRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.function.BooleanSupplier;

public class CanvasWidget extends AbstractWidget {

    private final BrushScreen screen;
    private final BooleanSupplier drawable;

    private Drawing drawing;

    public CanvasWidget(BrushScreen screen, int x, int y, int width, int height, BooleanSupplier drawable) {
        super(x, y, width, height, Component.empty());
        this.screen = screen;
        this.drawing = new Drawing();
        this.drawable = drawable;
    }

    public Drawing getDrawing() {
        return drawing;
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (drawable.getAsBoolean()) {
            DrawingRenderer.renderInGui(guiGraphics, getX(), getY(), getWidth(), getHeight(), getDrawing(), partialTick);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    private boolean isDragging = false;
    private boolean leftMouseButton = false;
    private boolean rightMouseButton = false;
    private boolean altPressed = false;

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            leftMouseButton = true;
        }

        if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            rightMouseButton = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;

        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            leftMouseButton = false;
        }

        if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            rightMouseButton = false;
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);

        if (isDragging) {
            if (leftMouseButton) {
                setPixel(mouseX, mouseY, screen.getSelectedColor());
            } else if (rightMouseButton) {
                setPixel(mouseX, mouseY, 0);
            }
        }
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        super.onDrag(mouseX, mouseY, dragX, dragY);

        if (drawable.getAsBoolean() && !isDragging) {
            isDragging = true;
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);

        if (drawable.getAsBoolean()) {
            if (leftMouseButton) {
                if (altPressed) {
                    screen.selectColor(pickColor(mouseX, mouseY));
                } else {
                    setPixel(mouseX, mouseY, screen.getSelectedColor());
                }
            } else if (rightMouseButton) {
                setPixel(mouseX, mouseY, 0);
            }
        }
    }

    @Override
    protected boolean isValidClickButton(int button) {
        return button == GLFW.GLFW_MOUSE_BUTTON_LEFT || button == GLFW.GLFW_MOUSE_BUTTON_RIGHT;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT_ALT || keyCode == GLFW.GLFW_KEY_RIGHT_ALT) {
            altPressed = true;
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT_ALT || keyCode == GLFW.GLFW_KEY_RIGHT_ALT) {
            altPressed = false;
            return true;
        }

        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    private void setPixel(double mouseX, double mouseY, int color) {
        var pixelW = 160.0 / Math.max(1, drawing.getWidth());
        var pixelH = 160.0 / Math.max(1, drawing.getHeight());

        var i = (int) Math.floor((mouseX - getX()) / pixelW);
        var j = (int) Math.floor((mouseY - getY()) / pixelH);
        getDrawing().setPixel(i, j, (byte) color);
    }

    private byte pickColor(double mouseX, double mouseY) {
        var pixelW = 160.0 / Math.max(1, drawing.getWidth());
        var pixelH = 160.0 / Math.max(1, drawing.getHeight());

        var i = (int) Math.floor((mouseX - getX()) / pixelW);
        var j = (int) Math.floor((mouseY - getY()) / pixelH);
        return getDrawing().getPixel(i, j);
    }
}
