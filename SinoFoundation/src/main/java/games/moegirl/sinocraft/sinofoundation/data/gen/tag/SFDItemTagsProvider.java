package games.moegirl.sinocraft.sinofoundation.data.gen.tag;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractItemTagsProvider;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SFDItemTagsProvider extends AbstractItemTagsProvider {
    public SFDItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTagsProvider, String modId,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addToTags() {
        tag(SFDItemTags.KNIVES).add(SFDItems.IRON_KNIFE.get(), SFDItems.GOLD_KNIFE.get(), SFDItems.DIAMOND_KNIFE.get());
        tag(SFDItemTags.JADE).add(SFDItems.BLACK_JADE.get(), SFDItems.GREEN_JADE.get(), SFDItems.RED_JADE.get(), SFDItems.WHITE_JADE.get(), SFDItems.YELLOW_JADE.get());
        tag(SFDItemTags.ORES).addTag(SFDItemTags.JADE).add(SFDBlockItems.NITER_ORE.get(), SFDBlockItems.SULPHUR_ORE.get(), SFDBlockItems.DEEPSLATE_SULPHUR_ORE.get(), SFDBlockItems.NETHER_SULPHUR_ORE.get());

        tag(SFDItemTags.FORGE_DUSTS_SULFUR).add(SFDItems.SULPHUR.get());
        tag(SFDItemTags.FORGE_DUSTS_NITER).add(SFDItems.NITER.get());
        tag(SFDItemTags.FORGE_CROPS)
                .add(SFDItems.CHILI_PEPPER.get(), SFDItems.GREEN_PEPPER.get(), SFDItems.CABBAGE.get(), SFDItems.EGGPLANT.get(), SFDItems.MILLET.get(), SFDItems.RICE.get(), SFDItems.SESAME.get())
                .add(SFDBlockItems.WHITE_RADISH.get(), SFDBlockItems.SUMMER_RADISH.get(), SFDBlockItems.GREEN_RADISH.get(), SFDBlockItems.SOYBEAN.get(), SFDBlockItems.GARLIC.get());
        tag(SFDItemTags.FORGE_SEEDS)
                .add(SFDItems.SEED_RICE.get(), SFDItems.SEED_SESAME.get(), SFDItems.SEED_WORMWOOD.get())
                .add(SFDBlockItems.CHILI_PEPPER_SEED.get(), SFDBlockItems.GREEN_PEPPER_SEED.get(), SFDBlockItems.EGGPLANT_SEED.get(), SFDBlockItems.CABBAGE_SEED.get(), SFDBlockItems.MILLET_SEED.get());

        chest(SFDBlockItems.JUJUBE_CHEST, false);
        chest(SFDBlockItems.JUJUBE_TRAPPED_CHEST, true);
    }
}
