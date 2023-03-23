package games.moegirl.sinocraft.sinocore.tree.data;

import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SCTreeItemTagsProvider extends ItemTagsProvider {

    protected final List<TreeType> treeTypes;

    public SCTreeItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                  TagsProvider<Block> blockTagsProvider, String modid,
                                  @Nullable ExistingFileHelper existingFileHelper,
                                  List<TreeType> treeTypes) {
        super(output, lookupProvider, blockTagsProvider, modid, existingFileHelper);

        this.treeTypes = treeTypes;
    }

    @Override
    public String getName() {
        return super.getName() + " Tree ItemTags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (var tree : treeTypes) {
            var logsItemTag = makeLogsTag(tree.name);
            tag(logsItemTag).add(tree.getBlock(TreeBlockType.LOG).asItem(),
                    tree.getBlock(TreeBlockType.STRIPPED_LOG).asItem(),
                    tree.getBlock(TreeBlockType.LOG_WOOD).asItem(),
                    tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD).asItem());

            tag(ItemTags.SAPLINGS).add(tree.getBlock(TreeBlockType.SAPLING).asItem());
            tag(ItemTags.LOGS_THAT_BURN).addTag(logsItemTag);
            tag(ItemTags.LEAVES).add(tree.getBlock(TreeBlockType.LEAVES).asItem());

            tag(ItemTags.PLANKS).add(tree.getBlock(TreeBlockType.PLANKS).asItem());
            tag(ItemTags.WOODEN_BUTTONS).add(tree.getBlock(TreeBlockType.BUTTON).asItem());
            tag(ItemTags.WOODEN_DOORS).add(tree.getBlock(TreeBlockType.DOOR).asItem());
            tag(ItemTags.WOODEN_STAIRS).add(tree.getBlock(TreeBlockType.STAIRS).asItem());
            tag(ItemTags.WOODEN_SLABS).add(tree.getBlock(TreeBlockType.SLAB).asItem());
            tag(ItemTags.WOODEN_FENCES).add(tree.getBlock(TreeBlockType.FENCE).asItem());
            tag(ItemTags.WOODEN_PRESSURE_PLATES).add(tree.getBlock(TreeBlockType.PRESSURE_PLATE).asItem());
            tag(ItemTags.WOODEN_TRAPDOORS).add(tree.getBlock(TreeBlockType.TRAPDOOR).asItem());
            tag(ItemTags.SIGNS).add(tree.getBlock(TreeBlockType.SIGN).asItem());
            tag(ItemTags.HANGING_SIGNS).add(tree.getBlock(TreeBlockType.HANGING_SIGN).asItem());
            tag(Tags.Items.FENCE_GATES_WOODEN).add(tree.getBlock(TreeBlockType.FENCE_GATE).asItem());
        }
    }

    protected TagKey<Item> makeLogsTag(ResourceLocation tree) {
        return ItemTags.create(makeLogsTagName(tree));
    }

    protected ResourceLocation makeLogsTagName(ResourceLocation tree) {
        return new ResourceLocation(tree.getNamespace(), tree.getPath() + "_logs");
    }
}
