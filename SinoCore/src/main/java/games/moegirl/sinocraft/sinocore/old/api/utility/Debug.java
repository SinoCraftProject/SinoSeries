package games.moegirl.sinocraft.sinocore.old.api.utility;

import java.util.HashMap;
import java.util.Map;

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
