package games.moegirl.sinocraft.sinobrush.fabric;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.cllient.FilledXuanPaperRenderer;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.item.DyeableLeatherItem;

public class SinoBrushFabric implements ModInitializer, ClientModInitializer {

    private final SinoBrush mod = new SinoBrush();

    @Override
    public void onInitialize() {
        mod.init();
    }

    @Override
    public void onInitializeClient() {
        mod.initClient();

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex == 0 && stack.getItem() instanceof DyeableLeatherItem dyeable) {
                return dyeable.getColor(stack);
            }
            return -1;
        }, SBRItems.XUAN_PAPER.get(), SBRItems.FILLED_XUAN_PAPER.get(), SBRItems.INK_BOTTLE.get());
    }
}
