package games.moegirl.sinocraft.sinocore.utility;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 可空类型接口，类似 {@link java.util.Optional}
 *
 * @param <T>
 */
public interface IOptional<T> {

    boolean isPresent();

    default boolean isEmpty() {
        return !isPresent();
    }

    T get();

    default void ifPresent(Consumer<? super T> action) {
        if (isPresent()) {
            action.accept(get());
        }
    }

    default Stream<T> stream() {
        return isPresent() ? Stream.generate(this::get) : Stream.empty();
    }

    default T orElse(T value) {
        return isPresent() ? get() : value;
    }

    default T orElseGet(Supplier<T> value) {
        return isPresent() ? get() : value.get();
    }
}
