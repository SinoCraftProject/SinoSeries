package games.moegirl.sinocraft.sinocore.data.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class AbstractBlockTagsProvider extends BlockTagsProvider {

    public AbstractBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                     String modId, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    protected abstract void addToTags();

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addToTags();
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

    protected void chest(Supplier<? extends Block> block, TagKey<Block> material, boolean trapped) {
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
