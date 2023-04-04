package games.moegirl.sinocraft.sinocore.client.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.client.GLSwitcher;
import games.moegirl.sinocraft.sinocore.mixin.interfaces.IScreen;
import games.moegirl.sinocraft.sinocore.utility.texture.ButtonEntry;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureEntry;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

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

    private final Screen parent;

    public ImageButton(AbstractContainerScreen<?> parent, TextureMap texture, ButtonEntry entry, OnPress onPress, @Nullable OnPress onRightClick) {
        super(entry.x() + parent.getGuiLeft(), entry.y() + parent.getGuiTop(), entry.w(), entry.h(), entry.buildTooltip(), onPress, Supplier::get);
        this.onRightClick = onRightClick;
        this.map = texture;
        this.tex = entry.texture();
        this.texHover = entry.textureHover();
        this.texPressed = entry.texturePressed();
        this.texDisable = entry.textureDisable();
        this.parent = parent;
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
            ((IScreen) parent).sinocoreRenderTooltip(pPoseStack, getMessage(), pMouseX, pMouseY);
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
                GuiComponent.blit(pPoseStack, getX(), getY(), width, height, entry.u(), entry.v(), entry.tw(), entry.th(), map.width, map.height);
                d.resume();
                b.resume();
            }
        }
    }

    public interface RenderTooltip {
        void renderTooltip(PoseStack poseStack, int x, int y);
    }
}
