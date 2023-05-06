package games.moegirl.sinocraft.sinofoundation.capability.block;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.entity.StoveBlockEntity;
import games.moegirl.sinocraft.sinofoundation.capability.SFDCapabilities;
import games.moegirl.sinocraft.sinofoundation.capability.block.combustible.CombustibleProvider;
import games.moegirl.sinocraft.sinofoundation.capability.block.combustible.ICombustible;
import games.moegirl.sinocraft.sinofoundation.capability.block.heatsource.HeatSourceProvider;
import games.moegirl.sinocraft.sinofoundation.capability.block.heatsource.IHeatSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoFoundation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SFDBlockCapsRegister {
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IHeatSource.class);
        event.register(ICombustible.class);
    }

    @SubscribeEvent
    public static void onAttachBlockEntity(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof StoveBlockEntity) {
            var heatSourceProvider = new HeatSourceProvider();
            event.addCapability(SFDCapabilities.HEAT_SOURCE_NAME, heatSourceProvider);
            event.addListener(heatSourceProvider::invalidate);

            var combustibleProvider = new CombustibleProvider();
            event.addCapability(SFDCapabilities.COMBUSTIBLE_NAME, combustibleProvider);
            event.addListener(combustibleProvider::invalidate);
        }
    }
}
