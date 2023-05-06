package games.moegirl.sinocraft.sinodivination.old.blockentity;

import games.moegirl.sinocraft.sinodivination.old.recipe.AltarRecipe;
import games.moegirl.sinocraft.sinodivination.old.recipe.AltarRecipeContainer;
import games.moegirl.sinocraft.sinodivination.old.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.old.util.container.ComposeItemHandler;
import games.moegirl.sinocraft.sinodivination.old.util.container.InputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.old.util.container.OutputOnlyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TripodEntity extends BlockEntity implements BlockEntityTicker<TripodEntity> {

    @Nullable
    private AltarRecipe recipe = null;
    private int runningTick = 0;
    private final InputOnlyContainer in = new InputOnlyContainer(1)
            .addChecker((slot, stack) -> recipe == null)
            .addChangeListener(__ -> setChangedAndUpdate());
    private final OutputOnlyContainer out = new OutputOnlyContainer(1).bindEntityChangeWithUpdate(this);
    private final ComposeItemHandler compose = new ComposeItemHandler().append(in).append(out);
    private final AltarStructure structure;
    private boolean notifyUpdated = false;
    @Nullable
    private ResourceLocation recipeId = null;

    public TripodEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
        structure = new AltarStructure(pWorldPosition, this);
    }

    public ItemStack takeItem() {
        if (out.getStackInSlot(0).isEmpty()) {
            return in.setStackInSlot2(0, ItemStack.EMPTY);
        } else {
            return out.setStackInSlot2(0, ItemStack.EMPTY);
        }
    }

    public ItemStack getItem() {
        return in.getStackInSlot(0).copy();
    }

    public ItemStack putItem(ItemStack stack) {
        return in.insertItem2(0, stack, false);
    }

    public AltarStructure getStructure() {
        return structure;
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, TripodEntity pBlockEntity) {
        if (pLevel.isClientSide) {
            return;
        }

        if (!structure.isBuilt(pLevel)) {
            recipe = null;
            recipeId = null;
            runningTick = 0;
            setChanged();
            return;
        }

        if (recipeId != null) {
            if (recipe == null || !recipe.getId().equals(recipeId)) {
                recipe = (AltarRecipe) pLevel.getRecipeManager().byKey(recipeId)
                        .filter(r -> r instanceof AltarRecipe)
                        .orElse(null);
            }
            recipeId = null;
        }

        if (recipe == null && in.getStackInSlot(0).isEmpty()) {
            runningTick = 0;
            AltarRecipeContainer container = structure.recipeContainer();
            SDRecipes.ALTAR.match(pLevel, container).ifPresent(r -> {
                recipe = r;
                in.shrink(0, 1);
            });
        }

        if (recipe != null) {
            runningTick++;
            if (runningTick > recipe.getTick()) {
                ItemStack stack = recipe.getResultItem().copy();
                runningTick = 0;
                recipe = null;
                out.setStackInSlot2(0, stack);
            }
            setChanged();
        }

        if (notifyUpdated) {
            pLevel.setBlockAndUpdate(pPos, pState);
            notifyUpdated = false;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("Tick", runningTick);
        in.save(pTag, "InSlot");
        out.save(pTag, "OutSlot");
        if (recipe != null) {
            pTag.putString("Recipe", recipe.getId().toString());
        } else if (recipeId != null) {
            pTag.putString("Recipe", recipeId.toString());
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        runningTick = pTag.getInt("Tick");
        in.load(pTag, "InSlot");
        out.load(pTag, "OutSlot");
        if (pTag.contains("Recipe", Tag.TAG_STRING)) {
            recipeId = new ResourceLocation(pTag.getString("Recipe"));
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyOptional.of(() -> compose).cast();
        }
        return super.getCapability(cap, side);
    }

    public void setChangedAndUpdate() {
        super.setChanged();
        notifyUpdated = true;
    }
}
