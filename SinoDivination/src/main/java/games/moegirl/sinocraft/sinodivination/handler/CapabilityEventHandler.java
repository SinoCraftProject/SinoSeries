package games.moegirl.sinocraft.sinodivination.handler;

import games.moegirl.sinocraft.sinofoundation.capability.SFDCapabilities;
import games.moegirl.sinocraft.sinofoundation.capability.entity.birthday.Birthday;
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
            event.addCapability(SFDCapabilities.BIRTHDAY_NAME, new Birthday());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            event.getOriginal().getCapability(SFDCapabilities.BIRTHDAY).ifPresent(cap -> cap.from(event.getOriginal()));
        }
    }
}
