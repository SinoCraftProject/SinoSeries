package games.moegirl.sinocraft.sinobrush.item.component;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class SBRDataComponents {
    public static final IRegistry<DataComponentType<?>> DATA_COMPONENTS = RegistryManager.obtain(SinoBrush.MODID, Registries.DATA_COMPONENT_TYPE);

    public static final Supplier<DataComponentType<PaperData>> PAPER = DATA_COMPONENTS.register("paper", () -> DataComponentType.<PaperData>builder().persistent(PaperData.CODEC).networkSynchronized(PaperData.STREAM_CODEC).build());
    public static final Supplier<DataComponentType<Drawing>> DRAWING = DATA_COMPONENTS.register("drawing", () -> DataComponentType.<Drawing>builder().persistent(Drawing.CODEC).networkSynchronized(Drawing.STREAM_CODEC).build());
    public static final Supplier<DataComponentType<FanData>> FAN = DATA_COMPONENTS.register("fan", () -> DataComponentType.<FanData>builder().persistent(FanData.CODEC).networkSynchronized(FanData.STREAM_CODEC).build());

    public static void register() {
        DATA_COMPONENTS.register();
    }
}
