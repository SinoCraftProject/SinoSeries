package games.moegirl.sinocraft.sinocore.data.base;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinocore.SinoCore;
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
import net.minecraftforge.common.ForgeHooks;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 使用 CODEC 或 DIRECT_CODEC 直接导出 json 文件的的 Provider
 */
public abstract class BaseCodecProvider implements DataProvider {

    private final CompletableFuture<HolderLookup.Provider> provider;
    private final PackOutput packOutput;
    private final String modid;

    private Map<ResourceKey<Registry>, List<Triple<ResourceLocation, Codec, Object>>> map = new HashMap<>();

    public BaseCodecProvider(CompletableFuture<HolderLookup.Provider> provider, PackOutput output, String modid) {
        this.provider = provider;
        this.packOutput = output;
        this.modid = modid;
    }

    public abstract void addAll();

    public void add(String name, ConfiguredFeature<?, ?> feature) {
        add(new ResourceLocation(modid, name), feature);
    }
    public void add(ResourceLocation name, ConfiguredFeature<?, ?> feature) {
        add(Registries.CONFIGURED_FEATURE, ConfiguredFeature.DIRECT_CODEC, name, feature);
    }

    public <T> void add(ResourceKey<Registry<T>> type, Codec<T> codec, ResourceLocation name, T value) {
        map.computeIfAbsent((ResourceKey) type, k -> new ArrayList<>()).add(Triple.of(name, codec, value));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return provider.thenCompose(p -> {
            RegistryOps<JsonElement> jsonOps = RegistryOps.create(JsonOps.INSTANCE, p);
            return CompletableFuture.allOf(map.entrySet().stream()
                    .flatMap(entry -> {
                        ResourceKey<Registry> key = entry.getKey();
                        PackOutput.PathProvider path = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, ForgeHooks.prefixNamespace(key.location()));
                        return entry.getValue().stream()
                                .map(triple -> {
                                    ResourceLocation location = triple.getLeft();
                                    Codec<Object> codec = (Codec<Object>) triple.getMiddle();
                                    Object value = triple.getRight();
                                    return codec.encodeStart(jsonOps, value)
                                            .resultOrPartial(str ->
                                                    SinoCore.LOGGER.error("Couldn't serialize element {}: {}", path, str))
                                            .map(json -> DataProvider.saveStable(output, json, path.json(location)))
                                            .orElseGet(() -> CompletableFuture.completedFuture(null));
                                });
                    })
                    .toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return "Codec Provider";
    }
}
