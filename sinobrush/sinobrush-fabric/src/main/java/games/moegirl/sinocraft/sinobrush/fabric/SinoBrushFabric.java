package games.moegirl.sinocraft.sinobrush.fabric;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.client.FilledXuanPaperItemColor;
import games.moegirl.sinocraft.sinobrush.client.NormalItemColor;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class SinoBrushFabric implements ModInitializer, ClientModInitializer {

    private final SinoBrush mod = new SinoBrush();

    @Override
    public void onInitialize() {
        mod.init();
    }

    @Override
    public void onInitializeClient() {
        mod.initClient();

        ColorProviderRegistry.ITEM.register(new NormalItemColor(), SBRItems.XUAN_PAPER.get(), SBRItems.INK_BOTTLE.get());
        ColorProviderRegistry.ITEM.register(new FilledXuanPaperItemColor(), SBRItems.FILLED_XUAN_PAPER.get());
    }
}
