package games.moegirl.sinocraft.sinodeco.data.gen.tag;

import games.moegirl.sinocraft.sinocore.data.gen.tag.AbstractItemTagsProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.block.item.SDBlockItems;
import games.moegirl.sinocraft.sinodeco.item.SDItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

public class ItemTagsProvider extends AbstractItemTagsProvider {
    public ItemTagsProvider(IDataGenContext context, TagsProvider<Block> blockTagsProvider) {
        super(context, blockTagsProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(SDItemTags.MARBLE_BLOCKS).add(SDBlockItems.MARBLE_BLOCK.get(),
                SDBlockItems.CHISELED_MARBLE.get(), SDBlockItems.MARBLE_PILLAR.get());

        tag(ItemTags.LEAVES).add(SDBlockItems.PEACH_LEAVES.get());
        tag(ItemTags.SAPLINGS).add(SDBlockItems.PEACH_SAPLING.get());

        tag(SDItemTags.PEACH_LOGS).add(SDBlockItems.PEACH_LOG.get(), SDBlockItems.PEACH_WOOD.get(), SDBlockItems.STRIPPED_PEACH_LOG.get(), SDBlockItems.STRIPPED_PEACH_WOOD.get());
        tag(ItemTags.LOGS_THAT_BURN).addTag(SDItemTags.PEACH_LOGS);

        tag(ItemTags.PLANKS).add(SDBlockItems.PEACH_PLANKS.get());
        tag(ItemTags.WOODEN_STAIRS).add(SDBlockItems.PEACH_STAIRS.get());
        tag(ItemTags.WOODEN_SLABS).add(SDBlockItems.PEACH_SLAB.get());
        tag(ItemTags.WOODEN_FENCES).add(SDBlockItems.PEACH_FENCE.get());
        tag(ItemTags.FENCE_GATES).add(SDBlockItems.PEACH_FENCE_GATE.get());
        tag(ItemTags.WOODEN_DOORS).add(SDBlockItems.PEACH_DOOR.get());
        tag(ItemTags.WOODEN_TRAPDOORS).add(SDBlockItems.PEACH_TRAPDOOR.get());
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(SDBlockItems.PEACH_PRESSURE_PLATE.get());
        tag(ItemTags.WOODEN_BUTTONS).add(SDBlockItems.PEACH_BUTTON.get());
        tag(ItemTags.SIGNS).add(SDBlockItems.PEACH_SIGN.get());
        tag(ItemTags.HANGING_SIGNS).add(SDBlockItems.PEACH_HANGING_SIGN.get());

        tag(ItemTags.BOATS).add(SDItems.PEACH_BOAT.get());
        tag(ItemTags.CHEST_BOATS).add(SDItems.PEACH_CHEST_BOAT.get());
    }
}
