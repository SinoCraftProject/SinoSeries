package games.moegirl.sinocraft.sinocore.gui.widgets.component;

import games.moegirl.sinocraft.sinocore.gui.AbstractWidgetScreen;
import games.moegirl.sinocraft.sinocore.gui.widgets.Widgets;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.ButtonEntry;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.TextureEntry;
import games.moegirl.sinocraft.sinocore.utility.GLSwitcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ImageButtonWidget extends Button {

    private final ButtonEntry entry;
    private final Font font;
    private final Widgets widgets;
    private final boolean[] clickedButton = new boolean[]{false, false, false};

    @Nullable
    private OnPress onLeftClick, onRightClick;

    public ImageButtonWidget(AbstractWidgetScreen<?> screen, ButtonEntry entry, OnPress onPress, Component tooltip) {
        super(entry.getX() + screen.getLeftPos(), entry.getY() + screen.getTopPos(),
                entry.getWidth(), entry.getHeight(), tooltip, onPress, DEFAULT_NARRATION);
        this.entry = entry;
        this.font = screen.getFont();
        this.widgets = screen.getWidgets();
        this.onLeftClick = onPress;
        this.onRightClick = null;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!visible) return;
        List<String> textures;
        if (active) {
            if (isHovered) {
                if (!getMessage().getString().isBlank()) {
                    guiGraphics.renderTooltip(font, getMessage(), mouseX, mouseY);
                }
                if ((onLeftClick != null && clickedButton[0]) || (onRightClick != null && clickedButton[1])) {
                    textures = entry.getTexturePressed();
                } else {
                    textures = entry.getTextureHover();
                }
            } else {
                textures = entry.getTexture();
                Arrays.fill(clickedButton, false);
            }
        } else {
            textures = entry.getTextureDisable();
            Arrays.fill(clickedButton, false);
        }
        GLSwitcher d = GLSwitcher.depth().enable();
        GLSwitcher b = GLSwitcher.blend().enable();
        for (String texture : textures) {
            if (widgets.containsWidget(texture)) {
                TextureEntry tex = (TextureEntry) widgets.getWidget(texture);
                guiGraphics.blit(widgets.getTexture(), getX(), getY(), getWidth(), getHeight(), tex.getTextureX(),
                        tex.getTextureY(), tex.getTextureWidth(), tex.getTextureHeight(),
                        widgets.getWidth(), widgets.getHeight());
            }
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
        clickedButton[button] = true;
        if (this.active && this.visible && button == 1 /* GLFW.GLFW_MOUSE_BUTTON_RIGHT */) {
            if (onRightClick != null && clicked(mouseX, mouseY)) {
                this.playDownSound(Minecraft.getInstance().getSoundManager());
                onRightClick.onPress(this);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        clickedButton[button] = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public void setOnRightClick(@Nullable OnPress onRightClick) {
        this.onRightClick = onRightClick;
    }

    public void setOnLeftClick(@Nullable OnPress onLeftClick) {
        this.onLeftClick = onLeftClick;
    }
}
