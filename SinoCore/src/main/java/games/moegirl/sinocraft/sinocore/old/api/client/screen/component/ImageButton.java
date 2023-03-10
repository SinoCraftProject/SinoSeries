package games.moegirl.sinocraft.sinocore.old.api.client.screen.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.api.client.GLSwitcher;
import games.moegirl.sinocraft.sinocore.api.utility.texture.ButtonEntry;
import games.moegirl.sinocraft.sinocore.api.utility.texture.TextureEntry;
import games.moegirl.sinocraft.sinocore.api.utility.texture.TextureMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import javax.annotation.Nullable;
import java.util.Optional;

public class ImageButton extends net.minecraft.client.gui.components.Button {

    @Nullable
    private final OnPress onRightClick;
    private final TextureMap map;
    @Nullable
    private final String tex;
    @Nullable
    private final String texHover;
    @Nullable
    private final String texPressed;
    @Nullable
    private final String texDisable;

    public ImageButton(AbstractContainerScreen<?> parent, TextureMap texture, ButtonEntry entry, OnPress onPress, @Nullable OnPress onRightClick) {
        super(entry.x() + parent.getGuiLeft(), entry.y() + parent.getGuiTop(), entry.w(), entry.h(), entry.buildTooltip(), onPress,
                entry.tooltip() == null ? NO_TOOLTIP : (__, arg2, i, j) -> parent.renderTooltip(arg2, entry.buildTooltip(), i, j));
        this.onRightClick = onRightClick;
        this.map = texture;
        this.tex = entry.texture();
        this.texHover = entry.textureHover();
        this.texPressed = entry.texturePressed();
        this.texDisable = entry.textureDisable();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (this.active && this.visible) {
            if (!this.isValidClickButton(pButton)) {
                if (onRightClick != null && isMouseOver(pMouseX, pMouseY)) {
                    onRightClick.onPress(this);
                    this.playDownSound(Minecraft.getInstance().getSoundManager());
                    return true;
                }
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (!visible) {
            return;
        }
        String texture;
        if (this.isHovered) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
            MouseHandler mouse = Minecraft.getInstance().mouseHandler;
            if (mouse.isLeftPressed() || mouse.isMiddlePressed() || mouse.isRightPressed()) {
                texture = texPressed;
            } else {
                texture = texHover;
            }
        } else if (active) {
            texture = tex;
        } else {
            texture = texDisable;
        }
        if (texture != null) {
            Optional<TextureEntry> optional = map.textures().get(texture);
            if (optional.isPresent()) {
                TextureEntry entry = optional.get();
                RenderSystem.setShaderTexture(0, map.texture());
                GLSwitcher d = GLSwitcher.depth().enable();
                GLSwitcher b = GLSwitcher.blend().enable();
                GuiComponent.blit(pPoseStack, x, y, width, height, entry.u(), entry.v(), entry.tw(), entry.th(), map.width, map.height);
                d.resume();
                b.resume();
            }
        }
    }
}
