package games.moegirl.sinocraft.sinofeast.capability.entity;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.capability.SFCapabilities;
import games.moegirl.sinocraft.sinofeast.capability.entity.player_preference.IPlayerFoodPreference;
import games.moegirl.sinocraft.sinofeast.capability.entity.player_preference.PlayerFoodPreferenceProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber(modid = SinoFeast.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SFEntityCapsRegister {
//    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IPlayerFoodPreference.class);
    }

//    @SubscribeEvent
    public static void onAttachEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            var provider = new PlayerFoodPreferenceProvider();
            event.addCapability(SFCapabilities.PLAYER_FOOD_PREFERENCE_NAME, provider);
            event.addListener(provider::invalidate);
        }
    }
}
