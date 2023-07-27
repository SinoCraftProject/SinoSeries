package games.moegirl.sinocraft.sinofeast.data.gen.tag;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractBlockTagsProvider;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SFBlockTagsProvider extends AbstractBlockTagsProvider {
    public SFBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               String modId, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addToTags() {

    }
}
