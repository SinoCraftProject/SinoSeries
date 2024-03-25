package games.moegirl.sinocraft.sinocore.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 提供一些函数功能
 */
public class Functions {

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
                    throw new IllegalArgumentException("Failed to invoke " + aClass.getCanonicalName() + "(" + p1.getName() + ")", ex);
                }
            };
        } catch (NoSuchMethodException ex) {
            throw new IllegalArgumentException("Can't find " + aClass.getCanonicalName() + "(" + p1.getName() + ")", ex);
        }
    }

    public static <T, R> Function<T, R> toFunction(Callable<T, R> callable) {
        return t -> {
            try {
                return callable.call(t);
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to call function with " + t, e);
            }
        };
    }

    public interface Callable<T, R> {
        R call(T t) throws Exception;
    }
}
