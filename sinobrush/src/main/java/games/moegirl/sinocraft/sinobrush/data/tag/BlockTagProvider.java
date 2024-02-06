package games.moegirl.sinocraft.sinobrush.data.tag;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinocore.datagen.AbstractBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends AbstractBlockTagsProvider {

    public BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, SinoBrush.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
    }
}
