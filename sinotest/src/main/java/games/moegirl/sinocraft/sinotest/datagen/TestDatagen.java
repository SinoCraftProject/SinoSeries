package games.moegirl.sinocraft.sinotest.datagen;

import games.moegirl.sinocraft.sinocore.datagen.ForgeProvider;
import games.moegirl.sinocraft.sinocore.registry.IDataProviderRegister;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinotest.SinoTest;
import games.moegirl.sinocraft.sinotest.datagen.gen.*;

import java.util.function.Supplier;

public class TestDatagen {

    public static void registerAll() {
        registerProvider();
        registerData();
    }

    private static void registerProvider() {
        IDataProviderRegister register = RegistryManager.obtainDataProvider(SinoTest.MODID);
        register.put(TestEnLanguageProvider::new);
        register.put(TestZhLanguageProvider::new);
        register.put(TestAdvancementProvider::new);
        register.put(TestBiomeModifierPriovider::new);
        Supplier<TestBlockTagsProvider> blockTags = register.put(TestBlockTagsProvider::new);
        register.put(TestFeatureProvider::new);
        register.put(TestItemModelProvider::new);
        register.put(ctx -> new TestItemTagsProvider(ctx, blockTags.get()));
        register.put(TestLootTableProvider::new);
        register.put(TestRecipeProvider::new);
        register.put(ForgeProvider::new);
    }

    private static void registerData() {
    }
}
