package games.moegirl.sinocraft.sinocalligraphy.data;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class SCABlockTagsProvider extends AbstractBlockTagsProvider {

    public SCABlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                String modId, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addToTags() {

    }
}
