package games.moegirl.sinocraft.sinofeast.data.gen;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinofeast.SFConstants;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTaste;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTasteCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SFTasteProvider implements DataProvider {
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> completableFuture;
    public static final Set<FoodTaste> tastes = new HashSet<>();

    public SFTasteProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        this.output = output;
        this.completableFuture = completableFuture;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        start();

        Path path = this.output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve("sinofeast")
                .resolve("sinoseries")
                .resolve("sinofeast")
                .resolve("food_tastes");

        List<CompletableFuture<?>> tastesBuilder = new ArrayList<>();

        //根据Codec来生成味道的json文件
        return completableFuture.thenCompose(provider -> {
            RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, provider);
            var codec = FoodTasteCodec.TASTE_CODEC;
            tastes.forEach(taste -> {
                Optional<JsonElement> jsonElement = codec.encodeStart(registryOps, taste).resultOrPartial(s -> LOGGER.error("Couldn't serialize element {}: {}", path, s));
                tastesBuilder.add(DataProvider.saveStable(output,
                        jsonElement.get(),
                        path.resolve(taste.getKey().getPath() + ".json")));
            });
            return CompletableFuture.allOf(tastesBuilder.toArray(CompletableFuture[]::new));
        });


    }

    /**
     * 在这个方法里面调用其他添加方法
     */
    public void start() {
        addTaste(SFConstants.TRANSLATE_TASTE_ACRID,
                false,
                5, 5,
                ItemTags.DIAMOND_ORES,
                ItemTags.DIAMOND_ORES,
                ItemTags.DIAMOND_ORES);
    }

    public void addTaste(String name, Boolean isAdvanced, int likeWeight, int dislikeWeight, TagKey<Item> tasteKey, TagKey<Item> tasteKeyPrimary, TagKey<Item> tasteKeySecondary) {
        tastes.add(new FoodTaste(name, isAdvanced, likeWeight, dislikeWeight, tasteKey, tasteKeyPrimary, tasteKeySecondary));
    }

    @Override
    public String getName() {
        return "Taste Provider:" + SinoFeast.MODID;
    }


}
