package games.moegirl.sinocraft.sinodivination.old.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.client.TextureMapClient;
import games.moegirl.sinocraft.sinodivination.old.menu.CarvingTableMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import static games.moegirl.sinocraft.sinodivination.old.menu.CarvingTableMenu.TEXTURE;

public class CarvingTableScreen extends AbstractContainerScreen<CarvingTableMenu> {

    private static final TextureMapClient CLIENT = new TextureMapClient(TEXTURE);

    public CarvingTableScreen(CarvingTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        CLIENT.blitTexture(pPoseStack, "background", this);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        CLIENT.renderText(poseStack, this, "title");
    }
}
