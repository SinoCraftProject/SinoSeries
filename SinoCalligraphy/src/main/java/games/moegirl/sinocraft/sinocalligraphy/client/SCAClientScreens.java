package games.moegirl.sinocraft.sinocalligraphy.client;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.gui.SCAMenus;
import games.moegirl.sinocraft.sinocalligraphy.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinocalligraphy.gui.screen.BrushScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SinoCalligraphy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SCAClientScreens {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuType<? extends BrushMenu> type = SCAMenus.BRUSH.get();
            MenuScreens.register(type, BrushScreen::new);
        });

//        ItemBlockRenderTypes.setRenderLayer(SCABlocks.WOOD_PULP_BLOCK.get(), RenderType.translucent());
//        ItemBlockRenderTypes.setRenderLayer(SCAFluids.WOOD_PULP.get(), RenderType.translucent());
//        ItemBlockRenderTypes.setRenderLayer(SCAFluids.WOOD_PULP_FLOWING.get(), RenderType.translucent());
    }
}
