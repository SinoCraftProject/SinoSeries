package games.moegirl.sinocraft.sinodivination.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractBlockTagsProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.data.SDTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.data.event.GatherDataEvent;

class SDBlockTagProvider extends AbstractBlockTagsProvider {

    public SDBlockTagProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), SinoDivination.MODID, event.getExistingFileHelper());
    }

    @Override
    protected void addToTags() {
        tag(SDTags.HEAT_SOURCE).addTag(BlockTags.FIRE).addTag(BlockTags.CAMPFIRES);
        add(SDTags.HEAT_SOURCE, Blocks.LAVA, Blocks.TORCH, Blocks.REDSTONE_TORCH, Blocks.SOUL_TORCH);
        tag(SDTags.FIRE_SOURCE).addTag(BlockTags.FIRE).addTag(BlockTags.CAMPFIRES);
        chest(SDBlocks.COTINUS_CHEST, SDTags.COTINUS_BLOCK, false);
        chest(SDBlocks.COTINUS_TRAPPED_CHEST, SDTags.COTINUS_BLOCK, true);
        chest(SDBlocks.SOPHORA_CHEST, SDTags.SOPHORA_BLOCK, false);
        chest(SDBlocks.SOPHORA_TRAPPED_CHEST, SDTags.SOPHORA_BLOCK, true);
    }
}
