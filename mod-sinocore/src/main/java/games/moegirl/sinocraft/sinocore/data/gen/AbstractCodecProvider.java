package games.moegirl.sinocraft.sinocore.data.gen;

import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 使用 CODEC 或 DIRECT_CODEC 直接导出 json 文件的的 Provider
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class AbstractCodecProvider implements ISinoDataProvider {

    protected final CompletableFuture<HolderLookup.Provider> provider;
    private final PackOutput packOutput;
    protected final String modId;
    private final Map<ResourceKey<Registry>, List<Triple<ResourceLocation, Codec, Object>>> map = new HashMap<>();
    private final Logger logger = LogUtils.getLogger();

    public AbstractCodecProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String modId) {
        this.provider = provider;
        this.packOutput = output;
        this.modId = modId;
    }

    public AbstractCodecProvider(DataGenContext context) {
        this(context.getOutput(), context.getRegistries(), context.getModId());
    }

    protected abstract void generateData();

    protected void add(String name, ConfiguredFeature<?, ?> feature) {
        add(modLoc(name), feature);
    }

    protected void add(ResourceLocation name, ConfiguredFeature<?, ?> feature) {
        add(Registries.CONFIGURED_FEATURE, ConfiguredFeature.DIRECT_CODEC, name, feature);
    }

    protected <T> void add(ResourceKey<Registry<T>> type, Codec<T> codec, ResourceLocation name, T value) {
        map.computeIfAbsent((ResourceKey) type, k -> new ArrayList<>()).add(Triple.of(name, codec, value));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        generateData();
        return provider.thenCompose(p -> {
            RegistryOps<JsonElement> jsonOps = RegistryOps.create(JsonOps.INSTANCE, p);
            return CompletableFuture.allOf(map.entrySet().stream().flatMap(entry -> {
                        ResourceLocation key = entry.getKey().location();
                        // ForgeHooks#prefixNamespace
                        String subPath = "minecraft".equals(key.getNamespace()) ? key.getPath() : (key.getNamespace() + "/" + key.getPath());
                        PackOutput.PathProvider path = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, subPath);
                        return entry.getValue().stream().map(triple -> ((Codec<Object>) triple.getMiddle())
                                .encodeStart(jsonOps, triple.getRight())
                                .resultOrPartial(str -> logger.error("Couldn't serialize element {}: {}", path, str))
                                .map(json -> DataProvider.saveStable(output, json, path.json(triple.getLeft())))
                                .orElseGet(() -> CompletableFuture.completedFuture(null)));
                    }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return "Codec Provider: " + modId;
    }

    @Override
    public String getModId() {
        return modId;
    }
}
