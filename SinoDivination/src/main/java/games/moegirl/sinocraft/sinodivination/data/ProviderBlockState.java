package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinocore.data.BlockStateProviderBase;
import games.moegirl.sinocraft.sinodivination.block.LucidGanoderma;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinocore.block.Crop;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import static games.moegirl.sinocraft.sinodivination.SinoDivination.MODID;

class ProviderBlockState extends BlockStateProviderBase {

    public ProviderBlockState(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), MODID, event.getExistingFileHelper(), SDBlocks.REGISTRY);
    }

    @Override
    protected void registerBlockStatesAndModels() {
        skipBlock(SDBlocks.KETTLE_POT.get());
        crop(SDBlocks.WORMWOOD);
        crop(SDBlocks.RICE);
        crop(SDBlocks.REHMANNIA);
        crop(SDBlocks.DRAGONLIVER_MELON);
        crop(SDBlocks.SESAME);
        lucidGanoderma();
    }

    /**
     * 创建任意成长阶段数的作物
     *
     * @param crop 作物
     * @param <T>  作物
     */
    private <T extends Block & Crop<?>> void crop(RegistryObject<T> crop) {
        skipBlock(crop.get());
        ResourceLocation name = crop.getId();

        VariantBlockStateBuilder builder = getVariantBuilder(crop.get());
        IntegerProperty property = crop.get().getAgeProperty();

        StateDefinition<Block, BlockState> definition = crop.get().getStateDefinition();
        EnumProperty<DoubleBlockHalf> half = BlockStateProperties.DOUBLE_BLOCK_HALF;
        if (definition.getProperties().contains(half)) {
            for (Integer age : property.getPossibleValues()) {
                String topModelName = name.getPath() + "_stage_top_" + age;
                String bottomModelName = name.getPath() + "_stage_bottom_" + age;
                BlockModelBuilder topModel = models()
                        .crop(topModelName, new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + topModelName))
                        .renderType("cutout");
                BlockModelBuilder bottomModel = models()
                        .crop(bottomModelName, new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + bottomModelName))
                        .renderType("cutout");
                builder = builder.partialState().with(property, age).with(half, DoubleBlockHalf.UPPER)
                        .modelForState().modelFile(topModel).addModel();
                builder = builder.partialState().with(property, age).with(half, DoubleBlockHalf.LOWER)
                        .modelForState().modelFile(bottomModel).addModel();
            }
        } else {
            for (Integer age : property.getPossibleValues()) {
                String modelName = name.getPath() + "_stage_" + age;
                BlockModelBuilder model = models()
                        .crop(modelName, new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + modelName))
                        .renderType("cutout");
                builder = builder.partialState().with(property, age)
                        .modelForState().modelFile(model).addModel();
            }
        }
    }

    private void lucidGanoderma() {
        String ganoderma = SDBlocks.LUCID_GANODERMA.getId().getPath();
        skipBlock(SDBlocks.LUCID_GANODERMA.get());
        BlockModelBuilder ganodermaModel = models().crop(ganoderma, new ResourceLocation(MODID, ModelProvider.BLOCK_FOLDER + "/" + ganoderma));
        getVariantBuilder(SDBlocks.LUCID_GANODERMA.get()).forAllStatesExcept(state -> {
            Direction facing = state.getValue(LucidGanoderma.LOG_FACING);
            int xRot, yRot;
            switch (facing) {
                case UP -> {
                    xRot = 270;
                    yRot = 0;
                }
                case DOWN -> {
                    xRot = 90;
                    yRot = 0;
                }
                case EAST -> {
                    xRot = 0;
                    yRot = 90;
                }
                case WEST -> {
                    xRot = 0;
                    yRot = 270;
                }
                case NORTH -> {
                    xRot = 0;
                    yRot = 0;
                }
                default -> {
                    xRot = 0;
                    yRot = 180;
                }
            }
            return ConfiguredModel.builder()
                    .modelFile(ganodermaModel)
                    .rotationX(xRot)
                    .rotationY(yRot)
                    .build();
        }, StairBlock.WATERLOGGED);
    }
}
