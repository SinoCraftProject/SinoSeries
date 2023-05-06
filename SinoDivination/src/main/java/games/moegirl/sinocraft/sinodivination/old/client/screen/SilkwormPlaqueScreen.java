package games.moegirl.sinocraft.sinodivination.old.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.client.GLSwitcher;
import games.moegirl.sinocraft.sinocore.client.TextureMapClient;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureEntry;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SilkwormPlaqueEntity;
import games.moegirl.sinocraft.sinodivination.old.menu.SilkwormPlaqueMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import static games.moegirl.sinocraft.sinodivination.old.menu.SilkwormPlaqueMenu.TEXTURE;

public class SilkwormPlaqueScreen extends AbstractContainerScreen<SilkwormPlaqueMenu> {

    private static final TextureMapClient CLIENT = new TextureMapClient(TEXTURE);
    private static final TextureEntry TAIL = TEXTURE.textures().ensureGet("texture_nutrition_tail");
    private static final TextureEntry BODY = TEXTURE.textures().ensureGet("texture_nutrition_body");

    public SilkwormPlaqueScreen(SilkwormPlaqueMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        TextureEntry entry = TEXTURE.textures().ensureGet("background");
        width = entry.w();
        height = entry.h();
    }

    @Override
    protected void init() {
        super.init();
        System.out.println("This is " + this.menu.entity());
        TEXTURE.points().get("title").ifPresent(p -> {
            titleLabelX = p.x();
            titleLabelY = p.y();
        });
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        CLIENT.blitTexture(pPoseStack, "background", this);
        SilkwormPlaqueEntity entity = menu.entity();
        for (int i = 0; i < SilkwormPlaqueEntity.SILKWORM_COUNT; i++) {
            CLIENT.blitProgress(pPoseStack, "progress" + i, this, entity.silkworm(i).progress());
        }
        float progress = entity.nutrition() / 100f;
        if (progress > 0) {
            GLSwitcher switcher = GLSwitcher.blend().enable();
            CLIENT.blitTexture(pPoseStack, "texture_nutrition_tail", this);
            CLIENT.blitProgress(pPoseStack, "nutrition_progress", this, progress);
            CLIENT.blitTexture(pPoseStack, "texture_nutrition_head", TAIL.x(), (int) (TAIL.y() - TAIL.h() - BODY.h() * progress), this);
            switcher.resume();
        }
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 0x404040);
    }
}
