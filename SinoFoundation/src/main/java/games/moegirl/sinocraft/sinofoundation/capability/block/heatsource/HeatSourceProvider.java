package games.moegirl.sinocraft.sinofoundation.capability.block.heatsource;

import games.moegirl.sinocraft.sinofoundation.capability.SFDCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HeatSourceProvider implements ICapabilitySerializable<CompoundTag> {
    private final IHeatSource emptyHeatSource = new HeatSource();

    private final LazyOptional<IHeatSource> heatSourceOptional = LazyOptional.of(() -> emptyHeatSource);

    public void invalidate() {
        heatSourceOptional.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == SFDCapabilities.HEAT_SOURCE_BLOCK) {
            return heatSourceOptional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        var heatSource = heatSourceOptional.orElseThrow(RuntimeException::new);
        return heatSource.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        var heatSource = heatSourceOptional.orElseThrow(RuntimeException::new);
        heatSource.deserializeNBT(tag);
    }
}
