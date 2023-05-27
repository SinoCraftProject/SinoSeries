package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

/**
 * 槐木门
 */
public class SophoraDoorEntity extends SophoraEntity {
    private boolean isAbove = false;

    public SophoraDoorEntity(Tree tree, BlockPos worldPosition, BlockState blockState) {
        super(tree.getBlockEntityType(TreeBlockType.DOOR), worldPosition, blockState);
    }

    public void above() {
        isAbove = true;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (isAbove) {
            tag.putBoolean(SinoDivination.MODID + ".above", true);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.getBoolean(SinoDivination.MODID + ".above")) {
            isAbove = true;
        }
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, GameEvent gameEvent, GameEvent.Context context, Vec3 pos) {
        return isAbove && super.handleGameEvent(level, gameEvent, context, pos);
    }
}
