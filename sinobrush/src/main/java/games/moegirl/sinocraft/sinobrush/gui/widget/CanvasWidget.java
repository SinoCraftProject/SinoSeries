package games.moegirl.sinocraft.sinobrush.gui.widget;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinobrush.gui.screen.BrushScreen;
import games.moegirl.sinocraft.sinobrush.utility.DrawingHelper;
import games.moegirl.sinocraft.sinocore.utility.GLSwitcher;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.function.BooleanSupplier;

public class CanvasWidget extends AbstractWidget {

    private final BrushScreen screen;
    private final BooleanSupplier canDrawable;

    private int color = 0;
    private Drawing drawing;

    public CanvasWidget(BrushScreen screen, int x, int y, int width, int height, BooleanSupplier drawable) {
        super(x, y, width, height, Component.empty());
        this.screen = screen;
        this.drawing = new Drawing();
        this.canDrawable = drawable;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void selectColor(int color) {
        screen.selectColor(color);
    }

    public Drawing getDrawing() {
        return drawing;
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(getX(), getY(), getX() + width, getY() + height, drawing.getPaperColor());

        var pixelW = (int)Math.floor(160.0F / Math.min(1, drawing.getWidth()));
        var pixelH = (int)Math.floor(160.0F / Math.min(1, drawing.getHeight()));
        if (!drawing.isEmpty()) {
            try (GLSwitcher ignored = GLSwitcher.blend().enable()) {
                for (int i = 0; i < drawing.getWidth(); i++) {
                    for (int j = 0; j < drawing.getHeight(); j++) {
                        var x = getX() + (i * pixelW);
                        var y = getY() + (j * pixelH);
                        var color = drawing.getPixel(i, j);
                        fillPixel(guiGraphics, x, y, x + pixelW, y + pixelH, DrawingHelper.pixelColorToARGB(color, drawing.getInkColor()));
                    }
                }
            }
        }
    }

    private void fillPixel(GuiGraphics graphics, int minX, int minY, int maxX, int maxY, int color) {
        var matrix4f = graphics.pose().last().pose();
        if (minX < maxX) {
            var temp = minX;
            minX = maxX;
            maxX = temp;
        }
        if (minY < maxY) {
            var temp = minY;
            minY = maxY;
            maxY = temp;
        }
        var buffer = graphics.bufferSource().getBuffer(RenderType.guiOverlay());
        buffer.vertex(matrix4f, minX, minY, 0).color(color).endVertex();
        buffer.vertex(matrix4f, minX, maxY, 0).color(color).endVertex();
        buffer.vertex(matrix4f, maxX, maxY, 0).color(color).endVertex();
        buffer.vertex(matrix4f, maxX, minY, 0).color(color).endVertex();
        graphics.flush();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    private boolean isDragging = false;
    private int mouseButton = 0;       // qyl27: 0 for none, 1 for left, 2 for right, 3 for both, ignore others.
    private boolean altPressed = false;

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            mouseButton |= 1;
        } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            mouseButton |= 2;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            mouseButton &= 1;
        } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            mouseButton &= 2;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (canDrawable.getAsBoolean()) {
            if ((mouseButton & 1) != 0) {
                if (altPressed) {
                    selectColor(pickColor(mouseX, mouseY));
                } else {
                    setPixel(mouseX, mouseY, color);
                }
            } else if ((mouseButton & 2) != 0) {
                setPixel(mouseX, mouseY, 0);
            }
        }
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        if (canDrawable.getAsBoolean()) {
            if (isMouseOver(mouseX, mouseY)) {
                isDragging = true;
            }
        }
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);

        if (isMouseOver(mouseX, mouseY) && isDragging) {
            if ((mouseButton & 1) != 0) {
                setPixel(mouseX, mouseY, color);
            } else if ((mouseButton & 2) != 0) {
                setPixel(mouseX, mouseY, 0);
            }
        }
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.onRelease(mouseX, mouseY);
        isDragging = false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (isMouseOver(mouseX, mouseY) && scrollX != 0) {
            var color = getColor();
            if (scrollX > 0) {
                color += 1;
            } else if (scrollX < 0) {
                color -= 1;
            }

            color += 16;
            color %= 16;
            color = Math.min(SBRConstants.DRAWING_COLOR_MAX - 1, Math.max(SBRConstants.DRAWING_COLOR_MIN, color));

            selectColor(color);
            return true;
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
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
        var pixelW = 160.0 / Math.min(1, drawing.getWidth());
        var pixelH = 160.0 / Math.min(1, drawing.getHeight());

        var i = (int) Math.floor((mouseX - getX()) / pixelW);
        var j = (int) Math.floor((mouseY - getY()) / pixelH);

        getDrawing().setPixel(i, j, (byte) color);
    }

    private byte pickColor(double mouseX, double mouseY) {
        var pixelW = 160.0 / Math.min(1, drawing.getWidth());
        var pixelH = 160.0 / Math.min(1, drawing.getHeight());

        var i = (int) Math.floor((mouseX - getX()) / pixelW);
        var j = (int) Math.floor((mouseY - getY()) / pixelH);

        return getDrawing().getPixel(i, j);
    }
}
