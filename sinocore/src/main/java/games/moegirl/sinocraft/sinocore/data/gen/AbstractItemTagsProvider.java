package games.moegirl.sinocraft.sinocore.data.gen;

import games.moegirl.sinocraft.sinocore.mixin_interfaces.interfaces.IRenamedProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
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

public abstract class AbstractItemTagsProvider extends IntrinsicHolderTagsProvider<Item> implements IRenamedProvider, ISinoDataProvider {

    protected static final TagKey<Item> FORGE_CHESTS_WOODEN = forgeTag("chests/wooden");
    protected static final TagKey<Item> FORGE_CHESTS_TRAPPED = forgeTag("chests/trapped");

    protected final String modId;
    protected final CompletableFuture<TagLookup<Block>> blockTags;
    protected final Map<TagKey<Block>, TagKey<Item>> tagsToCopy = new HashMap<>();

    public AbstractItemTagsProvider(IDataGenContext context, TagsProvider<Block> blockTagsProvider) {
        super(context.getOutput(), Registries.ITEM, context.registriesFuture(), item -> item.builtInRegistryHolder().key());
        this.modId = context.getModId();
        this.blockTags = blockTagsProvider.contentsGetter();
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
    public String getModId() {
        return modId;
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
