package games.moegirl.sinocraft.sinocore.old.api.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utils for Supplier
 */
public class Functions {

    /**
     * A supplier with decorator
     *
     * @param constructor object constructor
     * @param decorator   decorator
     * @param <T>         type
     * @return supplier
     */
    public static <T> Supplier<T> decorate(Supplier<T> constructor, Consumer<T> decorator) {
        return () -> {
            T value = constructor.get();
            decorator.accept(value);
            return value;
        };
    }

    /**
     * A supplier with decorator
     *
     * @param constructor object constructor
     * @param decorator   decorator
     * @param <T>         type
     * @return supplier
     */
    public static <T> Supplier<T> decorate(Supplier<T> constructor, Function<T, T> decorator) {
        return () -> decorator.apply(constructor.get());
    }

    public static <T, R> Supplier<R> curry(Function<T, R> function, T value) {
        return () -> function.apply(value);
    }

    public static <T> Supplier<T> constructor(Class<? extends T> aClass) {
        return () -> {
            try {
                return aClass.getConstructor().newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("Can't create " + aClass.getCanonicalName() + " by no-parameter public constructor.", e);
            }
        };
    }

    public static <T, P1> Supplier<T> constructor(Class<? extends T> aClass, Class<P1> p1, Supplier<P1> sup) {
        try {
            Constructor<? extends T> c1 = aClass.getConstructor(p1);
            return () -> {
                try {
                    return c1.newInstance(sup.get());
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                    throw new IllegalArgumentException("Can't create " + aClass.getCanonicalName() + " by public constructor with " + p1.getName(), ex);
                }
            };
        } catch (NoSuchMethodException ex) {
            try {
                Constructor<? extends T> c0 = aClass.getConstructor();
                return () -> {
                    try {
                        return c0.newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new IllegalArgumentException("Can't create " + aClass.getCanonicalName() + " by no-parameter public constructor.", e);
                    }
                };
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("Can't find constructor with no-parameter or " + p1.getName(), ex);
            }
        }
    }

    public static <T, P1> Function<P1, T> constructor(Class<? extends T> aClass, Class<P1> p1) {
        try {
            Constructor<? extends T> c1 = aClass.getConstructor(p1);
            return p -> {
                try {
                    return c1.newInstance(p);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                    throw new IllegalArgumentException("Can't create " + aClass.getCanonicalName() + " by public constructor with " + p1.getName(), ex);
                }
            };
        } catch (NoSuchMethodException ex) {
            try {
                Constructor<? extends T> c0 = aClass.getConstructor();
                return p -> {
                    try {
                        return c0.newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new IllegalArgumentException("Can't create " + aClass.getCanonicalName() + " by no-parameter public constructor.", e);
                    }
                };
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("Can't find constructor with no-parameter or " + p1.getName(), ex);
            }
        }
    }
}
