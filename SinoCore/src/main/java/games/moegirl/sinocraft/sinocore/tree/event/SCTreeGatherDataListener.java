package games.moegirl.sinocraft.sinocore.tree.event;

import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import games.moegirl.sinocraft.sinocore.tree.event.data.*;
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
            generator.addProvider(true, new SCTreeBlockStateProvider(output, modid, exHelper, trees));
            generator.addProvider(true, new SCTreeItemModelProvider(output, modid, exHelper, trees));
        }

        if (event.includeServer()) {
            var blockTagsProvider = new SCTreeBlockTagsProvider(output, lookupProvider, modid, exHelper, trees);
            generator.addProvider(true, blockTagsProvider);
            generator.addProvider(true, new SCTreeItemTagsProvider(output, lookupProvider, blockTagsProvider, modid, exHelper, trees));

            generator.addProvider(true, new SCTreeRecipeProvider(output, trees));
            generator.addProvider(true, new SCTreeLootTableProvider(output, trees));

            generator.addProvider(true, new SCTreeFeaturesProvider(output, lookupProvider, modid, trees));
        }
    }
}
