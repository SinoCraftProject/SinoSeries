package games.moegirl.sinocraft.sinocore.tree.data;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractBlockModelProvider;
import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeType;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SCTreeItemModelProvider extends AbstractItemModelProvider {

    protected final List<TreeType> treeTypes;

    public SCTreeItemModelProvider(PackOutput output, String modid, ExistingFileHelper exHelper, List<TreeType> treeTypes) {
        super(output, modid, exHelper);

        this.treeTypes = treeTypes;
    }

    @Override
    public @NotNull String getName() {
        return super.getName() + " Tree ItemModels";
    }

    @Override
    protected void registerModels() {
        for (var tree : treeTypes) {
            handheld(tree.getBlock(TreeBlockType.SAPLING));
            blockItem(tree.getBlock(TreeBlockType.LOG));
            blockItem(tree.getBlock(TreeBlockType.STRIPPED_LOG));
            blockItem(tree.getBlock(TreeBlockType.LOG_WOOD));
            blockItem(tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD));
            blockItem(tree.getBlock(TreeBlockType.PLANKS));
            blockItem(tree.getBlock(TreeBlockType.LEAVES));

            // Todo.
        }
    }
}
