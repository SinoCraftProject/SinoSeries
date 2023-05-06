package games.moegirl.sinocraft.sinodivination.old.handler;

import games.moegirl.sinocraft.sinodivination.old.capability.BirthdayData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void onAttachToPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(BirthdayData.ID, new BirthdayData());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            event.getOriginal().getCapability(BirthdayData.CAPABILITY).ifPresent(cap -> cap.from(event.getOriginal()));
        }
    }
}
