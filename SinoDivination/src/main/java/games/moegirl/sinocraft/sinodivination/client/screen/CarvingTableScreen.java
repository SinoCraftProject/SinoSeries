package games.moegirl.sinocraft.sinodivination.client.screen;

import games.moegirl.sinocraft.sinocore.client.TextureMapClient;
import games.moegirl.sinocraft.sinodivination.menu.CarvingTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import static games.moegirl.sinocraft.sinodivination.menu.CarvingTableMenu.TEXTURE;

public class CarvingTableScreen extends AbstractContainerScreen<CarvingTableMenu> {

    private static final TextureMapClient CLIENT = new TextureMapClient(TEXTURE);

    public CarvingTableScreen(CarvingTableMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        CLIENT.blitTexture(graphics, "background", this);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        CLIENT.renderText(graphics, this, "title");
    }
}
