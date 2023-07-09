package games.moegirl.sinocraft.sinofoundation.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.ProviderList;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.data.gen.world.SFDBiomeModifierProvider;
import games.moegirl.sinocraft.sinofoundation.data.gen.lang.*;
import games.moegirl.sinocraft.sinofoundation.data.gen.tag.SFDBiomeTagsProvider;
import games.moegirl.sinocraft.sinofoundation.data.gen.tag.SFDBlockTagsProvider;
import games.moegirl.sinocraft.sinofoundation.data.gen.tag.SFDItemTagsProvider;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = SinoFoundation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SFDData {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        ExistingFileHelper exHelper = event.getExistingFileHelper();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(true, new SFDItemModelProvider(output, SinoFoundation.MODID, exHelper, SFDItems.ITEMS, SFDBlockItems.ITEMS));
        gen.addProvider(true, new SFDBlockStateProvider(output, SinoFoundation.MODID, exHelper, SFDBlocks.BLOCKS));

        gen.addProvider(true, new SFDLanguageProviderZHCN(output, SinoFoundation.MODID, "zh_cn"));
        gen.addProvider(true, new SFDLanguageProviderZHHK(output, SinoFoundation.MODID, "zh_hk"));
        gen.addProvider(true, new SFDLanguageProviderZHTW(output, SinoFoundation.MODID, "zh_tw"));
        gen.addProvider(true, new SFDLanguageProviderZHL(output, SinoFoundation.MODID, "lzh"));
        gen.addProvider(true, new SFDLanguageProviderENUS(output, SinoFoundation.MODID, "en_us"));

        var blockTagsProvider = new SFDBlockTagsProvider(output, lookupProvider, SinoFoundation.MODID, exHelper);
        gen.addProvider(true, blockTagsProvider);
        gen.addProvider(true, new SFDItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), SinoFoundation.MODID, exHelper));
        gen.addProvider(true, new SFDBiomeTagsProvider(output, lookupProvider, exHelper));

        gen.addProvider(true, new SFDRecipeProvider(output, SinoFoundation.MODID));
        gen.addProvider(true, new SFDBlockLootTableProvider(output, SinoFoundation.MODID));

        gen.addProvider(true, new ProviderList("sinofoundation datapack and biome modifier")
                .then(() -> new SFDDatapackProvider(output, lookupProvider))
                .then(() -> new SFDBiomeModifierProvider(output, SinoFoundation.MODID)));
    }
}
