package games.moegirl.sinocraft.sinodivination.handler;

import games.moegirl.sinocraft.sinocore.client.blockentity.BaseChestRenderer;
import games.moegirl.sinocraft.sinodivination.SDTrees;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author luqin2007
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRendererEventHandler {

    @SubscribeEvent
    public static void onBlockEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(SDBlockEntities.COTINUS_CHEST.get(), context -> new BaseChestRenderer(context, SDTrees.COTINUS.name, false));
        event.registerBlockEntityRenderer(SDBlockEntities.COTINUS_TRAPPED_CHEST.get(), context -> new BaseChestRenderer(context, SDTrees.COTINUS.name, true));
        event.registerBlockEntityRenderer(SDBlockEntities.SOPHORA_CHEST.get(), context -> new BaseChestRenderer(context, SDTrees.SOPHORA.name, false));
        event.registerBlockEntityRenderer(SDBlockEntities.SOPHORA_TRAPPED_CHEST.get(), context -> new BaseChestRenderer(context, SDTrees.SOPHORA.name, true));
    }
}
