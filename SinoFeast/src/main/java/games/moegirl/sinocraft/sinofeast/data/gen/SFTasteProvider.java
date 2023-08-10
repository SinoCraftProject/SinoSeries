package games.moegirl.sinocraft.sinofeast.data.gen;

import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTaste;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTasteCodec;
import games.moegirl.sinocraft.sinofeast.data.gen.tag.SFItemTags;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SFTasteProvider extends JsonCodecProvider<FoodTaste> {
    public static final Map<ResourceLocation, FoodTaste> tastes = new HashMap<>();

    public SFTasteProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper, SinoFeast.MODID, JsonOps.INSTANCE, PackType.SERVER_DATA, "sinoseries/food_tastes", FoodTasteCodec.TASTE_CODEC, tastes);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        addTastes();

        return super.run(output);
    }

    /**
     * 在这个方法里面调用其他添加方法
     */
    public void addTastes() {
        addTaste(to("empty"),
                false,
                0,0,
                SFItemTags.EMPTY_TASTES,
                SFItemTags.PRIMARY_EMPTY_TASTES,
                SFItemTags.SECONDARY_EMPTY_TASTES);
        addTaste(to("sour"),
                false,
                5,5,
                SFItemTags.SOUR_TASTES,
                SFItemTags.PRIMARY_SOUR_TASTES,
                SFItemTags.SECONDARY_SOUR_TASTES);
        addTaste(to("sweet"),
                false,
                5,5,
                SFItemTags.SWEET_TASTES,
                SFItemTags.PRIMARY_SWEET_TASTES,
                SFItemTags.SECONDARY_SWEET_TASTES);
        addTaste(to("bitter"),
                false,
                5,5,
                SFItemTags.BITTER_TASTES,
                SFItemTags.PRIMARY_BITTER_TASTES,
                SFItemTags.SECONDARY_BITTER_TASTES);
        addTaste(to("spicy"),
                false,
                5,5,
                SFItemTags.SPICY_TASTES,
                SFItemTags.PRIMARY_SPICY_TASTES,
                SFItemTags.SECONDARY_SPICY_TASTES);
        addTaste(to("salty"),
                false,
                5,5,
                SFItemTags.SALTY_TASTES,
                SFItemTags.PRIMARY_SALTY_TASTES,
                SFItemTags.SECONDARY_SALTY_TASTES);
        addTaste(to("pungent"),
                true,
                5,5,
                SFItemTags.PUNGENT_TASTES,
                SFItemTags.PRIMARY_PUNGENT_TASTES,
                SFItemTags.SECONDARY_PUNGENT_TASTES);
        addTaste(to("acrid"),
                true,
                5, 5,
                SFItemTags.ACRID_TASTES,
                SFItemTags.PRIMARY_ACRID_TASTES,
                SFItemTags.SECONDARY_ACRID_TASTES);
        addTaste(to("fresh"),
                true,
                5,5,
                SFItemTags.FRESH_TASTES,
                SFItemTags.PRIMARY_FRESH_TASTES,
                SFItemTags.SECONDARY_FRESH_TASTES);
    }

    public static ResourceLocation to(String name) {
        return new ResourceLocation(SinoFeast.MODID, name);
    }

    public void addTaste(ResourceLocation key, Boolean isAdvanced, int likeWeight, int dislikeWeight, TagKey<Item> tasteKey, TagKey<Item> tasteKeyPrimary, TagKey<Item> tasteKeySecondary) {
        tastes.put(key, new FoodTaste(key, isAdvanced, likeWeight, dislikeWeight, tasteKey, tasteKeyPrimary, tasteKeySecondary));
    }
}
