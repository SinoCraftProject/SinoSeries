package games.moegirl.sinocraft.sinocore.data.gen;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SinoCore Data Generator
 */
public class DataGeneratorManager {
    private static final Map<String, List<IDataGenerator>> DATA_GENERATORS = new ConcurrentHashMap<>();

    /**
     * 创建用于 DataGenerator
     *
     * @param modId modid
     * @return DataGenerator
     */
    public synchronized static IDataGenerator createDataGenerator(String modId) {
        var provider = _createDataProvider(modId);
        DATA_GENERATORS.computeIfAbsent(modId, __ -> new ArrayList<>())
                .add(provider);
        return provider;
    }

    @ExpectPlatform
    static IDataGenerator _createDataProvider(String modId) {
        throw new AssertionError();
    }
}
