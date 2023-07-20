package games.moegirl.sinocraft.sinodivination.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractAutoBlockStateProvider;
import games.moegirl.sinocraft.sinodivination.SDTrees;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.LucidGanoderma;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;

import static games.moegirl.sinocraft.sinodivination.SinoDivination.MODID;

class SDBlockStateProvider extends AbstractAutoBlockStateProvider {

    public SDBlockStateProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), MODID, event.getExistingFileHelper(), SDBlocks.REGISTRY);
    }

    @Override
    protected void registerBlockStatesAndModels() {
        skipBlock(SDBlocks.KETTLE_POT.get());
        crop(SDBlocks.REHMANNIA);
        crop(SDBlocks.DRAGONLIVER_MELON);
        lucidGanoderma();
        chest(SDBlocks.COTINUS_CHEST, SDBlocks.COTINUS_TRAPPED_CHEST, SDTrees.COTINUS);
        chest(SDBlocks.SOPHORA_CHEST, SDBlocks.SOPHORA_TRAPPED_CHEST, SDTrees.SOPHORA);
    }

    private void lucidGanoderma() {
        String ganoderma = SDBlocks.LUCID_GANODERMA.getId().getPath();
        skipBlock(SDBlocks.LUCID_GANODERMA.get());
        BlockModelBuilder ganodermaModel = models().crop(ganoderma, new ResourceLocation(SinoDivination.MODID, ModelProvider.BLOCK_FOLDER + "/" + ganoderma));
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
