package games.moegirl.sinocraft.sinocore.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

/**
 * 陷阱箱
 *
 * @author luqin2007
 */
public class BaseTrappedChestBlock extends BaseChestBlock {

    public BaseTrappedChestBlock(Properties properties, RegistryObject<BlockEntityType<? extends ChestBlockEntity>> blockEntity, ResourceLocation name) {
        super(properties, blockEntity, name);
    }

    public BaseTrappedChestBlock(RegistryObject<BlockEntityType<? extends ChestBlockEntity>> blockEntity, ResourceLocation name) {
        super(Properties.copy(Blocks.TRAPPED_CHEST), blockEntity, name);
    }

    // Copy from net.minecraft.world.level.block.TrappedChestBlock =====================================================

    @Override
    protected Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return Mth.clamp(ChestBlockEntity.getOpenCount(level, pos), 0, 15);
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        if (direction == Direction.UP) {
            return state.getSignal(level, pos, direction);
        }
        return 0;
    }
}
