package games.moegirl.sinocraft.sinocore.data.gen;

import com.google.common.collect.ImmutableMap;
import dev.architectury.injectables.annotations.ExpectPlatform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * SinoCore Data Generator
 */
public class DataGeneratorManager {
    private static final Map<String, List<DataGenerator>> DATA_GENERATORS = new ConcurrentHashMap<>();

    public static Map<String, List<DataGenerator>> getDataGenerators() {
        return ImmutableMap.copyOf(DATA_GENERATORS);
    }

    /**
     * 创建 DataGenerator
     *
     * @param modId modid
     */
    public synchronized static void createDataGenerator(String modId, Consumer<DataGenerator> addProviders) {
        var provider = _createDataProvider(modId);
        addProviders.accept(provider);
        DATA_GENERATORS.computeIfAbsent(modId, __ -> new ArrayList<>())
                .add(provider);
    }

    @ExpectPlatform
    static DataGenerator _createDataProvider(String modId) {
        throw new AssertionError();
    }
}
