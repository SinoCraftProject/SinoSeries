package games.moegirl.sinocraft.sinocore.old.utility.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import oshi.util.tuples.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 **/
public enum JsonUtils {
    /** 最 佳 单 例 */
    INSTANCE;

    private Gson normal;
    private Gson pretty;
    private Gson noExpose;

    private final List<Pair<Type, BaseSerializer<?>>> serializers = new ArrayList<>();

    JsonUtils() {
        build();
    }

    public Gson getNormal() {
        return rebuild().normal;
    }

    public Gson getPretty() {
        return rebuild().pretty;
    }

    public Gson getNoExpose() {
        return rebuild().noExpose;
    }

    public <T> JsonUtils registerAdapter(Type type, BaseSerializer<T> serializer) {
        serializers.add(new Pair<>(type, serializer));
        return this;
    }

    private JsonUtils rebuild() {
        if (!serializers.isEmpty()) {
            build();
        }
        return this;
    }

    private void build() {
        GsonBuilder builder = new GsonBuilder()
                // 关闭 html 转义
                .disableHtmlEscaping()
                // 开启复杂 Map 的序列化
                .enableComplexMapKeySerialization();

        serializers.forEach(p -> builder.registerTypeAdapter(p.getA(), p.getB()));
        serializers.clear();

        // 无视 @Expose 注解的 Gson 实例
        noExpose = builder.create();

        // 要求 *全部*字段都有 @Expose 注解的 Gson 实例
        builder.excludeFieldsWithoutExposeAnnotation();
        normal = builder.create();

        // 输出的字符串漂亮一点的 Gson 实例 -> 输出到 json 文件（例如合成表）的，好看
        builder.setPrettyPrinting();
        pretty = builder.create();
    }
}
