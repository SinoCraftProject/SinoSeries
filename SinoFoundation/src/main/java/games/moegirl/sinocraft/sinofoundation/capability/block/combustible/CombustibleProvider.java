package games.moegirl.sinocraft.sinofoundation.capability.block.combustible;

import games.moegirl.sinocraft.sinofoundation.capability.SFDCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CombustibleProvider implements ICapabilitySerializable<CompoundTag> {
    private final ICombustible emptyCombustible = new Combustible();

    private final LazyOptional<ICombustible> combustibleOptional = LazyOptional.of(() -> emptyCombustible);

    public void invalidate() {
        combustibleOptional.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == SFDCapabilities.COMBUSTIBLE_BLOCK) {
            return combustibleOptional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        var heatSource = combustibleOptional.orElse(emptyCombustible);
        return heatSource.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        var heatSource = combustibleOptional.orElse(emptyCombustible);
        heatSource.deserializeNBT(tag);
    }
}
