package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AltarEntity extends BlockEntity {

    final InputOnlyContainer in = new InputOnlyContainer(1, 1).bindEntityChangeWithUpdate(this);
    private Direction direction = Direction.UP;

    public AltarEntity(BlockEntityType<?> entityType, BlockPos worldPosition, BlockState blockState) {
        super(entityType, worldPosition, blockState);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyOptional.of(() -> in).cast();
        }
        return super.getCapability(cap, side);
    }

    public Item getItem() {
        return in.getStackInSlot(0).getItem();
    }

    public ItemStack takeItem() {
        ItemStack itemStack = in.getStackInSlot(0);
        in.setStackInSlot2(0, ItemStack.EMPTY);
        return itemStack;
    }

    public ItemStack putItem(ItemStack itemStack) {
        return in.insertItem2(0, itemStack, false);
    }

    /**
     * 指向绑定的鼎方向，不存在鼎则返回 UP
     * @return 鼎方向
     */
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        in.save(tag, "Altar");
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        in.load(tag, "Altar");
    }
}
