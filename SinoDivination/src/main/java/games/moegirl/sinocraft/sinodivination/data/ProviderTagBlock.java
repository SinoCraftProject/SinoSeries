package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractBlockTagsProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
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
        tag(SDTags.SPAWN_DRAGONLIVER_MELON_BLOCK).addTag(Tags.Blocks.GRAVEL).addTag(BlockTags.DIRT);

        addPickaxe(SDBlocks.ORE_JADE.get(), SDBlocks.ORE_NITER.get(), SDBlocks.ORE_SULPHUR.get());
        addIronTool(SDBlocks.ORE_JADE.get());
        addStoneTool(SDBlocks.ORE_NITER.get(), SDBlocks.ORE_SULPHUR.get());

        tag(SDTags.FORGE_ORES_SULFUR).add(SDBlocks.ORE_SULPHUR.get());
        tag(SDTags.FORGE_ORES_NITER).add(SDBlocks.ORE_NITER.get());
        tag(SDTags.FORGE_ORES_JADE).add(SDBlocks.ORE_JADE.get());
    }
}
