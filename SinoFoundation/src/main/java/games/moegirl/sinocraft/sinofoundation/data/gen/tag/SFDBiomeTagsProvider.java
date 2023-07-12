package games.moegirl.sinocraft.sinofoundation.data.gen.tag;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.data.SFDBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class SFDBiomeTagsProvider extends BiomeTagsProvider {

    public SFDBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(output, future, SinoFoundation.MODID, helper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider provider) {
        tag(SFDBiomeTags.SPAWN_RICE).addTags(BiomeTags.IS_RIVER).addTags(Tags.Biomes.IS_PLAINS);
        tag(SFDBiomeTags.SPAWN_REHMANNIA).addTag(BiomeTags.IS_FOREST).add(Biomes.BAMBOO_JUNGLE);
        tag(SFDBiomeTags.SPAWN_DRAGONLIVER_MELON).addTags(Tags.Biomes.IS_SNOWY);
    }
}
