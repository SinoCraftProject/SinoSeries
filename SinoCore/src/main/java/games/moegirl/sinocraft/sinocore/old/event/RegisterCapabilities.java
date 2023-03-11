package games.moegirl.sinocraft.sinocore.old.event;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.old.capability.IQuizzingPlayer;
import games.moegirl.sinocraft.sinocore.old.capability.QuizzingPlayerProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegisterCapabilities {
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IQuizzingPlayer.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            var provider = new QuizzingPlayerProvider();
            event.addCapability(new ResourceLocation(SinoCore.MODID, "player_quiz"), provider);
            event.addListener(provider::invalidate);
        }
    }
}
