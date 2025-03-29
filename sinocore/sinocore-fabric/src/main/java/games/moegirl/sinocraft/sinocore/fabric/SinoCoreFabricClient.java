package games.moegirl.sinocraft.sinocore.fabric;

import games.moegirl.sinocraft.sinocore.event.client.RenderEvents;
import games.moegirl.sinocraft.sinocore.event.client.args.render.CustomItemRendererArgs;
import games.moegirl.sinocraft.sinocore.event.client.model.fabric.ModelEventImpl;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.world.item.Item;

public class SinoCoreFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(new ModelEventImpl());

        RenderEvents.CUSTOM_ITEM_RENDERER.invoke(new CustomItemRendererArgs((renderer, items) -> {
            for (Item item : items) {
                BuiltinItemRendererRegistry.INSTANCE.register(item, renderer::renderByItem);
            }
        }));
    }
}
