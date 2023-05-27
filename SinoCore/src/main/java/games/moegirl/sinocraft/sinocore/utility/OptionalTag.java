package games.moegirl.sinocraft.sinocore.utility;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.nbt.Tag.*;

@SuppressWarnings({"unchecked"})
public final class OptionalTag<T extends Tag> {

    // Optional ========================================================================================================

    private static final OptionalTag<?> EMPTY = new OptionalTag<>(null);

    @Nullable
    private final T value;

    public static <T extends Tag> OptionalTag<T> empty() {
        return (OptionalTag<T>) EMPTY;
    }

    private OptionalTag(@Nullable T value) {
        this.value = value;
    }

    public static <T extends Tag> OptionalTag<T> of(T value) {
        return new OptionalTag<>(Objects.requireNonNull(value));
    }

    public static <T extends Tag> OptionalTag<T> ofNullable(@Nullable T value) {
        return value == null ? (OptionalTag<T>) EMPTY : new OptionalTag<>(value);
    }

    public void ifPresent(Consumer<? super T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof OptionalTag<?> other && Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value != null ? String.format("OptionalTag[%s]", value) : "Optional.empty";
    }

    public OptionalTag<CompoundTag> asCompound() {
        return value instanceof CompoundTag tag ? of(tag) : empty();
    }

    public <U> Optional<U> mapToObj(Codec<U> codec) {
        return value == null ? Optional.empty() : codec.decode(NbtOps.INSTANCE, value).result().map(Pair::getFirst);
    }

    public OptionalTag<Tag> get(String name) {
        return value instanceof CompoundTag tag && tag.contains(name) ? of(tag.get(name)) : empty();
    }

    public Optional<String> getString(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_STRING) ? Optional.of(tag.getString(name)) : Optional.empty();
    }

    public OptionalTag<CompoundTag> getCompound(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_COMPOUND) ? of(tag.getCompound(name)) : empty();
    }

    public Optional<UUID> getUUID(String name) {
        return value instanceof CompoundTag tag && tag.hasUUID(name) ? Optional.of(tag.getUUID(name)) : Optional.empty();
    }

    public OptionalTag<CompoundTag> put(String name, @Nullable Tag value) {
        OptionalTag<CompoundTag> optionalTag = asCompound();
        if (value != null) optionalTag.ifPresent(tag -> tag.put(name, value));
        return optionalTag;
    }

    public OptionalTag<CompoundTag> put(String name, @Nullable UUID value) {
        OptionalTag<CompoundTag> optionalTag = asCompound();
        if (value != null) optionalTag.ifPresent(tag -> tag.putUUID(name, value));
        return optionalTag;
    }

    public <U> OptionalTag<CompoundTag> put(String name, @Nullable U value, Codec<U> codec) {
        OptionalTag<CompoundTag> optionalTag = asCompound();
        if (value != null) {
            codec.encodeStart(NbtOps.INSTANCE, value).result().ifPresent(tag -> optionalTag.put(name, tag));
        }
        return optionalTag;
    }

    public <U extends Tag> OptionalTag<U> computeIfAbsent(String name, int type, Function<String, U> factory) {
        if (value instanceof CompoundTag tag) {
            if (tag.contains(name, type)) {
                return ofNullable((U) tag.get(name));
            } else {
                U apply = factory.apply(name);
                tag.put(name, apply);
                return of(apply);
            }
        }
        return empty();
    }

    public <U extends Tag> OptionalTag<U> computeIfAbsent(String name, int type, Supplier<U> factory) {
        return computeIfAbsent(name, type, __ -> factory.get());
    }

    public static OptionalTag<CompoundTag> of(ItemStack stack) {
        return stack.hasTag() ? of(stack.getTag()) : empty();
    }

    public static OptionalTag<CompoundTag> ofOrCreate(ItemStack stack) {
        return of(stack.getOrCreateTag());
    }
}
