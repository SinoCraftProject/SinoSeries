package games.moegirl.sinocraft.sinocore.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class TagCapabilitySerializable<C extends INBTSerializable<CompoundTag>>
        implements ICapabilitySerializable<CompoundTag>, Supplier<C> {

    private final Capability<C> cap;
    private final LazyOptional<C> opt;

    public TagCapabilitySerializable(Capability<C> cap, Supplier<? extends C> value) {
        this.cap = cap;
        this.opt = LazyOptional.of(value::get);
    }

    public void invalidate() {
        opt.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction arg) {
        return cap.orEmpty(capability, opt);
    }

    @Override
    public CompoundTag serializeNBT() {
        return opt.map(C::serializeNBT).orElseGet(CompoundTag::new);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        opt.ifPresent(p -> p.deserializeNBT(nbt));
    }

    @Override
    public C get() {
        return opt.orElseThrow(() -> new NullPointerException("Not found existed " + cap.getName() + " capability."));
    }
}
