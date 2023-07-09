package games.moegirl.sinocraft.sinodivination.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.tag.AbstractBlockTagsProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.data.event.GatherDataEvent;

class ProviderTagBlock extends AbstractBlockTagsProvider {

    public ProviderTagBlock(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), SinoDivination.MODID, event.getExistingFileHelper());
    }

    @Override
    protected void addToTags() {
        tag(SDTags.HEAT_SOURCE).addTag(BlockTags.FIRE).addTag(BlockTags.CAMPFIRES);
        add(SDTags.HEAT_SOURCE, Blocks.LAVA, Blocks.TORCH, Blocks.REDSTONE_TORCH, Blocks.SOUL_TORCH);
        tag(SDTags.FIRE_SOURCE).addTag(BlockTags.FIRE).addTag(BlockTags.CAMPFIRES);
    }
}
