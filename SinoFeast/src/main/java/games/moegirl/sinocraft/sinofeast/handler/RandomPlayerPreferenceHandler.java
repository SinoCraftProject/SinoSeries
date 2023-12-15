package games.moegirl.sinocraft.sinofeast.handler;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.capability.SFCapabilities;
import games.moegirl.sinocraft.sinofeast.networking.packet.S2CSyncPlayerPreferencePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber(modid = SinoFeast.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RandomPlayerPreferenceHandler {
//    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        var player = event.getEntity();
        if (!event.isEndConquered()) {
            if (player instanceof ServerPlayer) {
                var cap = player.getCapability(SFCapabilities.PLAYER_FOOD_PREFERENCE).orElseThrow(RuntimeException::new);
                cap.randomPreference();
                SinoFeast.getInstance().getNetworking().sendTo(new S2CSyncPlayerPreferencePacket(cap.getPrefer(), cap.getLike(), cap.getDislike()), player);
            }
        }
    }

//    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        var player = event.getEntity();

        if (player instanceof ServerPlayer) {
            var cap = player.getCapability(SFCapabilities.PLAYER_FOOD_PREFERENCE).orElseThrow(RuntimeException::new);

            if (cap.getPrefer() == null || cap.getLike() == null || cap.getDislike() == null) {
                cap.randomPreference();
                SinoFeast.getInstance().getNetworking().sendTo(new S2CSyncPlayerPreferencePacket(cap.getPrefer(), cap.getLike(), cap.getDislike()), player);
            }
        }
    }
}
