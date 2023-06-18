package games.moegirl.sinocraft.sinofoundation.block.entity;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.util.OwnerChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class CotinusDoorEntity extends CotinusEntity {
    private boolean isAbove = false;

    public CotinusDoorEntity(Tree tree, BlockPos worldPosition, BlockState blockState) {
        super(tree.getBlockEntityType(TreeBlockType.DOOR), worldPosition, blockState);
    }

    public void above() {
        isAbove = true;
        setChanged();
    }

    @Override
    public boolean isOpen() {
        if (level != null && isAbove && level.getBlockEntity(worldPosition.below()) instanceof CotinusDoorEntity door) {
            // 底部：依赖于顶部
            return door.isOpen();
        }
        return super.isOpen();
    }

    @Override
    public void setOpen(boolean isOpen) {
        if (level != null && isAbove && level.getBlockEntity(worldPosition.below()) instanceof CotinusDoorEntity door) {
            // 底部：依赖于顶部
            door.setOpen(isOpen);
        }
        super.setOpen(isOpen);
    }

    @Override
    public OwnerChecker owner() {
        if (level != null && isAbove && level.getBlockEntity(worldPosition.below()) instanceof CotinusDoorEntity door) {
            // 底部：依赖于顶部
            return door.checker;
        }
        return checker;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean(SinoFoundation.MODID + ".above", true);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.getBoolean(SinoFoundation.MODID + ".above")) {
            isAbove = true;
        }
    }
}
