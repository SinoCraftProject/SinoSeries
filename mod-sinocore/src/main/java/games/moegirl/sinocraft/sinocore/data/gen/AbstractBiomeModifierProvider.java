package games.moegirl.sinocraft.sinocore.data.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// 明明有 CODEC，但搞不到 Holder，只能用这玩意，就很气
public abstract class AbstractBiomeModifierProvider implements ISinoDataProvider {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    protected final PackOutput output;
    protected final String modId;

    protected final List<Feature> features = new ArrayList<>();

    public AbstractBiomeModifierProvider(PackOutput output, String modId) {
        this.output = output;
        this.modId = modId;
    }

    public AbstractBiomeModifierProvider(DataGenContext context) {
        this(context.getOutput(), context.getModId());
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        registerBiomeModifiers();
        Path path = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve(modId)
                .resolve("forge")
                .resolve("biome_modifier");
        return CompletableFuture.allOf(features.stream()
                .map(f -> beginWriteFeature(f, path, cachedOutput))
                .toArray(CompletableFuture[]::new));
    }

    @Override
    public String getModId() {
        return modId;
    }

    private CompletableFuture<?> beginWriteFeature(Feature f, Path parentDir, CachedOutput output) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "forge:add_features");
        json.add("biomes", GSON.toJsonTree(f.hasBiomes()
                ? f.biomes().stream().map(k -> k.location().toString())
                : f.hasTag() ? "#" + f.biomesTag().location() : null));
        json.addProperty("features", f.feature().location().toString());
        json.addProperty("step", f.step().getName());

        Path p = parentDir.resolve(f.feature().location().getPath() + ".json");
        return DataProvider.saveStable(output, json, p);
    }

    @Override
    public String getName() {
        return modId + ": Biome Modifiers";
    }

    protected abstract void registerBiomeModifiers();

    protected void add(Feature f) {
        features.add(f);
    }

    public record Feature(ResourceKey<ConfiguredFeature<?, ?>> feature, GenerationStep.Decoration step,
                          @Nullable TagKey<Biome> biomesTag,
                          List<ResourceKey<Biome>> biomes) {

        @SafeVarargs
        public Feature(ResourceKey<ConfiguredFeature<?, ?>> feature, GenerationStep.Decoration step, ResourceKey<Biome>... biomes) {
            this(feature, step, null, List.of(biomes));
        }

        public Feature(ResourceKey<ConfiguredFeature<?, ?>> feature, GenerationStep.Decoration step, TagKey<Biome> biomesTag) {
            this(feature, step, biomesTag, List.of());
        }

        public boolean hasTag() {
            return biomesTag != null;
        }

        public boolean hasBiomes() {
            return biomes.size() >= 1;
        }
    }
}
