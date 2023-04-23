package games.moegirl.sinocraft.sinotest.sinocore.texture;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.client.TextureMapClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static games.moegirl.sinocraft.sinotest.sinocore.texture.TestTextureMenu.TEXTURE;

/**
 * @author luqin2007
 */
@OnlyIn(Dist.CLIENT)
public class TestTextureScreen extends AbstractContainerScreen<TestTextureMenu> {

    public static final TextureMapClient CLIENT = new TextureMapClient(TEXTURE);

    public TestTextureScreen(TestTextureMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        CLIENT.placeButton("button", this).onLeftClick(this::onLeft).onRightClick(this::onRight);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        CLIENT.blitTexture(poseStack, "background", this);
        CLIENT.renderText(poseStack, "title");
        float process = (System.currentTimeMillis() % 2000) / 2000f;
        CLIENT.blitProgress(poseStack, "firing", this, process);
        CLIENT.blitProgress(poseStack, "burning", this, process);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    private void onLeft(Button btn) {
        System.out.println("Left");
    }

    private void onRight(Button btn) {
        System.out.println("Right");
    }
}
