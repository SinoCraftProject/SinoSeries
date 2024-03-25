package games.moegirl.sinocraft.sinocore.utility;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * 用于强转的辅助接口，可用于切换泛型
 */
public interface EasyCast {

    static <T> T forceCast(Object o) {
        return (T) o;
    }

    static <T> Optional<T> safeCast(Object o, Class<? extends T> aClass) {
        return aClass.isInstance(o) ? Optional.of((T) o) : Optional.empty();
    }

    default <T> T cast() {
        return forceCast(this);
    }

    interface Self<T> extends EasyCast {

        default T self() {
            return safeCast(this, selfType()).orElseThrow();
        }

        default Class<T> selfType() {
            for (Type genericInterface : getClass().getGenericInterfaces()) {
                if (genericInterface instanceof ParameterizedType pType && pType.getRawType() == Self.class) {
                    Type argumentType = pType.getActualTypeArguments()[0];
                    if (argumentType instanceof Class<?> aClass) {
                        return forceCast(aClass);
                    }
                }
                throw new IllegalArgumentException("Type of T in Self<T> is not a Class.");
            }
            throw new RuntimeException("Object must implement Self interface (but why not?)");
        }
    }
}
