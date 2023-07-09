package games.moegirl.sinocraft.sinofeast.data.gen;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoFeast.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SFDataGen {
    @SubscribeEvent
    public static void onRegisterPredicates(GatherDataEvent event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();


    }
}
