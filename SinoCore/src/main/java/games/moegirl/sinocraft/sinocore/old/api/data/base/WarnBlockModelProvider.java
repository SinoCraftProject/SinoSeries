package games.moegirl.sinocraft.sinocore.old.api.data.base;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class WarnBlockModelProvider extends BlockModelProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarnBlockModelProvider.class);

    private final List<Pair<ResourceLocation, ResourceLocation>> notExistingModel = new ArrayList<>();

    public WarnBlockModelProvider(DataGenerator generator, String modid, ExistingFileHelper helper) {
        super(generator, modid, helper);
    }

    @Override
    public ModelFile.ExistingModelFile getExistingFile(ResourceLocation path) {
        try {
            return super.getExistingFile(path);
        } catch (IllegalStateException e) {
            ResourceLocation extended;
            if (path.getPath().contains("/")) {
                extended = path;
            } else {
                extended = new ResourceLocation(path.getNamespace(), folder + "/" + path.getPath());
            }
            notExistingModel.add(Pair.of(path, extended));
            return new ModelFile.ExistingModelFile(extended, existingFileHelper) {
                @Override
                public void assertExistence() {

                }
            };
        }
    }

    @Override
    public BlockModelBuilder getBuilder(String path) {
        Preconditions.checkNotNull(path, "Path must not be null");
        ResourceLocation rl = path.contains(":") ? new ResourceLocation(path) : new ResourceLocation(modid, path);
        ResourceLocation outputLoc = rl.getPath().contains("/") ? rl : new ResourceLocation(rl.getNamespace(), folder + "/" + rl.getPath());
        this.existingFileHelper.trackGenerated(outputLoc, MODEL);
        return generatedModels.computeIfAbsent(outputLoc, (p) -> new UnexceptionalBlockModelBuilder(p, existingFileHelper));
    }

    @Override
    public void run(HashCache cache) throws IOException {
        super.run(cache);
        printAllExceptions();
    }

    @Override
    @Nonnull
    public String getName() {
        return "BlockModels: " + modid;
    }

    public void printAllExceptions() {
        if (!notExistingModel.isEmpty()) {
            LOGGER.warn("Not found model in {}", getName());
            notExistingModel.forEach(pair -> LOGGER.warn("  Key: {}, Path: {}", pair.getFirst(), pair.getSecond()));
        }
        generatedModels.forEach((key, builder) -> {
            if (builder instanceof UnexceptionalBlockModelBuilder b && !b.notExistingTexture.isEmpty()) {
                LOGGER.warn("Not found texture in {} -> {}", getName(), key);
                b.notExistingTexture.forEach(pair ->
                        LOGGER.warn("  Texture: {}, Path: {}", pair.getFirst(), pair.getSecond()));
            }
        });
    }

    static class UnexceptionalBlockModelBuilder extends BlockModelBuilder {
        final List<Pair<String, ResourceLocation>> notExistingTexture = new ArrayList<>();

        public UnexceptionalBlockModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
            super(outputLocation, existingFileHelper);
        }

        @Override
        public BlockModelBuilder texture(String key, ResourceLocation texture) {
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
