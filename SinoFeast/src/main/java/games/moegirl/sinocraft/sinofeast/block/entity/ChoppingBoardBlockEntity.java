package games.moegirl.sinocraft.sinofeast.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChoppingBoardBlockEntity extends BlockEntity {
    public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    private LazyOptional<IItemHandler> foodHandler;
    public static final String INPUT_FOOD = "input_food";
    public static final String OUTPUT_FOOD = "output_food";
    public ChoppingBoardBlockEntity(BlockPos arg2, BlockState arg3) {
        super(SFBlockEntities.CHOPPING_BOARD_BLOCK_ENTITY.get(), arg2, arg3);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        var food = getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(RuntimeException::new);
        food.insertItem(INPUT, ItemStack.of(tag.getCompound(INPUT_FOOD)),false);
        food.insertItem(OUTPUT,ItemStack.of(tag.getCompound(OUTPUT_FOOD)),false);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        var food = getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(RuntimeException::new);
        tag.put(INPUT_FOOD, food.getStackInSlot(INPUT).serializeNBT());
        tag.put(OUTPUT_FOOD,food.getStackInSlot(OUTPUT).serializeNBT());
        return tag;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (this.foodHandler == null) {
                this.foodHandler = LazyOptional.of(this::createHandler);
            }
            return this.foodHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    private IItemHandler createHandler(){
        return new ItemStackHandler(2);
    }
}
