package games.moegirl.sinocraft.sinocore.client.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.client.GLSwitcher;
import games.moegirl.sinocraft.sinocore.utility.texture.ButtonEntry;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureEntry;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ImageButton extends net.minecraft.client.gui.components.Button {

    @Nullable
    private OnPress onRightClick;
    @Nullable
    private OnPress onLeftClick;
    private final TextureMap map;
    private final List<String> tex;
    private final List<String> texHover;
    private final List<String> texPressed;
    private final List<String> texDisable;

    private final Screen parent;

    public ImageButton(AbstractContainerScreen<?> parent, TextureMap texture, ButtonEntry entry) {
        super(entry.x() + parent.getGuiLeft(), entry.y() + parent.getGuiTop(), entry.w(), entry.h(), entry.buildTooltip(), btn -> {}, Supplier::get);
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
    public void renderWidget(PoseStack pPoseStack, int pMouseX, int pMouseY, float partialTick) {
        if (!visible) {
            return;
        }
        List<String> textures;
        if (this.isHovered) {
            parent.renderTooltip(pPoseStack, getMessage(), pMouseX, pMouseY);
            MouseHandler mouse = Minecraft.getInstance().mouseHandler;
            if (mouse.isLeftPressed() || mouse.isMiddlePressed() || mouse.isRightPressed()) {
                textures = texPressed;
            } else {
                textures = texHover;
            }
        } else if (active) {
            textures = tex;
        } else {
            textures = texDisable;
        }
        if (!textures.isEmpty()) {
            for (String texture : textures) {
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
    }

    @Override
    public void onPress() {
        if (onLeftClick != null)
            onLeftClick.onPress(this);
    }

    public ImageButton setOnRightClick(@Nullable OnPress onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }

    public ImageButton setOnLeftClick(@Nullable OnPress onLeftClick) {
        this.onLeftClick = onLeftClick;
        return this;
    }
}
