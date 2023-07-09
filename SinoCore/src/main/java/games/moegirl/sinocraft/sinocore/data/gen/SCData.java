package games.moegirl.sinocraft.sinocore.data.gen;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SCData {
    @SubscribeEvent
    public static void onRegisterPredicates(GatherDataEvent event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();
    }
}
