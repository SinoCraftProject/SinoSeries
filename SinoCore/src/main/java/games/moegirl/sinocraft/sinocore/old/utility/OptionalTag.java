package games.moegirl.sinocraft.sinocore.old.utility;

import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import static net.minecraft.nbt.Tag.*;

@SuppressWarnings({"unchecked", "ConstantConditions"})
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

    public T get() {
        return Objects.requireNonNull(value, "No value present");
    }

    public boolean isPresent() {
        return value != null;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public void ifPresent(Consumer<? super T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

    public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (value == null) {
            emptyAction.run();
        } else {
            action.accept(value);
        }
    }

    public OptionalTag<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return isPresent() ? predicate.test(value) ? this : empty() : this;
    }

    public <U extends Tag> OptionalTag<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        return isPresent() ? OptionalTag.ofNullable(mapper.apply(value)) : empty();
    }

    public <U extends Tag> OptionalTag<U> flatMap(Function<? super T, ? extends OptionalTag<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        return isPresent() ? Objects.requireNonNull((OptionalTag<U>) mapper.apply(value)) : empty();
    }

    public OptionalTag<T> or(Supplier<? extends OptionalTag<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        return isPresent() ? this : Objects.requireNonNull((OptionalTag<T>) supplier.get());
    }

    public Stream<T> stream() {
        return !isPresent() ? Stream.empty() : Stream.of(value);
    }

    public T orElse(T other) {
        return value != null ? value : other;
    }

    public T orElseGet(Supplier<? extends T> supplier) {
        return value != null ? value : supplier.get();
    }

    public T orElseThrow() {
        return Objects.requireNonNull(value, "No value present");
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
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

    // Tag =============================================================================================================

    public <U extends Tag> OptionalTag<U> as(int type) {
        return is(type) ? of((U) value) : empty();
    }

    public Optional<Byte> asByte() {
        return value instanceof ByteTag t ? Optional.of(t.getAsByte()) : Optional.empty();
    }

    public Optional<Short> asShort() {
        return value instanceof ShortTag t ? Optional.of(t.getAsShort()) : Optional.empty();
    }

    public OptionalInt asInt() {
        return value instanceof IntTag t ? OptionalInt.of(t.getAsInt()) : OptionalInt.empty();
    }

    public OptionalLong asLong() {
        return value instanceof LongTag t ? OptionalLong.of(t.getAsLong()) : OptionalLong.empty();
    }

    public Optional<Float> asFloat() {
        return value instanceof FloatTag t ? Optional.of(t.getAsFloat()) : Optional.empty();
    }

    public OptionalDouble asDouble() {
        return value instanceof DoubleTag t ? OptionalDouble.of(t.getAsDouble()) : OptionalDouble.empty();
    }

    public Optional<byte[]> asByteArray() {
        return value instanceof ByteArrayTag t ? Optional.of(t.getAsByteArray()) : Optional.empty();
    }

    public Optional<String> asString() {
        return value instanceof StringTag t ? Optional.of(t.getAsString()) : Optional.empty();
    }

    public OptionalTag<ListTag> asList() {
        return value instanceof ListTag list ? of(list) : empty();
    }

    public OptionalTag<CompoundTag> asCompound() {
        return value instanceof CompoundTag tag ? of(tag) : empty();
    }

    public Optional<int[]> asIntArray() {
        return value instanceof IntArrayTag t ? Optional.of(t.getAsIntArray()) : Optional.empty();
    }

    public Optional<long[]> asLongArray() {
        return value instanceof LongArrayTag t ? Optional.of(t.getAsLongArray()) : Optional.empty();
    }

    public Optional<Number> asNumber() {
        return value instanceof NumericTag t ? Optional.of(t.getAsNumber()) : Optional.empty();
    }

    public boolean is(int type) {
        return value != null && value.getId() == type;
    }

    public boolean isByte() {
        return is(TAG_BYTE);
    }

    public boolean isShort() {
        return is(TAG_SHORT);
    }

    public boolean isInt() {
        return is(TAG_INT);
    }

    public boolean isLong() {
        return is(TAG_LONG);
    }

    public boolean isFloat() {
        return is(TAG_FLOAT);
    }

    public boolean isDouble() {
        return is(TAG_DOUBLE);
    }

    public boolean isByteArray() {
        return is(TAG_BYTE_ARRAY);
    }

    public boolean isString() {
        return is(TAG_STRING);
    }

    public boolean isList() {
        return is(TAG_LIST);
    }

    public boolean isCompound() {
        return is(TAG_COMPOUND);
    }

    public boolean isIntArray() {
        return is(TAG_INT_ARRAY);
    }

    public boolean isLongArray() {
        return is(TAG_LONG_ARRAY);
    }

    public <U> Optional<U> mapToObj(Function<T, U> map) {
        return value == null ? Optional.empty() : Optional.ofNullable(map.apply(value));
    }

    // CompoundTag =====================================================================================================

    public OptionalTag<Tag> get(String name) {
        return value instanceof CompoundTag tag && tag.contains(name) ? of(tag.get(name)) : empty();
    }

    public <U extends Tag> OptionalTag<U> get(String name, int type) {
        return value instanceof CompoundTag tag && tag.contains(name, type) ? of((U) tag.get(name)) : empty();
    }

    public Optional<Byte> getByte(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_BYTE) ? Optional.of(tag.getByte(name)) : Optional.empty();
    }

    public Optional<Short> getShort(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_SHORT) ? Optional.of(tag.getShort(name)) : Optional.empty();
    }

    public OptionalInt getInt(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_INT) ? OptionalInt.of(tag.getInt(name)) : OptionalInt.empty();
    }

    public OptionalLong getLong(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_LONG) ? OptionalLong.of(tag.getLong(name)) : OptionalLong.empty();
    }

    public Optional<Float> getFloat(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_FLOAT) ? Optional.of(tag.getFloat(name)) : Optional.empty();
    }

    public OptionalDouble getDouble(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_DOUBLE) ? OptionalDouble.of(tag.getDouble(name)) : OptionalDouble.empty();
    }

    public Optional<byte[]> getByteArray(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_BYTE_ARRAY) ? Optional.of(tag.getByteArray(name)) : Optional.empty();
    }

    public Optional<String> getString(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_STRING) ? Optional.of(tag.getString(name)) : Optional.empty();
    }

    public OptionalTag<ListTag> getList(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_LIST) ? of((ListTag) tag.get(name)) : empty();
    }

    public OptionalTag<ListTag> getList(String name, int type) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_LIST) ? of(tag.getList(name, type)) : empty();
    }

    public OptionalTag<CompoundTag> getCompound(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_COMPOUND) ? of(tag.getCompound(name)) : empty();
    }

    public Optional<int[]> getIntArray(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_INT_ARRAY) ? Optional.of(tag.getIntArray(name)) : Optional.empty();
    }

    public Optional<long[]> getLongArray(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_LONG_ARRAY) ? Optional.of(tag.getLongArray(name)) : Optional.empty();
    }

    public Optional<UUID> getUUID(String name) {
        return value instanceof CompoundTag tag && tag.hasUUID(name) ? Optional.of(tag.getUUID(name)) : Optional.empty();
    }

    public boolean contains(String name) {
        return value instanceof CompoundTag tag && tag.contains(name);
    }

    public boolean contains(String name, int type) {
        return value instanceof CompoundTag tag && tag.contains(name, type);
    }

    public boolean containsByte(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_BYTE);
    }

    public boolean containsShort(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_SHORT);
    }

    public boolean containsInt(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_INT);
    }

    public boolean containsLong(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_LONG);
    }

    public boolean containsFloat(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_FLOAT);
    }

    public boolean containsDouble(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_DOUBLE);
    }

    public boolean containsByteArray(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_BYTE_ARRAY);
    }

    public boolean containsString(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_STRING);
    }

    public boolean containsList(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_LIST);
    }

    public boolean containsList(String name, int type) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_LIST);
    }

    public boolean containsCompound(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_COMPOUND);
    }

    public boolean containsIntArray(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_INT_ARRAY);
    }

    public boolean containsLongArray(String name) {
        return value instanceof CompoundTag tag && tag.contains(name, TAG_LONG_ARRAY);
    }

    public boolean containsUUID(String name) {
        return value instanceof CompoundTag tag && tag.hasUUID(name);
    }

    public OptionalTag<CompoundTag> put(String name, @Nullable Tag value) {
        OptionalTag<CompoundTag> optionalTag = asCompound();
        if (value != null) optionalTag.ifPresent(tag -> tag.put(name, value));
        return optionalTag;
    }

    public OptionalTag<CompoundTag> put(String name, byte value) {
        return put(name, ByteTag.valueOf(value));
    }

    public OptionalTag<CompoundTag> put(String name, short value) {
        return put(name, ShortTag.valueOf(value));
    }

    public OptionalTag<CompoundTag> put(String name, int value) {
        return put(name, IntTag.valueOf(value));
    }

    public OptionalTag<CompoundTag> put(String name, long value) {
        return put(name, LongTag.valueOf(value));
    }

    public OptionalTag<CompoundTag> put(String name, float value) {
        return put(name, FloatTag.valueOf(value));
    }

    public OptionalTag<CompoundTag> put(String name, double value) {
        return put(name, DoubleTag.valueOf(value));
    }

    public OptionalTag<CompoundTag> put(String name, byte[] value) {
        return put(name, (Tag) new ByteArrayTag(value));
    }

    public OptionalTag<CompoundTag> put(String name, String value) {
        return put(name, StringTag.valueOf(value));
    }

    public OptionalTag<CompoundTag> put(String name, List<? extends Tag> value) {
        if (value instanceof Tag tag) {
            return put(name, tag);
        } else {
            return put(name, (Tag) value.stream().collect(ListTag::new, ListTag::add, AbstractCollection::addAll));
        }
    }

    public OptionalTag<CompoundTag> put(String name, Map<String, ? extends Tag> value) {
        CompoundTag tag = new CompoundTag();
        value.forEach(tag::put);
        return put(name, tag);
    }

    public OptionalTag<CompoundTag> put(String name, int[] value) {
        return put(name, (Tag) new IntArrayTag(value));
    }

    public OptionalTag<CompoundTag> put(String name, long[] value) {
        return put(name, (Tag) new LongArrayTag(value));
    }

    public OptionalTag<CompoundTag> put(String name, @Nullable UUID value) {
        OptionalTag<CompoundTag> optionalTag = asCompound();
        if (value != null) optionalTag.ifPresent(tag -> tag.putUUID(name, value));
        return optionalTag;
    }

    public <U> OptionalTag<CompoundTag> put(@Nullable U value, BiConsumer<U, CompoundTag> serializer) {
        OptionalTag<CompoundTag> optionalTag = asCompound();
        if (value != null) optionalTag.ifPresent(tag -> serializer.accept(value, tag));
        return optionalTag;
    }

    public <U> OptionalTag<CompoundTag> put(String name, @Nullable U value, Function<U, Tag> serializer) {
        OptionalTag<CompoundTag> optionalTag = asCompound();
        if (value != null) {
            Tag tag = serializer.apply(value);
            if (tag != null) {
                return optionalTag.put(name, tag);
            }
        }
        return optionalTag;
    }

    public OptionalTag<CompoundTag> computeIfAbsent(String name, Function<String, Tag> factory) {
        OptionalTag<CompoundTag> compound = asCompound();
        compound.ifPresent(tag -> {
            if (!tag.contains(name)) {
                tag.put(name, factory.apply(name));
            }
        });
        return compound;
    }

    public OptionalTag<CompoundTag> computeIfAbsent(String name, Supplier<Tag> factory) {
        return computeIfAbsent(name, __ -> factory.get());
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

    // List ============================================================================================================

    public OptionalTag<Tag> get(int index) {
        return value instanceof ListTag list && list.size() > index ? of(list.get(index)) : empty();
    }

    public <U extends Tag> OptionalTag<U> get(int index, byte type) {
        return value instanceof ListTag list && list.getElementType() == type && list.size() > index ? of(list.get(index)).as(type) : empty();
    }

    public Optional<Byte> getByte(int index) {
        return get(index).asByte();
    }

    public Optional<Short> getShort(int index) {
        return get(index).asShort();
    }

    public OptionalInt getInt(int index) {
        return get(index).asInt();
    }

    public OptionalLong getLong(int index) {
        return get(index).asLong();
    }

    public Optional<Float> getFloat(int index) {
        return get(index).asFloat();
    }

    public OptionalDouble getDouble(int index) {
        return get(index).asDouble();
    }

    public Optional<byte[]> getByteArray(int index) {
        return get(index).asByteArray();
    }

    public Optional<String> getString(int index) {
        return get(index).asString();
    }

    public OptionalTag<ListTag> getList(int index) {
        return get(index).asList();
    }

    public OptionalTag<CompoundTag> getCompound(int index) {
        return get(index).asCompound();
    }

    public Optional<int[]> getIntArray(int index) {
        return get(index).asIntArray();
    }

    public Optional<long[]> getLongArray(int index) {
        return get(index).asLongArray();
    }

    public Stream<Tag> listStream() {
        return value instanceof ListTag list ? list.stream() : Stream.empty();
    }

    // Other

    public static OptionalTag<CompoundTag> of(ItemStack stack) {
        return stack.hasTag() ? of(stack.getTag()) : empty();
    }

    public static OptionalTag<CompoundTag> ofOrCreate(ItemStack stack) {
        return of(stack.getOrCreateTag());
    }
}
