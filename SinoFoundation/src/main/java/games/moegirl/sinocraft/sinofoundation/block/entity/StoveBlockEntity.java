package games.moegirl.sinocraft.sinofoundation.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class StoveBlockEntity extends BlockEntity implements BlockEntityTicker<StoveBlockEntity> {
    public StoveBlockEntity(BlockPos pos, BlockState state) {
        super(SFDBlockEntities.STOVE.get(), pos, state);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, StoveBlockEntity blockEntity) {

    }
}
