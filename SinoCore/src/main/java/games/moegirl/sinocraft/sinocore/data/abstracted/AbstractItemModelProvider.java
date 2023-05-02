package games.moegirl.sinocraft.sinocore.data.abstracted;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.data.model.WeakCheckModelFile;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The default base class of ItemModel data generator in SinoCore.
 *
 * @author qyl27
 */
public abstract class AbstractItemModelProvider extends ItemModelProvider {
    public static final ResourceLocation GENERATED = new ResourceLocation("minecraft", "item/generated");
    public static final ResourceLocation HANDHELD = new ResourceLocation("minecraft", "item/handheld");

    private final boolean strict;

    private final Logger logger;

    private final List<Pair<ResourceLocation, ResourceLocation>> errModels = new ArrayList<>();

    /**
     * ItemModelProviderBase
     * @param output PackOutput
     * @param modid ModId
     * @param helper ExistingFileHelper
     */
    public AbstractItemModelProvider(PackOutput output, String modid, ExistingFileHelper helper) {
        this(output, modid, helper, false);
    }

    /**
     * ItemModelProviderBase
     * @param output PackOutput
     * @param modid ModId
     * @param helper ExistingFileHelper
     * @param strict Exception when textures not exists. (true: Exception; false: Warning.)
     */
    public AbstractItemModelProvider(PackOutput output, String modid, ExistingFileHelper helper, boolean strict) {
        super(output, modid, helper);

        this.strict = strict;
        this.logger = LoggerFactory.getLogger(getName());
    }

    @Override
    public ModelFile.ExistingModelFile getExistingFile(ResourceLocation path) {
        try {
            return super.getExistingFile(path);
        } catch (IllegalStateException ex) {
            if (isStrict()) {
                throw ex;
            } else {
                return weakCheckModel(path);
            }
        }
    }

    @Override
    public ItemModelBuilder getBuilder(String path) {
        Preconditions.checkNotNull(path, "Path must not be null");
        var loc = path.contains(":") ? mcLoc(path) : modLoc(path);
        var folded = foldedLoc(loc);
        this.existingFileHelper.trackGenerated(folded, MODEL);
        return generatedModels.computeIfAbsent(folded, (p) -> new UnexceptionalItemModelBuilder(p, existingFileHelper));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return super.run(output).thenRun(this::printExceptions);
    }

    public void printExceptions() {
        if (!errModels.isEmpty()) {
            logger.warn("Not found model in {}", getName());
            errModels.forEach(pair -> logger.warn("  Key: {}, Path: {}", pair.getFirst(), pair.getSecond()));
        }

        generatedModels.forEach((key, builder) -> {
            if (builder instanceof UnexceptionalItemModelBuilder b && !b.notExistingTexture.isEmpty()) {
                logger.warn("Not found texture in {} -> {}", getName(), key);
                b.notExistingTexture.forEach(pair ->
                        logger.warn("  Texture: {}, Path: {}", pair.getFirst(), pair.getSecond()));
            }
        });
    }

    protected boolean isStrict() {
        return strict;
    }

    protected ModelFile.ExistingModelFile weakCheckModel(ResourceLocation path) {
        return new WeakCheckModelFile(path, existingFileHelper, strict, resourceLocation -> {
            errModels.add(new Pair<>(resourceLocation, foldedLoc(resourceLocation)));
            return true;
        });
    }

    protected ResourceLocation blockLoc(ResourceLocation path) {
        return new ResourceLocation(path.getNamespace(), BLOCK_FOLDER + "/" + path.getPath());
    }

    protected ResourceLocation foldedLoc(ResourceLocation path) {
        return path.getPath().contains("/") ? path :
                new ResourceLocation(path.getNamespace(), folder + "/" + path.getPath());
    }

    protected ItemModelBuilder withBlockParent(Block block) {
        var name = ForgeRegistries.BLOCKS.getKey(block);
        assert name != null;
        return withExistingParent(name.getPath(), blockLoc(name));
    }

    protected ItemModelBuilder withBlockParent(RegistryObject<Block> block) {
        var name = block.getId();
        return withExistingParent(name.getPath(), blockLoc(name));
    }

    protected void generated(ItemLike item) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(item.asItem());
        assert key != null;
        String path = key.getPath();
        withExistingParent(path, GENERATED).texture("layer0", modLoc("item/" + path));
    }

    protected void handheld(ItemLike item) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(item.asItem());
        assert key != null;
        String path = key.getPath();
        withExistingParent(path, HANDHELD).texture("layer0", modLoc("item/" + path));
    }

    protected void handheld(Block block) {
        ResourceLocation key = ForgeRegistries.BLOCKS.getKey(block);
        assert key != null;
        String path = key.getPath();
        withExistingParent(path, HANDHELD).texture("layer0", modLoc("block/" + path));
    }

    protected void blockItem(Block block) {
        ResourceLocation key = ForgeRegistries.BLOCKS.getKey(block);
        assert key != null;
        String path = key.getPath();
        withExistingParent(path, modLoc("block/" + path));
    }

    protected void blockItem(Block block, String statedModel) {
        ResourceLocation key = ForgeRegistries.BLOCKS.getKey(block);
        assert key != null;
        String path = key.getPath();
        withExistingParent(path, modLoc("block/" + path + "_" + statedModel));
    }

    protected static class UnexceptionalItemModelBuilder extends ItemModelBuilder {
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
