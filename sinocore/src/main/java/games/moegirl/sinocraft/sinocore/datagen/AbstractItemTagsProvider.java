package games.moegirl.sinocraft.sinocore.datagen;

import games.moegirl.sinocraft.sinocore.mixin_interfaces.IRenamedProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class AbstractItemTagsProvider extends IntrinsicHolderTagsProvider<Item> implements IRenamedProvider {

    protected static final TagKey<Item> FORGE_CHESTS_WOODEN = forgeTag("chests/wooden");
    protected static final TagKey<Item> FORGE_CHESTS_TRAPPED = forgeTag("chests/trapped");

    protected final String modId;
    protected final CompletableFuture<TagLookup<Block>> blockTags;
    protected final Map<TagKey<Block>, TagKey<Item>> tagsToCopy = new HashMap<>();

    public AbstractItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                    CompletableFuture<TagLookup<Block>> blockTagsProvider, String modId) {
        super(output, Registries.ITEM, lookupProvider, item -> item.builtInRegistryHolder().key());
        this.modId = modId;
        this.blockTags = blockTagsProvider;
    }

    public AbstractItemTagsProvider(IDataGenContext context, TagsProvider<Block> blockTagsProvider) {
        this(context.getOutput(), context.registriesFuture(), blockTagsProvider.contentsGetter(), context.getModId());
    }

    protected void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
        tagsToCopy.put(blockTag, itemTag);
    }

    protected void chest(Supplier<? extends Item> item, boolean trapped) {
        Item i = item.get();
        tag(FORGE_CHESTS_WOODEN).add(i);
        if (trapped) {
            tag(FORGE_CHESTS_TRAPPED).add(i);
        }
    }

    protected static TagKey<Item> forgeTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("forge", name));
    }

    @Override
    public String getNewName() {
        return "Tags for Item: " + modId;
    }

    @Override
    protected CompletableFuture<HolderLookup.Provider> createContentsProvider() {
        return super.createContentsProvider().thenCombineAsync(blockTags, (provider, lookup) -> {
            tagsToCopy.forEach((blockTag, itemTag) -> {
                TagBuilder tagBuilder = getOrCreateRawBuilder(itemTag);
                Optional<TagBuilder> optional = lookup.apply(blockTag);
                optional.orElseThrow(() -> new IllegalStateException("Missing block tag " + itemTag.location()))
                        .build()
                        .forEach(tagBuilder::add);
            });
            return provider;
        });
    }
}