package games.moegirl.sinocraft.sinodivination.old.capability;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.util.TagSerializers;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public class BirthdayData implements ICapabilitySerializable<CompoundTag> {

    public static final ResourceLocation ID = new ResourceLocation(SinoDivination.MODID, "birthday");

    public static final Capability<BirthdayData> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    private LocalDateTime birthday;

    public BirthdayData() {
        birthday = LocalDateTime.now();
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void from(Player original) {
        original.getCapability(CAPABILITY).ifPresent(d -> birthday = d.birthday);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        TagSerializers.writeDate(birthday, tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        birthday = TagSerializers.readDate(nbt);
    }
}
