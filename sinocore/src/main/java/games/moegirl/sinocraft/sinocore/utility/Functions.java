package games.moegirl.sinocraft.sinocore.utility;

import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 提供一些函数功能
 */
public class Functions {

    /**
     * 将一个无参构造转换成 Supplier
     */
    public static <T> Supplier<T> constructor(Class<? extends T> aClass) {
        return () -> {
            try {
                Constructor<? extends T> c0 = aClass.getDeclaredConstructor();
                c0.setAccessible(true);
                return c0.newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("Can't create " + aClass.getCanonicalName() + " by no-parameter public constructor.", e);
            }
        };
    }

    public static <T> Consumer<T> compose(Consumer<T> first, Consumer<T> second) {
        if (first == null && second == null) return t -> {};
        if (first == null) return second;
        if (second == null) return first;
        return t -> {
            first.accept(t);
            second.accept(t);
        };
    }

    /**
     * 将一个带一个参数的构造转换成 Function
     */
    public static <T, P1> Function<P1, T> constructor(Class<? extends T> aClass, Class<P1> p1) {
        try {
            Constructor<? extends T> c1 = aClass.getDeclaredConstructor(p1);
            c1.setAccessible(true);
            return p -> {
                try {
                    return c1.newInstance(p);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                    throw new IllegalArgumentException("Failed to invoke " + aClass.getCanonicalName() + "(" + p1.getName() + ")", ex);
                }
            };
        } catch (NoSuchMethodException ex) {
            throw new IllegalArgumentException("Can't find " + aClass.getCanonicalName() + "(" + p1.getName() + ")", ex);
        }
    }

    public static <P> Optional<P> getOrEmpty(Callable<P> sup, Logger logger) {
        try {
            return Optional.ofNullable(sup.call());
        } catch (Exception e) {
            logger.error("Error", e);
            return Optional.empty();
        }
    }

    public static <P> Stream<P> getStreamOrEmpty(Callable<Stream<P>> sup, Logger logger) {
        try {
            return sup.call();
        } catch (Exception e) {
            logger.error("Error", e);
            return Stream.empty();
        }
    }
}
