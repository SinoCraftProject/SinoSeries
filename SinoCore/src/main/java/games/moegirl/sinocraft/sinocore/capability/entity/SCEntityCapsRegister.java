package games.moegirl.sinocraft.sinocore.capability.entity;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.capability.SCCapabilities;
import games.moegirl.sinocraft.sinocore.capability.entity.armor_stand.ISummonedArmorStand;
import games.moegirl.sinocraft.sinocore.capability.entity.armor_stand.SummonedArmorStandProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SCEntityCapsRegister {
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ISummonedArmorStand.class);
    }

    @SubscribeEvent
    public static void onAttachEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof ArmorStand) {
            var provider = new SummonedArmorStandProvider();
            event.addCapability(SCCapabilities.SUMMONED_ARMOR_STAND_NAME, provider);
            event.addListener(provider::invalidate);
        }
    }
}
