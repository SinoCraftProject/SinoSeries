package games.moegirl.sinocraft.sinocore.tree.data;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeType;
import games.moegirl.sinocraft.sinocore.tree.depr.event.TreeDataHandler;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SCTreeData {
    private String modid;

    public SCTreeData(String modid) {
        this.modid = modid;
    }

    @SubscribeEvent
    public void onGatherEvent(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var exHelper = event.getExistingFileHelper();

        var trees = Tree.getRegistry().entrySet()
                .stream()
                .filter(e -> e.getKey().getNamespace().equals(modid))
                .map(Map.Entry::getValue)
                .toList();

        if (event.includeClient()) {
            generator.addProvider(true, new SCTreeBlockStateProvider(output, modid, exHelper, trees));
        }

        if (!mItem.isEmpty()) generator.addProvider(true, new TreeDataHandler.TItemModelProvider());
        if (!recipe.isEmpty()) generator.addProvider(true, new TreeDataHandler.TRecipeProvider());
        if (!(blockTags.isEmpty() && itemTags.isEmpty())) {
            BlockTagsProvider b;
            generator.addProvider(true, b = new TreeDataHandler.TBlockTagsProvider());
            if (!itemTags.isEmpty())
                generator.addProvider(true, new TreeDataHandler.TItemTagsProvider(b));
        }
        if (!lootTable.isEmpty()) generator.addProvider(true, new TreeDataHandler.TLootProvider());
        if (!features.isEmpty()) generator.addProvider(true, new TreeDataHandler.TFeatureProvider());
    }
}
