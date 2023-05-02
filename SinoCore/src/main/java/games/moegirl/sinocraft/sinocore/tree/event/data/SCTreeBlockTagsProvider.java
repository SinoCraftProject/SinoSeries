package games.moegirl.sinocraft.sinocore.tree.event.data;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SCTreeBlockTagsProvider extends BlockTagsProvider {

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
            var treeLogsTagKey = BlockTags.create(tree.getDefaultLogTag());

            tree.getBuilder().getBlockFactories().forEach((type, factory) -> {
                if (type.hasBlock()) {
                    Block block = tree.getBlock(type);
                    if (factory.isAddDefaultBlockTag()) {
                        switch (type) {
                            case SAPLING -> tag(BlockTags.SAPLINGS).add(block);
                            case LOG, STRIPPED_LOG, LOG_WOOD, STRIPPED_LOG_WOOD -> tag(treeLogsTagKey).add(block);
                            case POTTED_SAPLING -> tag(BlockTags.FLOWER_POTS).add(block);
                            case LEAVES -> tag(BlockTags.LEAVES).add(block);
                            case PLANKS -> tag(BlockTags.PLANKS).add(block);
                            case BUTTON -> tag(BlockTags.WOODEN_BUTTONS).add(block);
                            case DOOR -> tag(BlockTags.WOODEN_DOORS).add(block);
                            case STAIRS -> tag(BlockTags.WOODEN_STAIRS).add(block);
                            case SLAB -> tag(BlockTags.WOODEN_SLABS).add(block);
                            case FENCE -> tag(BlockTags.WOODEN_FENCES).add(block);
                            case PRESSURE_PLATE -> tag(BlockTags.WOODEN_PRESSURE_PLATES).add(block);
                            case TRAPDOOR -> tag(BlockTags.WOODEN_TRAPDOORS).add(block);
                            case SIGN -> tag(BlockTags.STANDING_SIGNS).add(block);
                            case WALL_SIGN -> tag(BlockTags.WALL_SIGNS).add(block);
                            case HANGING_SIGN -> tag(BlockTags.CEILING_HANGING_SIGNS).add(block);
                            case WALL_HANGING_SIGN -> tag(BlockTags.WALL_HANGING_SIGNS).add(block);
                            case FENCE_GATE -> {
                                tag(BlockTags.FENCE_GATES).add(block);
                                tag(Tags.Blocks.FENCE_GATES_WOODEN).add(block);
                            }
                        }
                    }

                    if (!factory.getBlockTags().isEmpty())
                        for (TagKey<Block> tag : factory.getBlockTags())
                            tag(tag).add(block);
                }
            });

            tag(BlockTags.LOGS).addTag(treeLogsTagKey);
            tag(BlockTags.LOGS_THAT_BURN).addTag(treeLogsTagKey);
        }
    }
}
