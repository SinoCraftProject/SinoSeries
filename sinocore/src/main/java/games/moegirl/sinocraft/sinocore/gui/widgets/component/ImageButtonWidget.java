package games.moegirl.sinocraft.sinocore.gui.widgets.component;

import games.moegirl.sinocraft.sinocore.gui.widgets.entry.ButtonEntry;
import games.moegirl.sinocraft.sinocore.gui.WidgetScreenBase;
import games.moegirl.sinocraft.sinocore.util.GLSwitcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ImageButtonWidget extends ImageButton {
    private final WidgetScreenBase<?> widgetScreenBase;
    private final ButtonEntry entry;

    @Nullable
    private OnPress onLeftClick, onRightClick;

    public ImageButtonWidget(WidgetScreenBase<?> screen, ButtonEntry entry, WidgetSprites sprites, OnPress onPress, Component tooltip) {
        super(entry.getX() + screen.getLeftPos(), entry.getY() + screen.getTopPos(), entry.getWidth(), entry.getHeight(), sprites, onPress, tooltip);
        this.widgetScreenBase = screen;
        this.entry = entry;

        this.onLeftClick = onPress;
        this.onRightClick = null;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!visible) return;
        List<String> textures;
        if (isHovered) {
            guiGraphics.renderTooltip(widgetScreenBase.getFont(), getMessage(), mouseX, mouseY);
            MouseHandler mouse = Minecraft.getInstance().mouseHandler;
            if (mouse.isLeftPressed() || mouse.isMiddlePressed() || mouse.isRightPressed()) {
                textures = entry.getTexturePressed();
            } else {
                textures = entry.getTextureHover();
            }
        } else if (active) {
            textures = entry.getTexture();
        } else {
            textures = entry.getTextureDisable();
        }
        GLSwitcher d = GLSwitcher.depth().enable();
        GLSwitcher b = GLSwitcher.blend().enable();
        for (String texture : textures) {
            widgetScreenBase.blitTexture(guiGraphics, texture, getX(), getY(), width, height);
        }
        d.resume();
        b.resume();
    }

    @Override
    public void onPress() {
        if (onLeftClick != null)
            onLeftClick.onPress(this);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.active && this.visible && !this.isValidClickButton(button)) {
            if (onRightClick != null && isMouseOver(mouseX, mouseY)) {
                onRightClick.onPress(this);
                this.playDownSound(Minecraft.getInstance().getSoundManager());
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void setOnRightClick(@Nullable OnPress onRightClick) {
        this.onRightClick = onRightClick;
    }

    public void setOnLeftClick(@Nullable OnPress onLeftClick) {
        this.onLeftClick = onLeftClick;
    }
}
