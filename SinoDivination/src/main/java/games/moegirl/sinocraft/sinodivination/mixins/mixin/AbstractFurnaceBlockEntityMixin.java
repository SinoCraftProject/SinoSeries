package games.moegirl.sinocraft.sinodivination.mixins.mixin;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.mixins.interfaces.IAbstractFurnaceBlockEntitySpeedUp;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin implements IAbstractFurnaceBlockEntitySpeedUp {

    @Shadow
    protected abstract boolean isLit();

    private int sinodivination$cookingProgress0;
    private boolean sinodivination$hasBellows;

    @Inject(method = "serverTick", at = @At("HEAD"))
    private static void injectBeforeServerTick(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo info) {
        IAbstractFurnaceBlockEntitySpeedUp be = (IAbstractFurnaceBlockEntitySpeedUp) blockEntity;
        if (state.hasProperty(AbstractFurnaceBlock.FACING)) {
            Direction direction = state.getValue(AbstractFurnaceBlock.FACING);
            if (level.getBlockState(pos.relative(direction.getClockWise())).is(SDBlocks.BELLOWS.get())
                    || level.getBlockState(pos.relative(direction.getCounterClockWise())).is(SDBlocks.BELLOWS.get())) {
                be.sinodivination$setHasBellows(true);
                be.sinodivination$setCookingProgress0(blockEntity.cookingProgress);
            }
        }
    }

    @Inject(method = "serverTick", at = @At("RETURN"))
    private static void injectAfterServerTick(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo info) {
        IAbstractFurnaceBlockEntitySpeedUp be = (IAbstractFurnaceBlockEntitySpeedUp) blockEntity;
        if (be.sinodivination$hasBellows() && be.sinodivination$isLit()
                // 仍有燃烧时间
                && blockEntity.litTime > 1
                // 配方正常推进
                && blockEntity.cookingProgress > be.sinodivination$getCookingProgress0()
                // 配方未完成且没有进行到最后 1 tick ( 不需要处理燃烧完成状态 )
                && blockEntity.cookingProgress < blockEntity.cookingTotalTime - 1) {
            blockEntity.litTime--;
            blockEntity.cookingProgress++;
            AbstractFurnaceBlockEntity.setChanged(level, pos, state);
        }
    }

    @Override
    public int sinodivination$getCookingProgress0() {
        return sinodivination$cookingProgress0;
    }

    @Override
    public void sinodivination$setCookingProgress0(int progress) {
        sinodivination$cookingProgress0 = progress;
    }

    @Override
    public boolean sinodivination$hasBellows() {
        return sinodivination$hasBellows;
    }

    @Override
    public void sinodivination$setHasBellows(boolean hasBellows) {
        sinodivination$hasBellows = hasBellows;
    }

    @Override
    public boolean sinodivination$isLit() {
        return isLit();
    }
}
