package games.moegirl.sinocraft.sinocore.gui.widgets.component;

import games.moegirl.sinocraft.sinocore.gui.widgets.Widgets;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.ProgressEntry;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.TextureEntry;
import games.moegirl.sinocraft.sinocore.utility.GLSwitcher;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.DoubleSupplier;

public class ProgressWidget extends AbstractWidget {

    private final DoubleSupplier progressSupplier;
    private final ProgressEntry entry;
    private final Widgets widgets;

    public ProgressWidget(int leftPos, int topPos, Widgets widgets, ProgressEntry entry, DoubleSupplier progress) {
        super(leftPos + entry.getX(), topPos + entry.getY(), entry.getWidth(), entry.getHeight(), Component.empty());
        this.progressSupplier = progress;
        this.entry = entry;
        this.widgets = widgets;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        GLSwitcher b = GLSwitcher.blend();
        if (entry.getTexture().isPresent() && widgets.containsWidget(entry.getTexture().get())) {
            TextureEntry texture = (TextureEntry) widgets.getWidget(entry.getTexture().get());
            guiGraphics.blit(widgets.getTexture(), getX(), getY(), getWidth(), getHeight(),
                    texture.getTextureX(), texture.getTextureY(), texture.getTextureWidth(), texture.getTextureHeight(),
                    widgets.getWidth(), widgets.getHeight());
        }
        double progress = getProgress();
        if (progress > 0) {
            TextureEntry p = (TextureEntry) widgets.getWidget(entry.getTextureFilled());
            int x = getX(), y = getY();
            int w = p.getWidth(), h = p.getHeight();
            double tu = p.getTextureX(), tv = p.getTextureY();
            int tw = p.getTextureWidth(), th = p.getTextureHeight();
            if (entry.isVertical()) {
                if (entry.isOpposite()) {
                    tv += (1 - progress) * p.getTextureHeight();
                    y += (int) ((1 - progress) * p.getY());
                }
                th = (int) (th * progress);
                h = (int) (h * progress);
            } else {
                if (entry.isOpposite()) {
                    tu += (1 - progress) * p.getTextureWidth();
                    x += (int) ((1 - progress) * p.getWidth());
                }
                tw = (int) (tw * progress);
                w = (int) (w * progress);
            }
            guiGraphics.blit(widgets.getTexture(), x, y, w, h, (float) tu, (float) tv, tw, th, widgets.getWidth(), widgets.getHeight());
        }
        b.disable();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, createNarrationMessage());
    }

    public double getProgress() {
        return progressSupplier.getAsDouble();
    }
}
