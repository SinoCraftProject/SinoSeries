package games.moegirl.sinocraft.sinofoundation.data.gen.tag;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SFDItemTags {
    public static final TagKey<Item> KNIVES = ItemTags.create(new ResourceLocation(SinoFoundation.MODID, "knives"));
    public static final TagKey<Item> STACK_SOPHORA = ItemTags.create(new ResourceLocation(SinoFoundation.MODID, "sophora_stack"));
    public static final TagKey<Item> ORES = ItemTags.create(new ResourceLocation(SinoFoundation.MODID, "peppers"));
    public static final TagKey<Item> JADE = ItemTags.create(new ResourceLocation(SinoFoundation.MODID, "jade"));
    public static final TagKey<Item> RADISHES = ItemTags.create(new ResourceLocation(SinoFoundation.MODID, "radishes"));
    public static final TagKey<Item> PEPPERS = ItemTags.create(new ResourceLocation(SinoFoundation.MODID, "peppers"));

    public static final TagKey<Item> FORGE_DUSTS_SULFUR = ItemTags.create(new ResourceLocation("forge", "dusts/sulfur"));
    public static final TagKey<Item> FORGE_DUSTS_NITER = ItemTags.create(new ResourceLocation("forge", "dusts/niter"));
    public static final TagKey<Item> FORGE_CROPS = ItemTags.create(new ResourceLocation("forge", "crops"));
    public static final TagKey<Item> FORGE_SEEDS = ItemTags.create(new ResourceLocation("forge", "seeds"));
}
