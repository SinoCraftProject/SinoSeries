package games.moegirl.sinocraft.sinocore.data.gen.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenerator;

public class DataGeneratorManagerImpl {
    public static IDataGenerator _createDataProvider(String modId) {
        return new NeoForgeDataGenerator(modId);
    }
}
