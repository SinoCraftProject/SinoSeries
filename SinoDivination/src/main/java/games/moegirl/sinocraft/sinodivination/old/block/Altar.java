package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinodivination.old.block.base.AltarBlock;
import games.moegirl.sinocraft.sinodivination.old.blockentity.AltarEntity;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.old.blockentity.TripodEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class Altar extends AltarBlock<AltarEntity> {

    public Altar() {
        super(SDBlockEntities.ALTAR);
    }

    @Override
    public ItemStack takeItem(AltarEntity be) {
        return be.takeItem();
    }

    @Override
    public ItemStack putItem(AltarEntity be, ItemStack stack) {
        return be.putItem(stack);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        BlockEntityType<TripodEntity> type = SDBlockEntities.TRIPOD.get();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            Optional<TripodEntity> tripod = pLevel.getBlockEntity(pPos.relative(direction, 4), type);
            if (tripod.isPresent()) {
                AltarEntity ae = (AltarEntity) pLevel.getBlockEntity(pPos);
                tripod.get().getStructure().setAltar(ae, direction.getOpposite());
                break;
            }
        }
    }
}
