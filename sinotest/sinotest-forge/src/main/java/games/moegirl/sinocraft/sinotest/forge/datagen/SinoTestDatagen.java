package games.moegirl.sinocraft.sinotest.forge.datagen;

import games.moegirl.sinocraft.sinocore.SinoCorePlatform;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SinoTestDatagen {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        IDataGenContext context = SinoCorePlatform.buildDataGeneratorContext(event, event.getLookupProvider());

        DataGenerator generator = event.getGenerator();
        TestBlockTagsProvider blockProvider;
        generator.addProvider(true, new TestEnLanguageProvider(context));
        generator.addProvider(true, new TestZhLanguageProvider(context));
        generator.addProvider(true, new TestAdvancementProvider(context));
        generator.addProvider(true, new TestBiomeModifierPriovider(context));
        generator.addProvider(true, blockProvider = new TestBlockTagsProvider(context));
        generator.addProvider(true, new TestCodecProvider(context));
        generator.addProvider(true, new TestItemModelProvider(context, TestRegistry.ITEMS));
        generator.addProvider(true, new TestItemTagsProvider(context, blockProvider));
        generator.addProvider(true, new TestLootTableProvider(context));
        generator.addProvider(true, new TestRecipeProvider(context));
    }
}
