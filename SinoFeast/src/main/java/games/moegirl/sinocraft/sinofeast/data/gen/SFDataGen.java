package games.moegirl.sinocraft.sinofeast.data.gen;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.block.SFBlockItems;
import games.moegirl.sinocraft.sinofeast.block.SFBlocks;
import games.moegirl.sinocraft.sinofeast.data.gen.lang.SFLanguageProviderENUS;
import games.moegirl.sinocraft.sinofeast.data.gen.lang.SFLanguageProviderZHCN;
import games.moegirl.sinocraft.sinofeast.data.gen.tag.SFBlockTagsProvider;
import games.moegirl.sinocraft.sinofeast.data.gen.tag.SFItemTagsProvider;
import games.moegirl.sinocraft.sinofeast.item.SFItems;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoFeast.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SFDataGen {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();
        var exHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        if (event.includeClient()) {
            gen.addProvider(true, new SFBlockStateProvider(output, SinoFeast.MODID, exHelper, SFBlocks.BLOCKS));
            gen.addProvider(true, new SFItemModelProvider(output, SinoFeast.MODID, exHelper, SFBlockItems.BLOCK_ITEMS, SFItems.ITEMS));
        }

        if (event.includeServer()) {
            gen.addProvider(true, new SFLanguageProviderZHCN(output, SinoFeast.MODID));
            gen.addProvider(true, new SFLanguageProviderENUS(output, SinoFeast.MODID));

            gen.addProvider(true, new SFLootTableProvider(output, SinoFeast.MODID));

            var blockTagsProvider = new SFBlockTagsProvider(output, lookupProvider, SinoFeast.MODID, exHelper);
            gen.addProvider(true, blockTagsProvider);
            gen.addProvider(true, new SFItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), SinoFeast.MODID, exHelper));
        }
    }
}
