package games.moegirl.sinocraft.sinofeast.data.gen.tag;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractItemTagsProvider;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SFItemTagsProvider extends AbstractItemTagsProvider {
    public SFItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTagsProvider, String modId,
                              @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addToTags() {
        tag(SFItemTags.PRIMARY_SOUR_TASTES)
                .add(Items.GLOW_BERRIES);
        tag(SFItemTags.PRIMARY_SWEET_TASTES)
                .add(SFDItems.JUJUBE.get())
                .add(Items.APPLE, Items.MELON, Items.SWEET_BERRIES, Items.BEETROOT, Items.CAKE, Items.PUMPKIN_PIE, Items.COOKIE, Items.BREAD, Items.HONEY_BOTTLE);
        tag(SFItemTags.PRIMARY_BITTER_TASTES)
                .add(SFDItems.WORMWOOD_LEAF.get());
        tag(SFItemTags.PRIMARY_SPICY_TASTES)
                .add(SFDItems.CHILI_PEPPER.get());
        tag(SFItemTags.PRIMARY_SALTY_TASTES)
                .add(Items.DRIED_KELP, Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.COOKED_MUTTON, Items.COOKED_CHICKEN, Items.COOKED_RABBIT);
        tag(SFItemTags.PRIMARY_PUNGENT_TASTES);
        tag(SFItemTags.PRIMARY_ACRID_TASTES)
                .add(SFDBlockItems.GARLIC.get(), SFDBlockItems.SUMMER_RADISH.get());
        tag(SFItemTags.PRIMARY_FRESH_TASTES)
                .add(Items.COOKED_COD, Items.COOKED_SALMON, Items.RABBIT_STEW, Items.MUSHROOM_STEW);

        tag(SFItemTags.SECONDARY_SOUR_TASTES)
                .add(SFDItems.JUJUBE.get())
                .add(Items.APPLE, Items.SWEET_BERRIES);
        tag(SFItemTags.SECONDARY_SWEET_TASTES)
                .add(Items.GLOW_BERRIES);
        tag(SFItemTags.SECONDARY_BITTER_TASTES);
        tag(SFItemTags.SECONDARY_SPICY_TASTES);
        tag(SFItemTags.SECONDARY_SALTY_TASTES)
                .add(Items.RABBIT_STEW, Items.MUSHROOM_STEW);
        tag(SFItemTags.SECONDARY_PUNGENT_TASTES)
                .add(SFDItems.CHILI_PEPPER.get());
        tag(SFItemTags.SECONDARY_ACRID_TASTES);
        tag(SFItemTags.SECONDARY_FRESH_TASTES)
                .add(Items.DRIED_KELP);

        tag(SFItemTags.SOUR_TASTES).addTag(SFItemTags.PRIMARY_SOUR_TASTES).addTag(SFItemTags.SECONDARY_SOUR_TASTES);
        tag(SFItemTags.SWEET_TASTES).addTag(SFItemTags.PRIMARY_SWEET_TASTES).addTag(SFItemTags.SECONDARY_SWEET_TASTES);
        tag(SFItemTags.BITTER_TASTES).addTag(SFItemTags.PRIMARY_BITTER_TASTES).addTag(SFItemTags.SECONDARY_BITTER_TASTES);
        tag(SFItemTags.SPICY_TASTES).addTag(SFItemTags.PRIMARY_SPICY_TASTES).addTag(SFItemTags.SECONDARY_SPICY_TASTES);
        tag(SFItemTags.SALTY_TASTES).addTag(SFItemTags.PRIMARY_SALTY_TASTES).addTag(SFItemTags.SECONDARY_SALTY_TASTES);
        tag(SFItemTags.PUNGENT_TASTES).addTag(SFItemTags.PRIMARY_PUNGENT_TASTES).addTag(SFItemTags.SECONDARY_PUNGENT_TASTES);
        tag(SFItemTags.ACRID_TASTES).addTag(SFItemTags.PRIMARY_ACRID_TASTES).addTag(SFItemTags.SECONDARY_ACRID_TASTES);
        tag(SFItemTags.FRESH_TASTES).addTag(SFItemTags.PRIMARY_FRESH_TASTES).addTag(SFItemTags.SECONDARY_FRESH_TASTES);

        tag(SFItemTags.MEAT_NUTRITION_CATEGORIES)
                .add(Items.BEEF, Items.COOKED_BEEF, Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.MUTTON, Items.COOKED_MUTTON, Items.CHICKEN, Items.COOKED_CHICKEN, Items.RABBIT, Items.COOKED_RABBIT, Items.COD, Items.COOKED_COD, Items.SALMON, Items.COOKED_SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH, Items.RABBIT_STEW);
        tag(SFItemTags.PROTEIN_NUTRITION_CATEGORIES)
                .add(Items.COD, Items.COOKED_COD, Items.SALMON, Items.COOKED_SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH, Items.EGG, Items.MILK_BUCKET, Items.ROTTEN_FLESH, Items.SPIDER_EYE);
        tag(SFItemTags.FRUITS_NUTRITION_CATEGORIES)
                .add(Items.APPLE, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.MELON, Items.SWEET_BERRIES, Items.GLOW_BERRIES);
        tag(SFItemTags.VEGETABLES_NUTRITION_CATEGORIES)
                .add(Items.CARROT, Items.GOLDEN_CARROT, Items.POTATO, Items.BAKED_POTATO, Items.BEETROOT, Items.DRIED_KELP, Items.BEETROOT_SOUP, Items.RABBIT_STEW, Items.MUSHROOM_STEW);
        tag(SFItemTags.GRAINS_NUTRITION_CATEGORIES)
                .add(Items.COOKIE, Items.BREAD, Items.CAKE, Items.PUMPKIN_PIE);
    }
}
