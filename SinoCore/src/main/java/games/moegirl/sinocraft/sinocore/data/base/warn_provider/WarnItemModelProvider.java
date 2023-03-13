package games.moegirl.sinocraft.sinocore.data.base.warn_provider;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class WarnItemModelProvider extends ItemModelProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarnItemModelProvider.class);

    private final List<Pair<ResourceLocation, ResourceLocation>> errModels = new ArrayList<>();

    public WarnItemModelProvider(PackOutput output, String modid, ExistingFileHelper helper) {
        super(output, modid, helper);
    }

    @Override
    public ModelFile.ExistingModelFile getExistingFile(ResourceLocation path) {
        try {
            return super.getExistingFile(path);
        } catch (IllegalStateException e) {
            return NotExistingModelFile.notExisting(this, path, folder, errModels);
        }
    }

    @Override
    public ItemModelBuilder getBuilder(String path) {
        Preconditions.checkNotNull(path, "Path must not be null");
        ResourceLocation rl = path.contains(":") ? new ResourceLocation(path) : new ResourceLocation(modid, path);
        ResourceLocation outputLoc = rl.getPath().contains("/") ? rl : new ResourceLocation(rl.getNamespace(), folder + "/" + rl.getPath());
        this.existingFileHelper.trackGenerated(outputLoc, MODEL);
        return generatedModels.computeIfAbsent(outputLoc, (p) -> new UnexceptionalItemModelBuilder(p, existingFileHelper));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return super.run(output).thenRun(this::printAllExceptions);
    }

    @Override
    @Nonnull
    public String getName() {
        return "ItemModels: " + modid;
    }

    public void printAllExceptions() {
        if (!errModels.isEmpty()) {
            LOGGER.warn("Not found model in {}", getName());
            errModels.forEach(pair -> LOGGER.warn("  Key: {}, Path: {}", pair.getFirst(), pair.getSecond()));
        }
        generatedModels.forEach((key, builder) -> {
            if (builder instanceof UnexceptionalItemModelBuilder b && !b.notExistingTexture.isEmpty()) {
                LOGGER.warn("Not found texture in {} -> {}", getName(), key);
                b.notExistingTexture.forEach(pair ->
                        LOGGER.warn("  Texture: {}, Path: {}", pair.getFirst(), pair.getSecond()));
            }
        });
    }

    public ItemModelBuilder block(Item item) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        return withExistingParent(name, modLoc(BLOCK_FOLDER + "/" + name));
    }

    public ItemModelBuilder block(RegistryObject<Item> item) {
        String name = item.getId().getPath();
        return withExistingParent(name, modLoc(BLOCK_FOLDER + "/" + name));
    }

    static class UnexceptionalItemModelBuilder extends ItemModelBuilder {
        final List<Pair<String, ResourceLocation>> notExistingTexture = new ArrayList<>();

        public UnexceptionalItemModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
            super(outputLocation, existingFileHelper);
        }

        @Override
        public ItemModelBuilder texture(String key, ResourceLocation texture) {
            try {
                return super.texture(key, texture);
            } catch (IllegalArgumentException e) {
                notExistingTexture.add(Pair.of(key, texture));
                textures.put(key, texture.toString());
                return this;
            }
        }
    }
}
