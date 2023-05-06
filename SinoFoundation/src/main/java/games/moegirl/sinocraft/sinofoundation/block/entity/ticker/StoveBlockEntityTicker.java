package games.moegirl.sinocraft.sinofoundation.block.entity.ticker;

import games.moegirl.sinocraft.sinofoundation.block.StoveBlock;
import games.moegirl.sinocraft.sinofoundation.block.entity.StoveBlockEntity;
import games.moegirl.sinocraft.sinofoundation.capability.SFDCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StoveBlockEntityTicker {
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (!(blockEntity instanceof StoveBlockEntity)) {
            return;
        }

        var heatSource = blockEntity.getCapability(SFDCapabilities.HEAT_SOURCE_BLOCK).orElseThrow(RuntimeException::new);
        var combustible = blockEntity.getCapability(SFDCapabilities.COMBUSTIBLE_BLOCK).orElseThrow(RuntimeException::new);

        if (heatSource.isWarmingUp()) {
            heatSource.warmUp();
        } else if (heatSource.isCoolingDown()) {
            heatSource.coolDown();
        }

        if (combustible.isBurning()) {
            if (!state.getValue(StoveBlock.BURNING)) {
                level.setBlock(pos, state.setValue(StoveBlock.BURNING, true), Block.UPDATE_ALL);
            }

            combustible.subBurnTime(1);
        } else {
            heatSource.resetHeatValue();

            if (state.getValue(StoveBlock.BURNING)) {
                level.setBlock(pos, state.setValue(StoveBlock.BURNING, true), Block.UPDATE_ALL);
            }
        }
    }
}
