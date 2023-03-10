package games.moegirl.sinocraft.sinofoundation.capability;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.capability.block.combustible.ICombustible;
import games.moegirl.sinocraft.sinofoundation.capability.block.heatsource.IHeatSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SFDCapabilities {
    public static final ResourceLocation HEAT_SOURCE_NAME = new ResourceLocation(SinoFoundation.MODID, "heat_source");
    public static final ResourceLocation COMBUSTIBLE_NAME = new ResourceLocation(SinoFoundation.MODID, "combustible");

    // Block Capabilities.
    public static final Capability<IHeatSource> HEAT_SOURCE_BLOCK = CapabilityManager.get(new CapabilityToken<>() { });
    public static final Capability<ICombustible> COMBUSTIBLE_BLOCK = CapabilityManager.get(new CapabilityToken<>() { });
}
