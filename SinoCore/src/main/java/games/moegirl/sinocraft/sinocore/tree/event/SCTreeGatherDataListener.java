package games.moegirl.sinocraft.sinocore.tree.event;

import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SCTreeGatherDataListener {
    private final String modid;
    public SCTreeGatherDataListener(String modid) {
        this.modid = modid;
    }

    @SubscribeEvent
    public void onGatherEvent(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var exHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        var trees = TreeRegistry.getRegistry().get(modid);

        if (event.includeClient()) {
            generator.addProvider(true, new ProviderBlockState(output, modid, exHelper, trees));
            generator.addProvider(true, new ProviderItemModel(output, modid, exHelper, trees));
        }

        if (event.includeServer()) {
            var blockTagsProvider = new ProviderBlockTags(output, lookupProvider, modid, exHelper, trees);
            generator.addProvider(true, blockTagsProvider);
            generator.addProvider(true, new ProviderItemTags(output, lookupProvider, blockTagsProvider, modid, exHelper, trees));

            generator.addProvider(true, new ProviderRecipe(output, trees));
            generator.addProvider(true, new ProviderBlockLootTable(output, trees));

            generator.addProvider(true, new ProviderFeatures(output, lookupProvider, modid, trees));
        }
    }
}
