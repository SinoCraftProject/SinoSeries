package games.moegirl.sinocraft.sinocalligraphy;

import games.moegirl.sinocraft.sinocalligraphy.fluid.SCAFluids;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SinoCalligraphy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SinoCalligraphyClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(SCAFluids.WOOD_PULP.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(SCAFluids.WOOD_PULP_FLOWING.get(), RenderType.translucent());
    }
}
