package games.moegirl.sinocraft.sinocore.datagen;

import games.moegirl.sinocraft.sinocore.data.gen.model.AbstractBlockModelProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * The default base class of BlockState data generator in SinoCore.
 *
 * @author qyl27
 */
public abstract class AbstractAutoBlockStateProvider extends BlockStateProvider {

    // 适配 DeferredRegister 自动添加
    private final DeferredRegister<? extends Block>[] deferredRegisters;
    // 在其他地方已经创建方块，或自定义过模型，因此跳过方块
    // 所有直接或间接通过 getVariantBuilder 或 getMultipartBuilder 的方块自动跳过
    private final Set<Block> skipBlocks = new HashSet<>();
    private boolean adding = false;

    // 纹理异常与 Logger
    protected final Logger logger;
    protected final AbstractBlockModelProvider blockModels;
    protected final AbstractAutoItemModelProvider itemModels;

    // 其他
    protected final String modid;
    protected final ExistingFileHelper fileHelper;

    @SafeVarargs
    public AbstractAutoBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper, DeferredRegister<? extends Block>... deferredRegisters) {
        super(output, modid, exFileHelper);

        this.deferredRegisters = deferredRegisters;
        this.modid = modid;
        this.fileHelper = exFileHelper;
        this.logger = LoggerFactory.getLogger(getName());
        this.blockModels = new AbstractBlockModelProvider(output, modid, exFileHelper) {
            @Override
            protected void registerModels() {
            }
        };
        this.itemModels = new AbstractAutoItemModelProvider(output, modid, this.blockModels.existingFileHelper) {
            @Override
            protected void registerItemModels() {
            }
        };
    }

    /**
     * 在此添加方块模型
     */
    protected abstract void registerBlockStatesAndModels();

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return super.run(output)
                // 输出无效方块模型
                .thenRun(blockModels::printExceptions)
                // 输出无效物品纹理
                .thenRun(itemModels::printExceptions);
    }

    @Override
    protected void registerStatesAndModels() {
        adding = true;
        registerBlockStatesAndModels();
        adding = false;

        // 遍历 DR，缺少模型的方块自动按普通方块添加
        Arrays.stream(deferredRegisters)
                .flatMap(dr -> dr.getEntries().stream())
                .map(RegistryObject::get)
                .filter(b -> !skipBlocks.contains(b))
                .forEach(this::simpleBlock);
    }

    @Override
    public VariantBlockStateBuilder getVariantBuilder(Block b) {
        if (adding) skipBlock(b);
        return super.getVariantBuilder(b);
    }

    @Override
    public MultiPartBlockStateBuilder getMultipartBuilder(Block b) {
        if (adding) skipBlock(b);
        return super.getMultipartBuilder(b);
    }

    /**
     * 跳过方块
     * <p>所有直接或间接通过 {@link #getVariantBuilder(Block)}} 或 {@link #getMultipartBuilder(Block)} 创建的方块模型已自动跳过</p>
     */
    protected void skipBlock(Block... blocks) {
        Collections.addAll(skipBlocks, blocks);
    }

    public BlockModelProvider models() {
        return blockModels;
    }

    public ItemModelProvider itemModels() {
        return itemModels;
    }

    protected ResourceLocation blockLoc(ResourceLocation path) {
        return new ResourceLocation(path.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + path.getPath());
    }

//    /**
//     * 创建任意成长阶段数的作物
//     *
//     * @param crop 作物
//     * @param <T>  作物
//     */
//    protected <T extends Block & Crop<?>> void crop(RegistryObject<T> crop) {
//        skipBlock(crop.get());
//        ResourceLocation name = crop.getId();
//
//        VariantBlockStateBuilder builder = getVariantBuilder(crop.get());
//        IntegerProperty property = crop.get().getAgeProperty();
//
//        StateDefinition<Block, BlockState> definition = crop.get().getStateDefinition();
//        EnumProperty<DoubleBlockHalf> half = BlockStateProperties.DOUBLE_BLOCK_HALF;
//        if (definition.getProperties().contains(half)) {
//            for (Integer age : property.getPossibleValues()) {
//                String topModelName = name.getPath() + "_stage_top_" + age;
//                String bottomModelName = name.getPath() + "_stage_bottom_" + age;
//                BlockModelBuilder topModel = models()
//                        .crop(topModelName, new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + topModelName))
//                        .renderType("cutout");
//                BlockModelBuilder bottomModel = models()
//                        .crop(bottomModelName, new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + bottomModelName))
//                        .renderType("cutout");
//                builder = builder.partialState().with(property, age).with(half, DoubleBlockHalf.UPPER)
//                        .modelForState().modelFile(topModel).addModel();
//                builder = builder.partialState().with(property, age).with(half, DoubleBlockHalf.LOWER)
//                        .modelForState().modelFile(bottomModel).addModel();
//            }
//        } else {
//            for (Integer age : property.getPossibleValues()) {
//                String modelName = name.getPath() + "_stage_" + age;
//                BlockModelBuilder model = models()
//                        .crop(modelName, new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + modelName))
//                        .renderType("cutout");
//                builder = builder.partialState().with(property, age)
//                        .modelForState().modelFile(model).addModel();
//            }
//        }
//    }

//    /**
//     * 创建箱子与陷阱箱
//     *
//     * @param chestObj        箱子
//     * @param trappedChestObj 陷阱箱
//     * @param tree            树，需要提取木板材质
//     */
//    protected void chest(RegistryObject<? extends ChestBlockBase> chestObj, RegistryObject<? extends Block> trappedChestObj, Tree tree) {
//        ResourceLocation planksTextures = blockTexture(tree.getBlock(TreeBlockType.PLANKS));
//        simpleBlock(chestObj.get(), models().getBuilder(chestObj.getId().getPath()).texture("particle", planksTextures));
//        simpleBlock(trappedChestObj.get(), models().getBuilder(trappedChestObj.getId().getPath()).texture("particle", planksTextures));
//        chestObj.get().verifyTexture(fileHelper, logger);
//    }

}
