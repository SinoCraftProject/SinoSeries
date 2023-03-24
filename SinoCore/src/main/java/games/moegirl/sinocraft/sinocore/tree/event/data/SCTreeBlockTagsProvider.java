package games.moegirl.sinocraft.sinocore.tree.event.data;

import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SCTreeBlockTagsProvider extends BlockTagsProvider {

    protected static final Map<ResourceLocation, TagKey<Block>> LOG_BLOCK_TAGS = new HashMap<>();

    protected final List<Tree> treeTypes;

    public SCTreeBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                   String modId, @Nullable ExistingFileHelper existingFileHelper,
                                   List<Tree> treeTypes) {
        super(output, lookupProvider, modId, existingFileHelper);

        this.treeTypes = treeTypes;
    }

    @Override
    public String getName() {
        return "Tree " + super.getName();
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (var tree : treeTypes) {
            var treeLogsTagKey = makeLogsTag(tree.name);
            LOG_BLOCK_TAGS.put(tree.name, treeLogsTagKey);

            tag(treeLogsTagKey).add(tree.getBlock(TreeBlockType.LOG),
                    tree.getBlock(TreeBlockType.STRIPPED_LOG),
                    tree.getBlock(TreeBlockType.LOG_WOOD),
                    tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD));

            tag(BlockTags.SAPLINGS).add(tree.getBlock(TreeBlockType.SAPLING));
            tag(BlockTags.LOGS).addTag(treeLogsTagKey);
            tag(BlockTags.LOGS_THAT_BURN).addTag(treeLogsTagKey);
            tag(BlockTags.FLOWER_POTS).add(tree.getBlock(TreeBlockType.POTTED_SAPLING));
            tag(BlockTags.LEAVES).add(tree.getBlock(TreeBlockType.LEAVES));
            tag(BlockTags.PLANKS).add(tree.getBlock(TreeBlockType.PLANKS));
            tag(BlockTags.WOODEN_BUTTONS).add(tree.getBlock(TreeBlockType.BUTTON));
            tag(BlockTags.WOODEN_DOORS).add(tree.getBlock(TreeBlockType.DOOR));
            tag(BlockTags.WOODEN_STAIRS).add(tree.getBlock(TreeBlockType.STAIRS));
            tag(BlockTags.WOODEN_SLABS).add(tree.getBlock(TreeBlockType.SLAB));
            tag(BlockTags.WOODEN_FENCES).add(tree.getBlock(TreeBlockType.FENCE));
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(tree.getBlock(TreeBlockType.PRESSURE_PLATE));
            tag(BlockTags.WOODEN_TRAPDOORS).add(tree.getBlock(TreeBlockType.TRAPDOOR));
            tag(BlockTags.STANDING_SIGNS).add(tree.getBlock(TreeBlockType.SIGN));
            tag(BlockTags.WALL_SIGNS).add(tree.getBlock(TreeBlockType.WALL_SIGN));
            tag(BlockTags.CEILING_HANGING_SIGNS).add(tree.getBlock(TreeBlockType.HANGING_SIGN));
            tag(BlockTags.WALL_HANGING_SIGNS).add(tree.getBlock(TreeBlockType.WALL_HANGING_SIGN));
            tag(BlockTags.FENCE_GATES).add(tree.getBlock(TreeBlockType.FENCE_GATE));
            tag(Tags.Blocks.FENCE_GATES_WOODEN).add(tree.getBlock(TreeBlockType.FENCE_GATE));
        }
    }

    protected TagKey<Block> makeLogsTag(ResourceLocation tree) {
        return BlockTags.create(makeLogsTagName(tree));
    }

    protected ResourceLocation makeLogsTagName(ResourceLocation tree) {
        return new ResourceLocation(tree.getNamespace(), tree.getPath() + "_logs");
    }
}
