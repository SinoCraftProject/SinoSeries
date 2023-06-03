package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.data.lang.*;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoFoundation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SFDData {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var exHelper = event.getExistingFileHelper();
        var output = gen.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        if (event.includeClient()) {
            gen.addProvider(true, new SFDItemModelProvider(output, SinoFoundation.MODID, exHelper, SFDItems.ITEMS, SFDBlockItems.ITEMS));
            gen.addProvider(true, new SFDBlockStateProvider(output, SinoFoundation.MODID, exHelper, SFDBlocks.BLOCKS));
        }

        if (event.includeServer()) {
            gen.addProvider(true, new SFDLanguageProviderZHCN(output, SinoFoundation.MODID, "zh_cn"));
            gen.addProvider(true, new SFDLanguageProviderZHHK(output, SinoFoundation.MODID, "zh_hk"));
            gen.addProvider(true, new SFDLanguageProviderZHTW(output, SinoFoundation.MODID, "zh_tw"));
            gen.addProvider(true, new SFDLanguageProviderZHL(output, SinoFoundation.MODID, "lzh"));
            gen.addProvider(true, new SFDLanguageProviderENUS(output, SinoFoundation.MODID, "en_us"));

            var blockTagsProvider = new SFDBlockTagsProvider(output, lookupProvider, SinoFoundation.MODID, exHelper);
            gen.addProvider(true, blockTagsProvider);
            gen.addProvider(true, new SFDItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), SinoFoundation.MODID, exHelper));

            gen.addProvider(true, new SFDRecipeProvider(output, SinoFoundation.MODID));
            gen.addProvider(true, new SFDBlockLootTableProvider(output, SinoFoundation.MODID));
        }
    }
}
