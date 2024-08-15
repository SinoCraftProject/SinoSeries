package games.moegirl.sinocraft.sinocore.utility;

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
}
