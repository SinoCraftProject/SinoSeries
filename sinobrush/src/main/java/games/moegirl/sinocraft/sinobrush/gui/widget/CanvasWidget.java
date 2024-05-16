package games.moegirl.sinocraft.sinobrush.gui.widget;

import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinocore.utility.GLSwitcher;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.BooleanSupplier;

public class CanvasWidget extends AbstractWidget {

    private final BooleanSupplier canDrawable;

    private int color = 0;
    private Drawing drawing;

    public CanvasWidget(int x, int y, int width, int height, int paperColor, int inkColor, BooleanSupplier canDrawable) {
        super(x, y, width, height, Component.empty());
        this.drawing = new Drawing();
        this.canDrawable = canDrawable;

        drawing.setWidth(width);
        drawing.setHeight(height);
        drawing.setInkColor(inkColor);
        drawing.setPaperColor(paperColor);
    }

    public void setColor(int color) {
        this.color = color;
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
        if (!drawing.isEmpty()) {
            try (GLSwitcher ignored = GLSwitcher.blend().enable()) {
                for (int i = 0; i < drawing.getWidth(); i++) {
                    for (int j = 0; j < drawing.getHeight(); j++) {
                        int x = i + getX();
                        int y = i + getY();
                        guiGraphics.fill(x, y, x + 1, y + 1, drawing.getPixel(i, j));
                    }
                }
            }
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (canDrawable.getAsBoolean()) {
            drawing.setPixel((int) mouseX - getX(), (int) mouseY - getY(), (byte) color);
        }
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        if (canDrawable.getAsBoolean()) {
            drawing.setPixel((int) mouseX - getX(), (int) mouseY - getY(), (byte) color);
        }
    }
}
