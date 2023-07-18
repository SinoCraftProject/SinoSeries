package games.moegirl.sinocraft.sinofeast.data.gen;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.block.SFBlockItems;
import games.moegirl.sinocraft.sinofeast.block.SFBlocks;
import games.moegirl.sinocraft.sinofeast.data.gen.lang.SFLanguageProviderENUS;
import games.moegirl.sinocraft.sinofeast.data.gen.lang.SFLanguageProviderZHCN;
import games.moegirl.sinocraft.sinofeast.item.SFItems;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoFeast.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SFDataGen {
    @SubscribeEvent
    public static void onRegisterPredicates(GatherDataEvent event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();
        var exHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            gen.addProvider(true, new SFBlockStateProvider(output, SinoFeast.MODID, exHelper, SFBlocks.BLOCKS));
            gen.addProvider(true, new SFItemModelProvider(output, SinoFeast.MODID, exHelper, SFBlockItems.BLOCK_ITEMS, SFItems.ITEMS));
        }

        if (event.includeServer()) {
            gen.addProvider(true, new SFLanguageProviderZHCN(output, SinoFeast.MODID));
            gen.addProvider(true, new SFLanguageProviderENUS(output, SinoFeast.MODID));

            gen.addProvider(true, new SFLootTableProvider(output, SinoFeast.MODID));
        }
    }
}
