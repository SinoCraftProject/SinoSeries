package games.moegirl.sinocraft.sinocalligraphy.data;

import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SCAItemTagsProvider extends AbstractItemTagsProvider {
    public SCAItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addToTags() {
        tag(SCAItemTags.PAPERS).add(SCAItems.EMPTY_XUAN_PAPER.get(), SCAItems.EMPTY_XUAN_PAPER_RED.get(), SCAItems.EMPTY_XUAN_PAPER_BLACK.get());
        tag(SCAItemTags.FILLED_PAPERS).add(SCAItems.FILLED_XUAN_PAPER.get());
        tag(SCAItemTags.INKS).add(SCAItems.INK.get(), SCAItems.GOLDEN_INK.get());
        tag(SCAItemTags.BRUSHES).add(SCAItems.BRUSH.get());

        tag(SCAItemTags.FAN).add(SCAItems.FAN.get(), SCAItems.FAN_FOLDED.get());
    }
}
