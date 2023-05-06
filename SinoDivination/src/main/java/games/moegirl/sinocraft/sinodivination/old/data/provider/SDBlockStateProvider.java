package games.moegirl.sinocraft.sinodivination.old.data.provider;

import games.moegirl.sinocraft.sinocore.data.BlockStateProviderBase;
import games.moegirl.sinocraft.sinodivination.old.block.LucidGanoderma;
import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.old.block.base.Crop;
import games.moegirl.sinocraft.sinodivination.old.block.base.WoodenChest;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import static games.moegirl.sinocraft.sinodivination.SinoDivination.MODID;

public class SDBlockStateProvider extends BlockStateProviderBase {

    public SDBlockStateProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), MODID, event.getExistingFileHelper(), SDBlocks.REGISTRY);
    }

    @Override
    protected void registerBlockStatesAndModels() {
        chest(SDBlocks.COTINUS_CHEST);
        chest(SDBlocks.JUJUBE_CHEST);
        chest(SDBlocks.SOPHORA_CHEST);
        skipBlock(SDBlocks.KETTLE_POT.get());
        crop(SDBlocks.WORMWOOD);
        crop(SDBlocks.RICE);
        crop(SDBlocks.GARLIC);
        crop(SDBlocks.REHMANNIA);
        crop(SDBlocks.DRAGONLIVER_MELON);
        crop(SDBlocks.SESAME);

        String ganoderma = SDBlocks.LUCID_GANODERMA.getId().getPath();
        BlockModelBuilder ganodermaModel = models().crop(ganoderma, new ResourceLocation(MODID, ModelProvider.BLOCK_FOLDER + "/" + ganoderma));
        facing(ganodermaModel, LucidGanoderma.LOG_FACING);

        super.registerStatesAndModels();
    }

    private <T extends WoodenChest> void chest(RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        simpleBlock(chest, models().getBuilder(chestObj.getId().getPath()).texture("particle", blockTexture(chest.planks())));
    }

    private <T extends Block & Crop<?>> void crop(RegistryObject<T> cropSup) {
        ResourceLocation name = cropSup.getId();

        VariantBlockStateBuilder builder = getVariantBuilder(cropSup.get());
        IntegerProperty property = cropSup.get().getAgeProperty();

        StateDefinition<Block, BlockState> definition = cropSup.get().getStateDefinition();
        EnumProperty<DoubleBlockHalf> half = BlockStateProperties.DOUBLE_BLOCK_HALF;
        if (definition.getProperties().contains(half)) {
            for (Integer age : property.getPossibleValues()) {
                String topModelName = name.getPath() + "_stage_top_" + age;
                String bottomModelName = name.getPath() + "_stage_bottom_" + age;
                BlockModelBuilder topModel = models().crop(topModelName,
                        new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + topModelName));
                BlockModelBuilder bottomModel = models().crop(bottomModelName,
                        new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + bottomModelName));
                builder = builder.partialState().with(property, age).with(half, DoubleBlockHalf.UPPER)
                        .modelForState().modelFile(topModel).addModel();
                builder = builder.partialState().with(property, age).with(half, DoubleBlockHalf.LOWER)
                        .modelForState().modelFile(bottomModel).addModel();
            }
        } else {
            for (Integer age : property.getPossibleValues()) {
                String modelName = name.getPath() + "_stage_" + age;
                BlockModelBuilder model = models().crop(modelName,
                        new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + modelName));
                builder = builder.partialState().with(property, age)
                        .modelForState().modelFile(model).addModel();
            }
        }
    }

    private <T extends Block> void facing(BlockModelBuilder model, Property<Direction> property) {
        this.getVariantBuilder(SDBlocks.LUCID_GANODERMA.get()).forAllStatesExcept(state -> {
            Direction facing = state.getValue(property);
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
                    .modelFile(model)
                    .rotationX(xRot)
                    .rotationY(yRot)
                    .build();
        }, StairBlock.WATERLOGGED);
    }
}
