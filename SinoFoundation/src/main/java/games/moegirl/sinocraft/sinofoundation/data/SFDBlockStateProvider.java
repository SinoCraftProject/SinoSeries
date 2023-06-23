package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinocore.block.ChestBlockBase;
import games.moegirl.sinocraft.sinocore.block.Crop;
import games.moegirl.sinocraft.sinocore.data.BlockStateProviderBase;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.block.StoveBlock;
import games.moegirl.sinocraft.sinofoundation.block.WoodDeskBlock;
import games.moegirl.sinocraft.sinofoundation.block.plant.DoublePlantBlock;
import games.moegirl.sinocraft.sinofoundation.block.plant.LucidGanoderma;
import games.moegirl.sinocraft.sinofoundation.block.plant.PlantBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
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

public class SFDBlockStateProvider extends BlockStateProviderBase {

    private final Logger logger = LoggerFactory.getLogger(getName());
    private final ExistingFileHelper fileHelper;

    @SafeVarargs
    public SFDBlockStateProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper, DeferredRegister<? extends Block>... deferredRegisters) {
        super(output, modId, existingFileHelper, deferredRegisters);
        this.fileHelper = existingFileHelper;
    }

    @Override
    protected void registerBlockStatesAndModels() {
        addStove();
        addCrops();
        addWoodDesk();
        addChest();
        addMarble();

        lucidGanoderma();
    }

    private void addMarble() {
        skipBlock(SFDBlocks.MARBLE_WALLS.get());
        wallBlock(SFDBlocks.MARBLE_WALLS.get(), new ModelFile.UncheckedModelFile(modLoc("block/marble_wall_post")), new ModelFile.UncheckedModelFile(modLoc("block/marble_wall_side")), new ModelFile.UncheckedModelFile(modLoc("block/marble_wall_side_tall")));

        skipBlock(SFDBlocks.MARBLE_PILLAR.get());
        logBlock(SFDBlocks.MARBLE_PILLAR.get());
        skipBlock(SFDBlocks.CHISELED_MARBLE_BLOCK.get());
        logBlock(SFDBlocks.CHISELED_MARBLE_BLOCK.get());

        var marbleTextures = modLoc("block/marble_block");
        skipBlock(SFDBlocks.MARBLE_SLAB.get());
        slabBlock(SFDBlocks.MARBLE_SLAB.get(), marbleTextures, marbleTextures);
        skipBlock(SFDBlocks.MARBLE_STAIRS.get());
        stairsBlock(SFDBlocks.MARBLE_STAIRS.get(), marbleTextures);

        var smoothMarbleTextures = modLoc("block/smooth_marble");
        skipBlock(SFDBlocks.SMOOTH_MARBLE_SLAB.get());
        slabBlock(SFDBlocks.SMOOTH_MARBLE_SLAB.get(), smoothMarbleTextures, smoothMarbleTextures);
        skipBlock(SFDBlocks.SMOOTH_MARBLE_STAIRS.get());
        stairsBlock(SFDBlocks.SMOOTH_MARBLE_STAIRS.get(), smoothMarbleTextures);
    }

    private void addStove() {
        var builder = getVariantBuilder(SFDBlocks.STOVE.get());
        var direction = Direction.NORTH;
        for (int i = 0; i < 4; i++) {
            builder.partialState()
                    .with(StoveBlock.FACING, direction)
                    .with(StoveBlock.BURNING, false)
                    .modelForState()
                    .modelFile(models().getExistingFile(modLoc("block/stove_off")))
                    .rotationY(90 * i)
                    .addModel();
            builder.partialState()
                    .with(StoveBlock.FACING, direction)
                    .with(StoveBlock.BURNING, true)
                    .modelForState()
                    .modelFile(models().getExistingFile(modLoc("block/stove_on")))
                    .rotationY(90 * i)
                    .addModel();
            direction = direction.getClockWise();
        }
    }

    private void addCrops() {
        cropsStaged(SFDBlocks.WHITE_RADISH_PLANT.get(), "white_radish_plant");
        cropsStaged(SFDBlocks.SUMMER_RADISH_PLANT.get(), "summer_radish_plant");
        cropsStaged(SFDBlocks.GREEN_RADISH_PLANT.get(), "green_radish_plant");
        cropsStaged(SFDBlocks.GREEN_PEPPER_PLANT.get(), "green_pepper_plant");
        cropsStaged(SFDBlocks.CHILI_PEPPER_PLANT.get(), "chili_pepper_plant");
        cropsStaged(SFDBlocks.CABBAGE_PLANT.get(), "celery_cabbage");
        cropsStaged(SFDBlocks.EGGPLANT_PLANT.get());
        cropsStaged(SFDBlocks.MILLET_PLANT.get());
        cropsStaged(SFDBlocks.SOYBEAN_PLANT.get());

        crop(SFDBlocks.WORMWOOD);
        crop(SFDBlocks.RICE);
        crop(SFDBlocks.REHMANNIA);
        crop(SFDBlocks.DRAGONLIVER_MELON);
        crop(SFDBlocks.SESAME);

        // Todo: qyl27: double crops.
    }

    private void addWoodDesk() {
        var builder = getVariantBuilder(SFDBlocks.WOOD_DESK.get());
        var direction = Direction.NORTH;
        var states = WoodDeskBlock.ConnectState.values();
        for (var i = 0; i < 4; i++) {
            for (var j = 0; j < states.length; j++) {
                builder.partialState()
                        .with(WoodDeskBlock.FACING, direction)
                        .with(WoodDeskBlock.CONNECT_STATE, j)
                        .modelForState()
                        .modelFile(models().getExistingFile(modLoc("block/" + states[j].getName())))
                        .rotationY(90 * i)
                        .addModel();
            }
            direction = direction.getClockWise();
        }
    }

    private void addChest() {
        chest(SFDBlocks.COTINUS_CHEST, SFDBlocks.COTINUS_TRAPPED_CHEST, SFDTrees.COTINUS);
        chest(SFDBlocks.JUJUBE_CHEST, SFDBlocks.JUJUBE_TRAPPED_CHEST, SFDTrees.JUJUBE);
        chest(SFDBlocks.SOPHORA_CHEST, SFDBlocks.SOPHORA_TRAPPED_CHEST, SFDTrees.SOPHORA);
    }

    private void lucidGanoderma() {
        String ganoderma = SFDBlocks.LUCID_GANODERMA.getId().getPath();
        skipBlock(SFDBlocks.LUCID_GANODERMA.get());
        BlockModelBuilder ganodermaModel = models().crop(ganoderma, new ResourceLocation(SinoFoundation.MODID, ModelProvider.BLOCK_FOLDER + "/" + ganoderma));
        getVariantBuilder(SFDBlocks.LUCID_GANODERMA.get()).forAllStatesExcept(state -> {
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

    /// <editor-fold desc="Utility methods.">

    protected void cropsStaged(PlantBlock plant) {
        cropsStaged(plant, plant.getPlantType().getName());
    }

    protected void cropsStaged(PlantBlock plant, String name) {
        var builder = getVariantBuilder(plant);
        var type = plant.getPlantType();
        if (type.getMaxHeightCount() > 1) {
            for (int i = 0; i <= plant.getMaxAge(); i++) {
                builder.partialState().with(plant.getStageProperty(), i).with(DoublePlantBlock.TOP, true).modelForState()
                        .modelFile(models().crop("block/" + name + "_stage_top_" + i,
                                modLoc("block/" + name + "_stage_top_" + i)).renderType("cutout"))
                        .addModel();
                builder.partialState().with(plant.getStageProperty(), i).with(DoublePlantBlock.TOP, false).modelForState()
                        .modelFile(models().crop("block/" + name + "_stage_bottom_" + i,
                                modLoc("block/" + name + "_stage_bottom_" + i)).renderType("cutout"))
                        .addModel();
            }
        } else {
            for (int i = 0; i <= plant.getMaxAge(); i++) {
                builder.partialState().with(plant.getStageProperty(), i).modelForState()
                        .modelFile(models().crop("block/" + name + "_stage_" + i,
                                modLoc("block/" + name + "_stage_" + i)).renderType("cutout"))
                        .addModel();
            }
        }
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

    private void chest(RegistryObject<? extends ChestBlockBase> chestObj, RegistryObject<? extends Block> trappedChestObj, Tree tree) {
        ResourceLocation planksTextures = blockTexture(tree.getBlock(TreeBlockType.PLANKS));
        simpleBlock(chestObj.get(), models().getBuilder(chestObj.getId().getPath()).texture("particle", planksTextures));
        simpleBlock(trappedChestObj.get(), models().getBuilder(trappedChestObj.getId().getPath()).texture("particle", planksTextures));
        chestObj.get().verifyTexture(fileHelper, logger);
    }

    /// </editor-fold>
}
