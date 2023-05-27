package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.data.event.GatherDataEvent;

class ProviderTagBiome extends BiomeTagsProvider {

    public ProviderTagBiome(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), SinoDivination.MODID, event.getExistingFileHelper());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider provider) {
        tag(SDTags.SPAWN_RICE).addTags(BiomeTags.IS_RIVER).addTags(Tags.Biomes.IS_PLAINS);
        tag(SDTags.SPAWN_REHMANNIA);
        tag(SDTags.SPAWN_DRAGONLIVER_MELON);
    }
}
