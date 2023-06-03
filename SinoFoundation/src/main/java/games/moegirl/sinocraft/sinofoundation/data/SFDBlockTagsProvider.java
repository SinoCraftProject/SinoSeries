package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractBlockTagsProvider;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class SFDBlockTagsProvider extends AbstractBlockTagsProvider {

    public SFDBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                String modId, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addToTags() {
        tag(SFDBlockTags.MINECRAFT_WALLS).add(SFDBlocks.MARBLE_WALL.get());
    }
}
