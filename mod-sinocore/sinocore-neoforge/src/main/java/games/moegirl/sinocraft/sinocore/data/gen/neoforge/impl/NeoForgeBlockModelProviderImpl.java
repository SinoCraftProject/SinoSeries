package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.model.UnexceptionalBlockModelBuilder;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.model.WeakCheckModelFile;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class NeoForgeBlockModelProviderImpl extends BlockModelProvider {
    private NeoForgeBlockModelProviderDelegate delegate;

    @SafeVarargs
    public NeoForgeBlockModelProviderImpl(NeoForgeDataGenContext context, boolean strict, IRegistry<? extends Block>... modRegisters) {
        super(context.getOutput(), context.getModId(), context.getExistingFileHelper());
        this.modRegisters = modRegisters;
        this.strict = strict;
        this.logger = LoggerFactory.getLogger(getName());
    }

    protected void registerBlockModels() {
        delegate.generateData();
    }

    public void setDelegate(NeoForgeBlockModelProviderDelegate delegate) {
        this.delegate = delegate;
    }

    /// <editor-fold desc="内部实现">

    private final boolean strict;
    private final Logger logger;
    private final List<Pair<ResourceLocation, ResourceLocation>> errModels = new ArrayList<>();

    private final IRegistry<? extends Block>[] modRegisters;
    private final Set<Block> skip = new HashSet<>();
    private final Set<String> added = new HashSet<>();
    private boolean adding = false;

    @Override
    protected void registerModels() {
        adding = true;
        registerBlockModels();
        adding = false;

        Arrays.stream(modRegisters)
                .flatMap(reg -> Streams.stream(reg.getEntries()))
                .filter(ref -> !skip.contains(ref.get()))
                .filter(ref -> !added.contains(ref.getId().getPath()))
                .map(Supplier::get)
                .map(o -> (Block) o)
                .forEach(this::genDefaultBlockModel);
        delegate.generateData();
    }

    protected void genDefaultBlockModel(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ResourceLocation texture = modLoc("block/" + name);
        cubeAll(name, texture);
    }

    public boolean isStrict() {
        return strict;
    }

    public ModelFile.ExistingModelFile weakCheckModel(ResourceLocation path) {
        return new WeakCheckModelFile(path, existingFileHelper, strict, resourceLocation -> {
            errModels.add(new Pair<>(resourceLocation, foldedLoc(resourceLocation)));
            return true;
        });
    }

    public ResourceLocation foldedLoc(ResourceLocation path) {
        return path.getPath().contains("/") ? path :
                ResourceLocation.fromNamespaceAndPath(path.getNamespace(), folder + "/" + path.getPath());
    }

    @Override
    public BlockModelBuilder getBuilder(String path) {
        Preconditions.checkNotNull(path, "Path must not be null");
        if (adding) {
            added(path);
        }

        ResourceLocation rl = path.contains(":") ? mcLoc(path) : modLoc(path);
        ResourceLocation outputLoc = rl.getPath().contains("/") ? rl : ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), folder + "/" + rl.getPath());
        this.existingFileHelper.trackGenerated(outputLoc, MODEL);
        return generatedModels.computeIfAbsent(outputLoc, (p) -> new UnexceptionalBlockModelBuilder(p, existingFileHelper));
    }

    protected void skip(Block... blocks) {
        skip.addAll(Arrays.asList(blocks));
    }

    private void added(String... blocks) {
        added.addAll(Arrays.asList(blocks));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return super.run(output).thenRun(this::printExceptions);
    }

    public void printExceptions() {
        if (!errModels.isEmpty()) {
            logger.warn("Not found baseModel in {}", getName());
            errModels.forEach(pair -> logger.warn("  Key: {}, Path: {}", pair.getFirst(), pair.getSecond()));
        }
        generatedModels.forEach((key, builder) -> {
            if (builder instanceof UnexceptionalBlockModelBuilder b && !b.notExistingTexture.isEmpty()) {
                logger.warn("Not found texture in {} -> {}", getName(), key);
                b.notExistingTexture.forEach(pair ->
                        logger.warn("  Texture: {}, Path: {}", pair.getFirst(), pair.getSecond()));
            }
        });
    }

    /// </editor-fold>
}
