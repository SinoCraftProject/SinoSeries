package games.moegirl.sinocraft.sinofoundation.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractAutoBlockStateProvider;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.block.StoveBlock;
import games.moegirl.sinocraft.sinofoundation.block.WoodChairsBlock;
import games.moegirl.sinocraft.sinofoundation.block.WoodDeskBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;

public class SFDBlockStateProvider extends AbstractAutoBlockStateProvider {

    @SafeVarargs
    public SFDBlockStateProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper, DeferredRegister<? extends Block>... deferredRegisters) {
        super(output, modId, existingFileHelper, deferredRegisters);
    }

    @Override
    protected void registerBlockStatesAndModels() {
        addStove();
        addCrops();
        addWoodDesk();
        addWoodChairs();
        addChest();
        addMarble();
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

    private void addWoodChairs() {
        var builder = getVariantBuilder(SFDBlocks.WOOD_CHAIRS.get());
        var direction = Direction.EAST;
        for (var i = 0; i < 4; i++) {
            builder.partialState()
                    .with(WoodChairsBlock.FACING, direction)
                    .modelForState()
                    .modelFile(models().getExistingFile(modLoc("block/wood_chairs")))
                    .rotationY(90 * i)
                    .addModel();
            direction = direction.getClockWise();
        }
    }

    private void addCrops() {
        crop(SFDBlocks.WHITE_RADISH_PLANT);
        crop(SFDBlocks.SUMMER_RADISH_PLANT);
        crop(SFDBlocks.GREEN_RADISH_PLANT);
        crop(SFDBlocks.GREEN_PEPPER_PLANT);
        crop(SFDBlocks.CHILI_PEPPER_PLANT);
        crop(SFDBlocks.CABBAGE_PLANT);
        crop(SFDBlocks.EGGPLANT_PLANT);
        crop(SFDBlocks.MILLET_PLANT);
        crop(SFDBlocks.SOYBEAN_PLANT);
        crop(SFDBlocks.GARLIC_PLANT);
        crop(SFDBlocks.WORMWOOD);
        crop(SFDBlocks.RICE);
        crop(SFDBlocks.SESAME);
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
        chest(SFDBlocks.JUJUBE_CHEST, SFDBlocks.JUJUBE_TRAPPED_CHEST, SFDTrees.JUJUBE);
    }
}
