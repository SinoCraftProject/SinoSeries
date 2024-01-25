package games.moegirl.sinocraft.sinocore.datagen.copy;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class AbstractItemTagsProvider extends ItemTagsProvider {
    public AbstractItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                    CompletableFuture<TagLookup<Block>> blockTagsProvider, String modId,
                                    @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagsProvider, modId, existingFileHelper);
    }

    protected abstract void addToTags();

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addToTags();
    }

    protected void chest(Supplier<? extends Item> item, boolean trapped) {
        Item i = item.get();
        tag(Tags.Items.CHESTS_WOODEN).add(i);
        if (trapped) {
            tag(Tags.Items.CHESTS_TRAPPED).add(i);
        }
    }
}
