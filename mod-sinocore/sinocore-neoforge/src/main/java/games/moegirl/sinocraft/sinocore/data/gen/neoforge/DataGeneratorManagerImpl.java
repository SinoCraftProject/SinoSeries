package games.moegirl.sinocraft.sinocore.data.gen.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenerator;

public class DataGeneratorManagerImpl {
    public static DataGenerator _createDataProvider(String modId) {
        return new NeoForgeDataGenerator(modId);
    }
}
