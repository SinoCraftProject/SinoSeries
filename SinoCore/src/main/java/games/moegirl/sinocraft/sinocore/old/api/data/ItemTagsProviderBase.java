package games.moegirl.sinocraft.sinocore.old.api.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

/**
 * Register item tags.
 *
 * @author qyl27
 */
public abstract class ItemTagsProviderBase extends ItemTagsProvider {

    public ItemTagsProviderBase(DataGenerator pGenerator, BlockTagsProviderBase blockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, blockTags, modId, existingFileHelper);
    }

    @Override
    protected abstract void addTags();
}
