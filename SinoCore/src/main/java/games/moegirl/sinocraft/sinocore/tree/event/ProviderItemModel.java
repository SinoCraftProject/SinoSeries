package games.moegirl.sinocraft.sinocore.tree.event;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
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
            handheld(tree.getItem(TreeBlockType.SAPLING));

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

            ResourceLocation name = tree.getBlockObj(TreeBlockType.PLANKS).getId();
            ResourceLocation texPlank = new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
            singleTexture(tree.getBlockObj(TreeBlockType.CHEST).getId().getPath(), mcLoc("item/chest"), "particle", texPlank);
            singleTexture(tree.getBlockObj(TreeBlockType.TRAPPED_CHEST).getId().getPath(), mcLoc("item/chest"), "particle", texPlank);
        }
    }
}
