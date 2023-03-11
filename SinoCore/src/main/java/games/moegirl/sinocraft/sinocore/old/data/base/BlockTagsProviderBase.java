package games.moegirl.sinocraft.sinocore.old.data.base;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public abstract class BlockTagsProviderBase extends BlockTagsProvider {

    public BlockTagsProviderBase(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    protected abstract void addTags();

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addTags();
    }

    public void addPickaxe(Block... blocks) {
        add(BlockTags.MINEABLE_WITH_PICKAXE, blocks);
    }

    public void addAxe(Block... blocks) {
        add(BlockTags.MINEABLE_WITH_AXE, blocks);
    }

    public void addShovel(Block... blocks) {
        add(BlockTags.MINEABLE_WITH_SHOVEL, blocks);
    }

    public void addHoe(Block... blocks) {
        add(BlockTags.MINEABLE_WITH_HOE, blocks);
    }

    public void addStoneTool(Block... blocks) {
        add(BlockTags.NEEDS_STONE_TOOL, blocks);
    }

    public void addIronTool(Block... blocks) {
        add(BlockTags.NEEDS_IRON_TOOL, blocks);
    }

    public void addDiamondTool(Block... blocks) {
        add(BlockTags.NEEDS_DIAMOND_TOOL, blocks);
    }

    public void add(TagKey<Block> tag, Block... blocks) {
        tag(tag).add(blocks);
    }

    public void add(TagKey<Block> tag, Collection<? extends Block> blocks) {
        tag(tag).add(blocks.toArray(Block[]::new));
    }

    public void add(TagKey<Block> tag, Stream<? extends Block> blocks) {
        tag(tag).add(blocks.toArray(Block[]::new));
    }
}
