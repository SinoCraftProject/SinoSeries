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
    private final BooleanSupplier drawable;

    private int color = 0;
    private Drawing drawing;

    public CanvasWidget(BrushScreen screen, int x, int y, int width, int height, BooleanSupplier drawable) {
        super(x, y, width, height, Component.empty());
        this.screen = screen;
        this.drawing = new Drawing();
        this.drawable = drawable;
    }

    public void setHeight(int height) {
        this.height = height;
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

        var pixelW = (int)Math.floor(160.0F / Math.max(1, drawing.getWidth()));
        var pixelH = (int)Math.floor(160.0F / Math.max(1, drawing.getHeight()));
        if (!drawing.isEmpty()) {
            try (var ignored = GLSwitcher.blend().enable()) {
                for (var i = 0; i < drawing.getWidth(); i++) {
                    for (var j = 0; j < drawing.getHeight(); j++) {
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
    private boolean leftMouseButton = false;
    private boolean rightMouseButton = false;
    private boolean altPressed = false;

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        var result = super.mouseClicked(mouseX, mouseY, button);

        if (result) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                leftMouseButton = true;
            }

            if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                rightMouseButton = true;
            }

            if (drawable.getAsBoolean()) {
                if (leftMouseButton) {
                    if (altPressed) {
                        selectColor(pickColor(mouseX, mouseY));
                    } else {
                        setPixel(mouseX, mouseY, color);
                    }
                } else if (rightMouseButton) {
                    setPixel(mouseX, mouseY, 0);
                }
            }
        }

        return result;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        var result = super.mouseReleased(mouseX, mouseY, button);

        if (result) {
            isDragging = false;

            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                leftMouseButton = false;
            }

            if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                rightMouseButton = false;
            }
        }

        return result;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        var result = super.mouseDragged(mouseX, mouseY, button, dragX, dragY);

        if (result) {
            if (drawable.getAsBoolean()) {
                if (isMouseOver(mouseX, mouseY)) {
                    isDragging = true;
                }
            }
        }

        return result;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);

        if (isMouseOver(mouseX, mouseY) && isDragging) {
            if (leftMouseButton) {
                setPixel(mouseX, mouseY, color);
            } else if (rightMouseButton) {
                setPixel(mouseX, mouseY, 0);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        // Todo: qyl27: check it.

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX) {
        if (isMouseOver(mouseX, mouseY) && scrollX != 0) {
            var color = getColor();
            if (scrollX > 0) {
                color += 1;
            } else if (scrollX < 0) {
                color -= 1;
            }

            color += 16;
            color %= 16;
            color = Math.max(1, color);

            selectColor(color);
            return true;
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX);
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

        System.out.println("i: " + i + ", j: " + j);
        getDrawing().setPixel(i, j, (byte) color);
    }

    private byte pickColor(double mouseX, double mouseY) {
        var pixelW = 160.0 / Math.max(1, drawing.getWidth());
        var pixelH = 160.0 / Math.max(1, drawing.getHeight());

        var i = (int) Math.floor((mouseX - getX()) / pixelW);
        var j = (int) Math.floor((mouseY - getY()) / pixelH);

        System.out.println("i: " + i + ", j: " + j);
        return getDrawing().getPixel(i, j);
    }
}
