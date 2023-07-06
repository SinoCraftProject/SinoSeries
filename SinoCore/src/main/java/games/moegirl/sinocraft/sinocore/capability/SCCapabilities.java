package games.moegirl.sinocraft.sinocore.capability;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.capability.entity.armor_stand.ISummonedArmorStand;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

/**
 * All capability
 */
public class SCCapabilities {
    public static final ResourceLocation SUMMONED_ARMOR_STAND_NAME = new ResourceLocation(SinoCore.MODID, "summoned_armor_stand");

    public static final Capability<ISummonedArmorStand> SUMMONED_ARMOR_STAND = CapabilityManager.get(new CapabilityToken<>() {
    });
}
