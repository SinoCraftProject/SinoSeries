package games.moegirl.sinocraft.sinocore.old.event;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.old.capability.QuizzingPlayer;
import games.moegirl.sinocraft.sinocore.old.capability.SCCapabilities;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ReSyncDataOnPlayerClone {
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
//        if (!event.isWasDeath()) {
//            return;
//        }

        var cap = event.getOriginal().getCapability(SCCapabilities.QUIZZING_PLAYER_CAPABILITY);
        if (!cap.isPresent()) {
            return;
        }

        var original = event.getOriginal().getCapability(SCCapabilities.QUIZZING_PLAYER_CAPABILITY);
        if (!original.isPresent()) {
            return;
        }

        var nbt = original.orElse(new QuizzingPlayer()).serializeNBT();
        cap.orElse(new QuizzingPlayer()).deserializeNBT(nbt);
    }
}
