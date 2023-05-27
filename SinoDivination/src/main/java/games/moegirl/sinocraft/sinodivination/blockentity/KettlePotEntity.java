package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.data.SDTags;
import games.moegirl.sinocraft.sinodivination.util.RecipeProcessor;
import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.recipe.KettlePotRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.util.container.OutputOnlyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class KettlePotEntity extends BlockEntity implements BlockEntityTicker<KettlePotEntity> {

    private final InputOnlyContainer input = new InputOnlyContainer(3).bindEntityChange(this);
    private final FluidTank fluid = new FluidTank(8000);
    private final KettlePotProcessor processor;

    public KettlePotEntity(BlockEntityType<?> arg, BlockPos arg2, BlockState arg3) {
        super(arg, arg2, arg3);
        processor = new KettlePotProcessor(SDRecipes.KETTLE_POT);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, KettlePotEntity kpe) {
        if (level.isClientSide) {
            return;
        }

        // 下方是否有火
        if (level.getBlockState(worldPosition.below()).is(SDTags.HEAT_SOURCE)) {
            processor.tick(this);
        } else {
            processor.back();
        }

        if (processor.isDataChanged()) {
            setChanged();
        }

        if (processor.isStatusChanged() && processor.getStatus() == RecipeProcessor.Status.BLOCKING) {
            level.setBlockAndUpdate(worldPosition, getBlockState());
        }
    }

    public int getProgress() {
        return processor.getProgress();
    }

    public Optional<KettlePotRecipe> getRecipe() {
        return processor.getRecipe();
    }

    public FluidTank getTank() {
        return fluid;
    }

    public InvWrapper getInput() {
        return input;
    }

    public boolean isReady() {
        return processor.getStatus() == RecipeProcessor.Status.READY;
    }

    public RecipeProcessor.Status getStatus() {
        return processor.getStatus();
    }

    public ItemStack getResult() {
        return processor.getResult();
    }

    public void run() {
        processor.run();
    }

    public Optional<ItemStack> takeResult(ItemStack container) {
        return processor.takeItem(container);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyOptional.of(() -> input).cast();
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return LazyOptional.of(() -> fluid).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        input.save(tag, "Input");
        tag.put("Fluid", fluid.getFluid().writeToNBT(new CompoundTag()));
        processor.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        input.load(tag, "Input");
        fluid.setFluid(FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid")));
        processor.load(tag);
    }
}
