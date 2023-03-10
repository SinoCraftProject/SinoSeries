package games.moegirl.sinocraft.sinocore.old.capability;

import games.moegirl.sinocraft.sinocore.api.capability.IQuizzingPlayer;
import games.moegirl.sinocraft.sinocore.api.capability.SCCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class QuizzingPlayerProvider implements ICapabilitySerializable<CompoundTag> {
    private final QuizzingPlayer quizzingPlayer = new QuizzingPlayer();
    private final LazyOptional<IQuizzingPlayer> quizzingPlayerOptional = LazyOptional.of(() -> quizzingPlayer);

    public void invalidate() {
        quizzingPlayerOptional.invalidate();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
        if (capability == SCCapabilities.QUIZZING_PLAYER_CAPABILITY) {
            return quizzingPlayerOptional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        if (!quizzingPlayerOptional.isPresent()) {
            return new CompoundTag();
        }

        return quizzingPlayerOptional.resolve().get().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (!quizzingPlayerOptional.isPresent()) {
            return;
        }

        quizzingPlayerOptional.resolve().get().deserializeNBT(nbt);
    }
}
