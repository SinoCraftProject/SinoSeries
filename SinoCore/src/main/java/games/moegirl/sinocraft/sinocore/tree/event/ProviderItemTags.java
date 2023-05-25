package games.moegirl.sinocraft.sinocore.tree.event;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

class ProviderItemTags extends ItemTagsProvider {

    protected final List<Tree> treeTypes;

    public ProviderItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                            TagsProvider<Block> blockTagsProvider, String modid,
                            @Nullable ExistingFileHelper existingFileHelper,
                            List<Tree> treeTypes) {
        super(output, lookupProvider, blockTagsProvider.contentsGetter(), modid, existingFileHelper);
        this.treeTypes = treeTypes;
    }

    @Override
    public @NotNull String getName() {
        return "Tree " + super.getName();
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (var tree : treeTypes) {
            var logsItemTag = ItemTags.create(tree.getDefaultLogTag());

            tree.getBuilder().getBlockFactories().forEach((type, factory) -> {
                if (type.hasItem()) {
                    Item item = tree.getItem(type);

                    if (factory.isAddDefaultItemTag()) {
                        switch (type) {
                            case LOG, STRIPPED_LOG, LOG_WOOD, STRIPPED_LOG_WOOD -> tag(logsItemTag).add(item);
                            case SAPLING -> tag(ItemTags.SAPLINGS).add(item);
                            case LEAVES -> tag(ItemTags.LEAVES).add(item);
                            case PLANKS -> tag(ItemTags.PLANKS).add(item);
                            case BUTTON -> tag(ItemTags.WOODEN_BUTTONS).add(item);
                            case DOOR -> tag(ItemTags.WOODEN_DOORS).add(item);
                            case STAIRS -> tag(ItemTags.WOODEN_STAIRS).add(item);
                            case SLAB -> tag(ItemTags.WOODEN_SLABS).add(item);
                            case FENCE -> tag(ItemTags.WOODEN_FENCES).add(item);
                            case PRESSURE_PLATE -> tag(ItemTags.WOODEN_PRESSURE_PLATES).add(item);
                            case TRAPDOOR -> tag(ItemTags.WOODEN_TRAPDOORS).add(item);
                            case SIGN -> tag(ItemTags.SIGNS).add(item);
                            case HANGING_SIGN -> tag(ItemTags.HANGING_SIGNS).add(item);
                            case FENCE_GATE -> tag(Tags.Items.FENCE_GATES_WOODEN).add(item);
                            case CHEST -> tag(Tags.Items.CHESTS_WOODEN).add(item);
                            case TRAPPED_CHEST -> {
                                tag(Tags.Items.CHESTS_WOODEN).add(item);
                                tag(Tags.Items.CHESTS_TRAPPED).add(item);
                            }
                        }
                    }

                    if (!factory.getItemTags().isEmpty())
                        for (TagKey<Item> tag : factory.getItemTags())
                            tag(tag).add(item);
                }
            });

            tag(ItemTags.LOGS).addTag(logsItemTag);
            tag(ItemTags.LOGS_THAT_BURN).addTag(logsItemTag);
        }
    }
}
