package games.moegirl.sinocraft.sinocore.util;

import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    private T value;
    private final Supplier<T> supplier;

    Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        return value == null ? (value = supplier.get()) : value;
    }
}
