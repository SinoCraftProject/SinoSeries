package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinocore.data.BlockStateProviderBase;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.block.StoveBlock;
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
}
