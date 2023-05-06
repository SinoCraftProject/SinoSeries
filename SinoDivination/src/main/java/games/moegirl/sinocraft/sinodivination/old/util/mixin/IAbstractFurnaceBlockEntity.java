package games.moegirl.sinocraft.sinodivination.old.util.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IAbstractFurnaceBlockEntity {

    void reUpdateServer(Level pLevel, BlockPos pPos, BlockState pState);
}
