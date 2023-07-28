package games.moegirl.sinocraft.sinofeast.capability.entity.player_preference;

import games.moegirl.sinocraft.sinofeast.capability.SFCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerFoodPreferenceProvider implements ICapabilitySerializable<CompoundTag> {
    private final IPlayerFoodPreference empty = new PlayerFoodPreference();

    private final LazyOptional<IPlayerFoodPreference> optional = LazyOptional.of(() -> empty);

    public void invalidate() {
        optional.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
        if (capability == SFCapabilities.PLAYER_FOOD_PREFERENCE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        var cap = optional.orElseThrow(RuntimeException::new);
        return cap.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        var cap = optional.orElseThrow(RuntimeException::new);
        cap.deserializeNBT(tag);
    }
}
