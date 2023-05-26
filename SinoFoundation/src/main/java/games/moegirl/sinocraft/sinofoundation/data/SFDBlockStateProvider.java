package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinocore.data.BlockStateProviderBase;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.block.StoveBlock;
import games.moegirl.sinocraft.sinofoundation.block.plant.DoublePlantBlock;
import games.moegirl.sinocraft.sinofoundation.block.plant.PlantBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;

public class SFDBlockStateProvider extends BlockStateProviderBase {
    public SFDBlockStateProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper, DeferredRegister<? extends Block>... deferredRegisters) {
        super(output, modId, existingFileHelper, deferredRegisters);
    }

    @Override
    protected void registerBlockStatesAndModels() {
        addStove();

        addCrops();
    }

    private void addStove() {
        VariantBlockStateBuilder stoveBuilder = getVariantBuilder(SFDBlocks.STOVE.get());
        Direction stoveDirection = Direction.NORTH;
        for (int i = 0; i < 4; i++) {
            stoveBuilder.partialState()
                    .with(StoveBlock.FACING, stoveDirection)
                    .with(StoveBlock.BURNING, false)
                    .modelForState()
                    .modelFile(models().getExistingFile(modLoc("block/stove_off")))
                    .rotationY(90 * i)
                    .addModel();
            stoveBuilder.partialState()
                    .with(StoveBlock.FACING, stoveDirection)
                    .with(StoveBlock.BURNING, true)
                    .modelForState()
                    .modelFile(models().getExistingFile(modLoc("block/stove_on")))
                    .rotationY(90 * i)
                    .addModel();
            stoveDirection = stoveDirection.getClockWise();
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

        // Todo: qyl27: double crops.
    }

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
}
