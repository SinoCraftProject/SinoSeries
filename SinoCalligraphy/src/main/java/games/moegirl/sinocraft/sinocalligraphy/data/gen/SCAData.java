package games.moegirl.sinocraft.sinocalligraphy.data.gen;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.block.SCABlockItems;
import games.moegirl.sinocraft.sinocalligraphy.block.SCABlocks;
import games.moegirl.sinocraft.sinocalligraphy.data.gen.advancement.SCAAdvancementGeneratorBrush;
import games.moegirl.sinocraft.sinocalligraphy.data.gen.lang.SCALanguageProviderENUS;
import games.moegirl.sinocraft.sinocalligraphy.data.gen.lang.SCALanguageProviderZHCN;
import games.moegirl.sinocraft.sinocalligraphy.data.gen.lang.SCALanguageProviderZHTW;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = SinoCalligraphy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SCAData {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var exHelper = event.getExistingFileHelper();
        var output = gen.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        if (event.includeClient()) {
            gen.addProvider(true, new SCAItemModelProvider(output, SinoCalligraphy.MODID, exHelper, SCAItems.ITEMS, SCABlockItems.BLOCK_ITEMS));

            gen.addProvider(true, new SCABlockStateProvider(output, SinoCalligraphy.MODID, exHelper, SCABlocks.BLOCKS));
        }

        if (event.includeServer()) {
            var blockTagsProvider = new SCABlockTagsProvider(output, lookupProvider, SinoCalligraphy.MODID, exHelper);
            gen.addProvider(true, blockTagsProvider);
            gen.addProvider(true, new SCAItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), SinoCalligraphy.MODID, exHelper));

            gen.addProvider(true, new ForgeAdvancementProvider(output, lookupProvider, exHelper, List.of(new SCAAdvancementGeneratorBrush(SinoCalligraphy.MODID))));

            gen.addProvider(true, new SCARecipeProvider(output, SinoCalligraphy.MODID));

            gen.addProvider(true, new SCALanguageProviderZHCN(output, SinoCalligraphy.MODID, "zh_cn"));
            gen.addProvider(true, new SCALanguageProviderZHTW(output, SinoCalligraphy.MODID, "zh_tw"));
            gen.addProvider(true, new SCALanguageProviderZHTW(output, SinoCalligraphy.MODID, "zh_hk"));
            gen.addProvider(true, new SCALanguageProviderENUS(output, SinoCalligraphy.MODID, "en_us"));
        }
    }
}
