package games.moegirl.sinocraft.sinodivination.old.mixin;

import games.moegirl.sinocraft.sinodivination.old.util.mixin.IAbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class MixinAbstractFurnaceBlockEntity implements IAbstractFurnaceBlockEntity {

//    @Shadow
//    protected abstract boolean isLit();
//
//    @Shadow
//    int litTime;
//
//    // 0: input, 1: fuel, 2: result
//    @Shadow
//    protected NonNullList<ItemStack> items;
//
//    @Shadow
//    @Final
//    private RecipeType<? extends AbstractCookingRecipe> recipeType;
//
//    @Shadow
//    public static boolean isFuel(ItemStack pStack) {
//        return false;
//    }
//
//    @Shadow
//    protected abstract int getBurnDuration(ItemStack pFuel);
//
//    @Shadow
//    protected abstract boolean canBurn(@Nullable Recipe<?> p_155006_, NonNullList<ItemStack> p_155007_, int p_155008_);
//
//    @Shadow
//    int litDuration;
//
//    @Shadow
//    int cookingProgress;
//
//    @Shadow
//    int cookingTotalTime;
//
//    @Shadow
//    private static int getTotalCookTime(Level pLevel, RecipeType<? extends AbstractCookingRecipe> pRecipeType, Container pContainer) {
//        return 0;
//    }
//
//    @Shadow
//    protected abstract boolean burn(@Nullable Recipe<?> p_155027_, NonNullList<ItemStack> p_155028_, int p_155029_);
//
//    @Shadow
//    public abstract void setRecipeUsed(@Nullable Recipe<?> pRecipe);
//
//    /**
//     * @see AbstractFurnaceBlockEntity#serverTick(Level, BlockPos, BlockState, AbstractFurnaceBlockEntity)
//     */
//    @Override
//    public void reUpdateServer(Level pLevel, BlockPos pPos, BlockState pState) {
//        if (new RoundChecker(pLevel, pPos).near(0, SDBlocks.BELLOWS)) {
//            boolean flag = isLit();
//            boolean flag1 = false;
//            if (isLit()) {
//                --litTime;
//            }
//
//            ItemStack itemstack = items.get(1);
//            if (isLit() || !itemstack.isEmpty() && !items.get(0).isEmpty()) {
//                AbstractCookingRecipe recipe = pLevel.getRecipeManager().getRecipeFor(recipeType, (AbstractFurnaceBlockEntity) (Object) this, pLevel).orElse(null);
//                int i = ((AbstractFurnaceBlockEntity) (Object) this).getMaxStackSize();
//                if (!isLit() && canBurn(recipe, items, i)) {
//                    litTime -= getBurnDuration(itemstack);
//                    litDuration = litTime;
//                    if (isLit()) {
//                        flag1 = true;
//                        if (itemstack.hasCraftingRemainingItem()) {
//                            items.set(1, itemstack.getCraftingRemainingItem());
//                        } else if (!itemstack.isEmpty()) {
//                            itemstack.shrink(1);
//                            if (itemstack.isEmpty()) {
//                                items.set(1, itemstack.getCraftingRemainingItem());
//                            }
//                        }
//                    }
//                }
//
//                if (isLit() && canBurn(recipe, items, i)) {
//                    ++cookingProgress;
//                    if (cookingProgress == cookingTotalTime) {
//                        cookingProgress = 0;
//                        cookingTotalTime = getTotalCookTime(pLevel, recipeType, (AbstractFurnaceBlockEntity) (Object) this);
//                        if (burn(recipe, items, i)) {
//                            setRecipeUsed(recipe);
//                        }
//                    }
//                } else {
//                    cookingProgress = 0;
//                }
//            } else if (!isLit() && cookingProgress > 0) {
//                cookingProgress = Mth.clamp(cookingProgress - 2, 0, cookingTotalTime);
//            }
//
//            if (flag != isLit()) {
//                flag1 = true;
//                pState = pState.setValue(AbstractFurnaceBlock.LIT, isLit());
//                pLevel.setBlock(pPos, pState, 3);
//            }
//
//            if (flag1) {
//                pLevel.blockEntityChanged(pPos);
//                if (!pState.isAir()) {
//                    pLevel.updateNeighbourForOutputSignal(pPos, pState.getBlock());
//                }
//            }
//        }
//    }
//
//    @Inject(method = "serverTick", at = @At("RETURN"))
//    private static void injectServerTick(Level pLevel, BlockPos pPos, BlockState pState, AbstractFurnaceBlockEntity pBlockEntity, CallbackInfo ci) {
//        if (pBlockEntity.getType() == BlockEntityType.BLAST_FURNACE) {
//            ((IAbstractFurnaceBlockEntity) pBlockEntity).reUpdateServer(pLevel, pPos, pState);
//        }
//    }
}
