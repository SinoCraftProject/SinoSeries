package games.moegirl.sinocraft.sinocore.data.gen.forge.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.data.gen.forge.ForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.data.gen.forge.model.UnexceptionalItemModelBuilder;
import games.moegirl.sinocraft.sinocore.data.gen.forge.model.WeakCheckModelFile;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * The default base class of ItemModel data generator in SinoCore.
 *
 * @author qyl27
 */
public class ForgeItemModelProviderImpl extends ItemModelProvider {

    private ForgeItemModelProviderDelegate delegate;

    // default strict is false
    @SafeVarargs
    public ForgeItemModelProviderImpl(ForgeDataGenContextImpl context, boolean strict, IRegistry<? extends ItemLike>... modRegisters) {
        super(context.getOutput(), context.getModId(), context.getExistingFileHelper());
        this.modRegisters = modRegisters;
        this.strict = strict;
        this.logger = LoggerFactory.getLogger(getName());
    }

    /**
     * 在这里添加物品模型
     */
    protected void registerItemModels() {
        delegate.generateData();
    }

    public void setDelegate(ForgeItemModelProviderDelegate delegate) {
        this.delegate = delegate;
    }

    /// <editor-fold desc="内部实现">

    public static final ResourceLocation GENERATED = new ResourceLocation("minecraft", "item/generated");
    public static final ResourceLocation HANDHELD = new ResourceLocation("minecraft", "item/handheld");

    // 异常纹理与警告
    // strict: 严格模式，当纹理缺失时产生异常而不是警告
    private final boolean strict;
    private final Logger logger;
    private final List<Pair<ResourceLocation, ResourceLocation>> errModels = new ArrayList<>();

    // DR 适配
    private final IRegistry<? extends ItemLike>[] modRegisters;
    private final Set<Item> skip = new HashSet<>();
    private final Set<String> added = new HashSet<>();
    private boolean adding = false;
    /**
     * Register item baseModel.
     */
    @Override
    protected final void registerModels() {
        adding = true;
        registerItemModels();
        adding = false;

        Arrays.stream(modRegisters)
                .flatMap(reg -> Streams.stream(reg.getEntries()))
                // 跳过手动跳过的物品
                .filter(ref -> !skip.contains(ref.get().asItem()))
                // 跳过直接或间接通过 getBuilder 创建模型的物品
                .filter(ref -> !added.contains(ref.getId().getPath()))
                .map(ref -> ref.get().asItem())
                .forEach(this::genDefaultItemModel);
    }

    /**
     * 创建默认模型
     *
     * <table>
     *     <tr>
     *         <th>物品类型</th>
     *         <th>模型类型</th>
     *     </tr>
     *     <tr>
     *         <td>BlockItem-有纹理</td>
     *         <td>普通物品</td>
     *     </tr>
     *     <tr>
     *         <td>BlockItem-无纹理</td>
     *         <td>方块物品</td>
     *     </tr>
     *     <tr>
     *         <td>TieredItem</td>
     *         <td>手持物品</td>
     *     </tr>
     *     <tr>
     *         <td>其他</td>
     *         <td>普通物品</td>
     *     </tr>
     * </table>
     */
    protected void genDefaultItemModel(Item item) {
        if (item instanceof BlockItem) {
            String name = ForgeRegistries.ITEMS.getKey(item).getPath();
            ResourceLocation texture = modLoc("item/" + name);
            if (existingFileHelper.exists(texture, TEXTURE)) {
                generated(item);
            } else {
                withExistingParent(name, modLoc("block/" + name));
            }
        } else if (item instanceof TieredItem) {
            handheld(item);
        } else {
            generated(item);
        }
    }

    @Override
    public ItemModelBuilder getBuilder(String path) {
        Preconditions.checkNotNull(path, "Path must not be null");
        if (adding) {
            added(path);
        }

        var loc = path.contains(":") ? mcLoc(path) : modLoc(path);
        var outputLoc = foldedLoc(loc);
        this.existingFileHelper.trackGenerated(outputLoc, MODEL);
        return generatedModels.computeIfAbsent(outputLoc, (p) -> new UnexceptionalItemModelBuilder(p, existingFileHelper));
    }

    protected void skip(Item... items) {
        skip.addAll(Arrays.asList(items));
    }

    private void added(String... items) {
        added.addAll(Arrays.asList(items));
    }

    // 异常纹理处理

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
    public CompletableFuture<?> run(CachedOutput output) {
        return super.run(output).thenRun(this::printExceptions);
    }

    public void printExceptions() {
        if (!errModels.isEmpty()) {
            logger.warn("Not found baseModel in {}", getName());
            errModels.forEach(pair -> logger.warn("  Key: {}, Path: {}", pair.getFirst(), pair.getSecond()));
        }

        generatedModels.forEach((key, builder) -> {
            if (builder instanceof UnexceptionalItemModelBuilder b && !b.isEmpty()) {
                logger.warn("Not found texture in {} -> {}", getName(), key);
                b.forEach(pair -> logger.warn("  Texture: {}, Path: {}", pair.getFirst(), pair.getSecond()));
            }
        });
    }

    protected boolean isStrict() {
        return strict;
    }

    public ModelFile.ExistingModelFile weakCheckModel(ResourceLocation path) {
        return new WeakCheckModelFile(path, existingFileHelper, strict, resourceLocation -> {
            errModels.add(new Pair<>(resourceLocation, foldedLoc(resourceLocation)));
            return true;
        });
    }

    // 工具方法与默认模型

    public ResourceLocation blockLoc(ResourceLocation path) {
        return new ResourceLocation(path.getNamespace(), BLOCK_FOLDER + "/" + path.getPath());
    }

    public ResourceLocation foldedLoc(ResourceLocation path) {
        return path.getPath().contains("/") ? path :
                new ResourceLocation(path.getNamespace(), folder + "/" + path.getPath());
    }

    protected ItemModelBuilder withBlockParent(Block block) {
        var name = ForgeRegistries.BLOCKS.getKey(block);
        assert name != null;
        return withExistingParent(name.getPath(), blockLoc(name));
    }

    protected ItemModelBuilder withBlockParent(IRegRef<Block, ?> block) {
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

    @Override
    public Path getPath(ItemModelBuilder model) {
        return super.getPath(model);
    }

    /// </editor-fold>
}
