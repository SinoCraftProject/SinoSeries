package games.moegirl.sinocraft.sinofoundation.handler;

import games.moegirl.sinocraft.sinocore.tree.client.BaseChestRenderer;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import games.moegirl.sinocraft.sinofoundation.block.entity.SFDBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author luqin2007
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockEntityRendererEventHandler {

    @SubscribeEvent
    public void onBlockEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(SFDBlockEntities.COTINUS_CHEST.get(), context -> new BaseChestRenderer(context, SFDTrees.COTINUS.name, false));
        event.registerBlockEntityRenderer(SFDBlockEntities.COTINUS_TRAPPED_CHEST.get(), context -> new BaseChestRenderer(context, SFDTrees.COTINUS.name, true));
        event.registerBlockEntityRenderer(SFDBlockEntities.JUJUBE_CHEST.get(), context -> new BaseChestRenderer(context, SFDTrees.JUJUBE.name, false));
        event.registerBlockEntityRenderer(SFDBlockEntities.JUJUBE_TRAPPED_CHEST.get(), context -> new BaseChestRenderer(context, SFDTrees.JUJUBE.name, true));
        event.registerBlockEntityRenderer(SFDBlockEntities.SOPHORA_CHEST.get(), context -> new BaseChestRenderer(context, SFDTrees.SOPHORA.name, false));
        event.registerBlockEntityRenderer(SFDBlockEntities.SOPHORA_TRAPPED_CHEST.get(), context -> new BaseChestRenderer(context, SFDTrees.SOPHORA.name, true));
    }
}
