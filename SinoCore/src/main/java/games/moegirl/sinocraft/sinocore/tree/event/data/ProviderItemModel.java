package games.moegirl.sinocraft.sinocore.tree.event.data;

import games.moegirl.sinocraft.sinocore.data.model.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class ProviderItemModel extends AbstractItemModelProvider {

    protected final List<Tree> treeTypes;

    public ProviderItemModel(PackOutput output, String modid, ExistingFileHelper exHelper, List<Tree> treeTypes) {
        super(output, modid, exHelper);

        this.treeTypes = treeTypes;
    }

    @Override
    public @NotNull String getName() {
        return "Tree " + super.getName();
    }

    @Override
    protected void registerModels() {
        for (var tree : treeTypes) {
            generated(tree.getItem(TreeBlockType.SAPLING));

            blockItem(tree.getBlock(TreeBlockType.LOG));
            blockItem(tree.getBlock(TreeBlockType.STRIPPED_LOG));
            blockItem(tree.getBlock(TreeBlockType.LOG_WOOD));
            blockItem(tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD));
            blockItem(tree.getBlock(TreeBlockType.PLANKS));
            blockItem(tree.getBlock(TreeBlockType.LEAVES));
            blockItem(tree.getBlock(TreeBlockType.STAIRS));
            blockItem(tree.getBlock(TreeBlockType.SLAB));
            blockItem(tree.getBlock(TreeBlockType.PRESSURE_PLATE));
            blockItem(tree.getBlock(TreeBlockType.FENCE_GATE));
            blockItem(tree.getBlock(TreeBlockType.FENCE), "inventory");
            blockItem(tree.getBlock(TreeBlockType.BUTTON), "inventory");
            blockItem(tree.getBlock(TreeBlockType.TRAPDOOR), "bottom");

            generated(tree.getItem(TreeBlockType.DOOR));
            generated(tree.getItem(TreeBlockType.SIGN));
        }
    }
}
