package games.moegirl.sinocraft.sinofoundation.capability.entity.birthday;

import games.moegirl.sinocraft.sinofoundation.capability.SFDCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public class Birthday implements ICapabilitySerializable<Tag> {

    private Instant birthday;

    public Birthday() {
        birthday = Instant.now();
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void from(Player original) {
        original.getCapability(SFDCapabilities.BIRTHDAY).ifPresent(d -> birthday = d.birthday);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return SFDCapabilities.BIRTHDAY.orEmpty(cap, LazyOptional.of(() -> this));
    }

    @Override
    public Tag serializeNBT() {
        return ExtraCodecs.INSTANT_ISO8601.encodeStart(NbtOps.INSTANCE, birthday).result().orElseThrow();
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        birthday = ExtraCodecs.INSTANT_ISO8601.decode(NbtOps.INSTANCE, nbt).result().orElseThrow().getFirst();
    }
}
