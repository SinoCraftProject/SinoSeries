package games.moegirl.sinocraft.sinocore.registry;

import java.util.function.Supplier;

public interface IRegistry<T> {

    void register();

    <R extends T> IRef<T, R> register(String name, Supplier<? extends R> supplier);
}
