package games.moegirl.sinocraft.sinodeco.data.gen.tag;

import games.moegirl.sinocraft.sinocore.data.gen.tag.AbstractBlockTagsProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

public class BlockTagsProvider extends AbstractBlockTagsProvider {
    public BlockTagsProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.WALLS).add(SDBlocks.MARBLE_BALUSTRADE.get());
        tag(SDBlockTags.MARBLE_BUILDING_BLOCKS).add(SDBlocks.MARBLE_BLOCK.get(), SDBlocks.CHISELED_MARBLE.get(),
                SDBlocks.MARBLE_PILLAR.get(), SDBlocks.MARBLE_STAIRS.get(), SDBlocks.MARBLE_SLAB.get(),
                SDBlocks.SMOOTH_MARBLE.get(), SDBlocks.SMOOTH_MARBLE_STAIRS.get(), SDBlocks.SMOOTH_MARBLE_SLAB.get(),
                SDBlocks.MARBLE_BRICKS.get(), SDBlocks.MARBLE_BALUSTRADE.get());

        tag(BlockTags.LEAVES).add(SDBlocks.PEACH_LEAVES.get());
        tag(BlockTags.SAPLINGS).add(SDBlocks.PEACH_SAPLING.get());

        tag(SDBlockTags.PEACH_LOGS).add(SDBlocks.PEACH_LOG.get(), SDBlocks.PEACH_WOOD.get(), SDBlocks.STRIPPED_PEACH_LOG.get(), SDBlocks.STRIPPED_PEACH_WOOD.get());
        tag(BlockTags.LOGS_THAT_BURN).addTag(SDBlockTags.PEACH_LOGS);
        tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(SDBlocks.PEACH_LOG.get());

        tag(BlockTags.PLANKS).add(SDBlocks.PEACH_PLANKS.get());
        tag(BlockTags.WOODEN_STAIRS).add(SDBlocks.PEACH_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(SDBlocks.PEACH_SLAB.get());
        tag(BlockTags.WOODEN_FENCES).add(SDBlocks.PEACH_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(SDBlocks.PEACH_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS).add(SDBlocks.PEACH_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(SDBlocks.PEACH_TRAPDOOR.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(SDBlocks.PEACH_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_BUTTONS).add(SDBlocks.PEACH_BUTTON.get());
        tag(BlockTags.STANDING_SIGNS).add(SDBlocks.PEACH_SIGN.get());
        tag(BlockTags.WALL_SIGNS).add(SDBlocks.PEACH_WALL_SIGN.get());
        tag(BlockTags.CEILING_HANGING_SIGNS).add(SDBlocks.PEACH_HANGING_SIGN.get());
        tag(BlockTags.WALL_HANGING_SIGNS).add(SDBlocks.PEACH_WALL_HANGING_SIGN.get());
    }
}
