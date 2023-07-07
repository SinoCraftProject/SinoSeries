package games.moegirl.sinocraft.sinofoundation.data.tag;

import games.moegirl.sinocraft.sinocore.data.tag.AbstractBlockTagsProvider;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.data.tag.SFDBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SFDBlockTagsProvider extends AbstractBlockTagsProvider {

    public SFDBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                String modId, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addToTags() {
        tag(SFDBlockTags.MINECRAFT_WALLS).add(SFDBlocks.MARBLE_WALLS.get());

        addPickaxe(SFDBlocks.JADE_ORE.get(), SFDBlocks.NITER_ORE.get(), SFDBlocks.SULPHUR_ORE.get());
        addIronTool(SFDBlocks.JADE_ORE.get());
        addStoneTool(SFDBlocks.NITER_ORE.get(), SFDBlocks.SULPHUR_ORE.get());

        tag(SFDBlockTags.FORGE_ORES_SULFUR).add(SFDBlocks.SULPHUR_ORE.get());
        tag(SFDBlockTags.FORGE_ORES_NITER).add(SFDBlocks.NITER_ORE.get());
        tag(SFDBlockTags.FORGE_ORES_JADE).add(SFDBlocks.JADE_ORE.get());
        tag(SFDBlockTags.SPAWN_DRAGONLIVER_MELON).addTag(Tags.Blocks.GRAVEL).addTag(BlockTags.DIRT);

        chest(SFDBlocks.COTINUS_CHEST, SFDBlockTags.COTINUS_BLOCK, false);
        chest(SFDBlocks.COTINUS_TRAPPED_CHEST, SFDBlockTags.COTINUS_BLOCK, true);
        chest(SFDBlocks.JUJUBE_CHEST, SFDBlockTags.JUJUBE_BLOCK, false);
        chest(SFDBlocks.JUJUBE_TRAPPED_CHEST, SFDBlockTags.JUJUBE_BLOCK, true);
        chest(SFDBlocks.SOPHORA_CHEST, SFDBlockTags.SOPHORA_BLOCK, false);
        chest(SFDBlocks.SOPHORA_TRAPPED_CHEST, SFDBlockTags.SOPHORA_BLOCK, true);
    }

    private void chest(Supplier<? extends Block> block, TagKey<Block> material, boolean trapped) {
        Block b = block.get();
        tag(Tags.Blocks.CHESTS_WOODEN).add(b);
        tag(BlockTags.GUARDED_BY_PIGLINS).add(b);
        tag(BlockTags.MINEABLE_WITH_AXE).add(b);
        tag(BlockTags.FEATURES_CANNOT_REPLACE).add(b);
        tag(material).add(b);
        if (trapped) {
            tag(Tags.Blocks.CHESTS_TRAPPED).add(b);
        }
    }
}
