package games.moegirl.sinocraft.sinocore.mixin.interfaces;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author luqin2007
 */
@OnlyIn(Dist.CLIENT)
public interface IScreen {

    <T extends GuiEventListener & Renderable& NarratableEntry> T sinocoreAddRenderableWidget(T widget);

    void sinocoreRenderTooltip(PoseStack poseStack, Component text, int mouseX, int mouseY);
}
