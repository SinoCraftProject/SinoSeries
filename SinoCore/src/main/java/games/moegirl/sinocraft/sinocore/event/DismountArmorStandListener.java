package games.moegirl.sinocraft.sinocore.event;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.capability.SCCapabilities;
import games.moegirl.sinocraft.sinocore.capability.entity.armor_stand.SummonedArmorStand;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DismountArmorStandListener {
    @SubscribeEvent
    public static void onDismount(EntityMountEvent event) {
        if (event.isDismounting()) {
            if (event.getEntityBeingMounted() instanceof ArmorStand armorStand) {
                if (armorStand.isRemoved()) {
                    return;
                }

                var capOptional = armorStand.getCapability(SCCapabilities.SUMMONED_ARMOR_STAND);
                var cap = capOptional.orElse(new SummonedArmorStand());
                if (cap.shouldDeleteAfterDismount()) {
                    event.getEntityBeingMounted().discard();
                }
            }
        }
    }
}
