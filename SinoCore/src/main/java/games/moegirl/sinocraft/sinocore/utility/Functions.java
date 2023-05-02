package games.moegirl.sinocraft.sinocore.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 提供一些函数功能
 */
public class Functions {

    public static <T> Function<T, T> compose(Function<T, T> first, Consumer<T> second) {
        return t -> {
            T tt = first.apply(t);
            second.accept(tt);
            return tt;
        };
    }

    public static <A, B, C> Function<A, C> compose(Function<A, B> first, Function<B, C> second) {
        return obj -> second.apply(first.apply(obj));
    }

    public static <T, R> Function<T, R> ext(Supplier<R> sup) {
        return __ -> sup.get();
    }

    public static <T, U, R> BiFunction<T, U, R> lExt(Function<U, R> sup) {
        return (__, u) -> sup.apply(u);
    }

    public static <T, U, R> BiFunction<T, U, R> rExt(Function<T, R> sup) {
        return (t, __) -> sup.apply(t);
    }

    /**
     * 生成一个 Supplier，可以根据给定构造函数构造出无参构造，并通过 Consumer 对其修饰
     */
    public static <T> Supplier<T> constructor(Supplier<T> constructor, Consumer<T> decorator) {
        return () -> {
            T value = constructor.get();
            decorator.accept(value);
            return value;
        };
    }

    /**
     * 生成一个 Supplier，可以根据给定构造函数构造出无参构造，并通过 Function 对其修饰
     */
    public static <T, R> Supplier<R> constructor(Supplier<T> constructor, Function<T, R> decorator) {
        return () -> decorator.apply(constructor.get());
    }

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
                    throw new IllegalArgumentException("Can't create " + aClass.getCanonicalName() + " by public constructor with " + p1.getName(), ex);
                }
            };
        } catch (NoSuchMethodException ex) {
            try {
                Constructor<? extends T> c0 = aClass.getDeclaredConstructor();
                c0.setAccessible(true);
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

    /**
     * 将一个带两个参数的构造转换成 BiFunction
     */
    public static <T, P1, P2> BiFunction<P1, P2, T> constructor(Class<? extends T> aClass, Class<P1> p1, Class<P2> p2) {
        try {
            Constructor<? extends T> c1 = aClass.getDeclaredConstructor(p1, p2);
            c1.setAccessible(true);
            return (_p1, _p2) -> {
                try {
                    return c1.newInstance(_p1, _p2);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                    throw new IllegalArgumentException("Can't create " + aClass.getCanonicalName() + " by public constructor with " + p1.getName(), ex);
                }
            };
        } catch (NoSuchMethodException ex) {
            try {
                Constructor<? extends T> c0 = aClass.getDeclaredConstructor();
                c0.setAccessible(true);
                return (_1, _2) -> {
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
