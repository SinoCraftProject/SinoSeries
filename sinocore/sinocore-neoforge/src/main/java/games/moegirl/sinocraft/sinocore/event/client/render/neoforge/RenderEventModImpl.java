package games.moegirl.sinocraft.sinocore.event.client.render.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.event.client.RenderEvents;
import games.moegirl.sinocraft.sinocore.event.client.args.render.CustomItemRendererArgs;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = SinoCore.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderEventModImpl {
    @SubscribeEvent
    public static void onClientExtensionRegister(RegisterClientExtensionsEvent event) {
        RenderEvents.CUSTOM_ITEM_RENDERER.invoke(new CustomItemRendererArgs((renderer, items) ->
                event.registerItem(new IClientItemExtensions() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return renderer;
                    }
                }, items)));
    }
}
