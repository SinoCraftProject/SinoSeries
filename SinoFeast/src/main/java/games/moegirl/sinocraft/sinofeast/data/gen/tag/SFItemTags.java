package games.moegirl.sinocraft.sinofeast.data.gen.tag;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SFItemTags {
    public static final TagKey<Item> EMPTY_TASTES = createTastes("empty");
    public static final TagKey<Item> SOUR_TASTES = createTastes("sour");
    public static final TagKey<Item> SWEET_TASTES = createTastes("sweet");
    public static final TagKey<Item> BITTER_TASTES = createTastes("bitter");
    public static final TagKey<Item> SPICY_TASTES = createTastes("spicy");
    public static final TagKey<Item> SALTY_TASTES = createTastes("salty");
    public static final TagKey<Item> PUNGENT_TASTES = createTastes("pungent");
    public static final TagKey<Item> ACRID_TASTES = createTastes("acrid");
    public static final TagKey<Item> FRESH_TASTES = createTastes("fresh");

    public static final TagKey<Item> PRIMARY_EMPTY_TASTES = createTastesPrimary("empty");
    public static final TagKey<Item> PRIMARY_SOUR_TASTES = createTastesPrimary("sour");
    public static final TagKey<Item> PRIMARY_SWEET_TASTES = createTastesPrimary("sweet");
    public static final TagKey<Item> PRIMARY_BITTER_TASTES = createTastesPrimary("bitter");
    public static final TagKey<Item> PRIMARY_SPICY_TASTES = createTastesPrimary("spicy");
    public static final TagKey<Item> PRIMARY_SALTY_TASTES = createTastesPrimary("salty");
    public static final TagKey<Item> PRIMARY_PUNGENT_TASTES = createTastesPrimary("pungent");
    public static final TagKey<Item> PRIMARY_ACRID_TASTES = createTastesPrimary("acrid");
    public static final TagKey<Item> PRIMARY_FRESH_TASTES = createTastesPrimary("fresh");

    public static final TagKey<Item> SECONDARY_EMPTY_TASTES = createTastesSecondary("empty");
    public static final TagKey<Item> SECONDARY_SOUR_TASTES = createTastesSecondary("sour");
    public static final TagKey<Item> SECONDARY_SWEET_TASTES = createTastesSecondary("sweet");
    public static final TagKey<Item> SECONDARY_BITTER_TASTES = createTastesSecondary("bitter");
    public static final TagKey<Item> SECONDARY_SPICY_TASTES = createTastesSecondary("spicy");
    public static final TagKey<Item> SECONDARY_SALTY_TASTES = createTastesSecondary("salty");
    public static final TagKey<Item> SECONDARY_PUNGENT_TASTES = createTastesSecondary("pungent");
    public static final TagKey<Item> SECONDARY_ACRID_TASTES = createTastesSecondary("acrid");
    public static final TagKey<Item> SECONDARY_FRESH_TASTES = createTastesSecondary("fresh");

    public static final TagKey<Item> MEAT_NUTRITION_CATEGORIES = ItemTags.create(new ResourceLocation(SinoFeast.MODID, "nutrition_categories/meat"));
    public static final TagKey<Item> PROTEIN_NUTRITION_CATEGORIES = ItemTags.create(new ResourceLocation(SinoFeast.MODID, "nutrition_categories/protein"));
    public static final TagKey<Item> FRUITS_NUTRITION_CATEGORIES = ItemTags.create(new ResourceLocation(SinoFeast.MODID, "nutrition_categories/fruits"));
    public static final TagKey<Item> VEGETABLES_NUTRITION_CATEGORIES = ItemTags.create(new ResourceLocation(SinoFeast.MODID, "nutrition_categories/vegetables"));
    public static final TagKey<Item> GRAINS_NUTRITION_CATEGORIES = ItemTags.create(new ResourceLocation(SinoFeast.MODID, "nutrition_categories/grains"));

    public static TagKey<Item> createTastes(String name) {
        return ItemTags.create(new ResourceLocation(SinoFeast.MODID, "tastes/"+name));
    }

    public static TagKey<Item> createTastesPrimary(String name) {
        return ItemTags.create(new ResourceLocation(SinoFeast.MODID, "tastes/primary/"+name));
    }

    public static TagKey<Item> createTastesSecondary(String name) {
        return ItemTags.create(new ResourceLocation(SinoFeast.MODID, "tastes/secondary/"+name));
    }
}
