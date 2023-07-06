package games.moegirl.sinocraft.sinocore.capability.entity.armor_stand;

import games.moegirl.sinocraft.sinocore.capability.SCCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SummonedArmorStandProvider implements ICapabilitySerializable<CompoundTag> {
    private final ISummonedArmorStand empty = new SummonedArmorStand();

    private final LazyOptional<ISummonedArmorStand> optional = LazyOptional.of(() -> empty);

    public void invalidate() {
        optional.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == SCCapabilities.SUMMONED_ARMOR_STAND) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        var heatSource = optional.orElseThrow(RuntimeException::new);
        return heatSource.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        var heatSource = optional.orElseThrow(RuntimeException::new);
        heatSource.deserializeNBT(tag);
    }
}
