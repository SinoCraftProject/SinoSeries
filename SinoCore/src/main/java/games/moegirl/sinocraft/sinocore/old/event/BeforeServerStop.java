package games.moegirl.sinocraft.sinocore.old.event;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BeforeServerStop {
    @SubscribeEvent
    public static void onServerStop(ServerStoppingEvent event) {
        SinoCore.getInstance().getPool().shutdownNow();
    }
}
