package games.moegirl.sinocraft.sinocore.utility;

import java.util.HashMap;
import java.util.Map;

/**
 * 为调试提供一个暂时保存变量的地方
 *
 * @author luqin2007
 */
public interface Debug {

    Map<String, Object> MAP = new HashMap<>();

    static void add(String name, Object value) {
        MAP.put(name, value);
    }

    static <T> T get(String name) {
        return (T) MAP.get(name);
    }

    static boolean contains(String name) {
        return MAP.containsKey(name);
    }

    static <T> boolean contains(String name, Class<? extends T> type) {
        return type.isInstance(MAP.get(name));
    }
}
