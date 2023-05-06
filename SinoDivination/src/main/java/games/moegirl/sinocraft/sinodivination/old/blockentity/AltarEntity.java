package games.moegirl.sinocraft.sinodivination.old.blockentity;

import games.moegirl.sinocraft.sinodivination.old.util.container.InputOnlyContainer;
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
import java.util.Optional;

public class AltarEntity extends BlockEntity {

    private final InputOnlyContainer in = new InputOnlyContainer(1, 1).bindEntityChangeWithUpdate(this);
    @Nullable
    private Direction direction = null;

    public AltarEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyOptional.of(() -> in).cast();
        }
        return super.getCapability(cap, side);
    }

    public ItemStack takeItem() {
        return in.setStackInSlot2(0, ItemStack.EMPTY);
    }

    public ItemStack putItem(ItemStack stack) {
        return in.insertItem2(0, stack, false);
    }

    public Item getItem() {
        return in.getStackInSlot(0).getItem();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Optional<Direction> getDirection() {
        return isRemoved() ? Optional.empty() : Optional.ofNullable(direction);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        in.save(pTag, "Altar");
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        in.load(pTag, "Altar");
    }
}
