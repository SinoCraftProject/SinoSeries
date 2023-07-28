package games.moegirl.sinocraft.sinofeast.capability.entity;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoFeast.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SFPlayerCapsClone {
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
//        if (event.isWasDeath()) {
//
//        }
    }
}
