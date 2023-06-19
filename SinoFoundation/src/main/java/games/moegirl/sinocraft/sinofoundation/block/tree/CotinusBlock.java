package games.moegirl.sinocraft.sinofoundation.block.tree;

import games.moegirl.sinocraft.sinofoundation.block.entity.tree.ICotinusEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.GameEventListener;

import javax.annotation.Nullable;

public interface CotinusBlock extends EntityBlock {

    default void placedBy(Level level, BlockPos pos, @Nullable LivingEntity placer) {
        if (placer != null) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ICotinusEntity ce) {
                ce.owner().setOwner(placer);
            }
        }
    }

    @Override
    default <T2 extends BlockEntity> GameEventListener getListener(ServerLevel level, T2 blockEntity) {
        return (GameEventListener) blockEntity;
    }

    /**
     * 判断实体是否可被允许
     *
     * @param pos    方块位置
     * @param entity 实体
     * @return 是否被允许
     */
    default boolean isAllowed(BlockPos pos, @Nullable Entity entity) {
        return isAllowed(pos, entity, true);
    }

    /**
     * 判断实体是否可被允许
     *
     * @param pos       方块位置
     * @param entity    实体
     * @param allowNull 无实体时是否允许
     * @return 是否被允许
     */
    default boolean isAllowed(BlockPos pos, @Nullable Entity entity, boolean allowNull) {
        if (entity == null) {
            return allowNull;
        }

        return entity.level().getBlockEntity(pos) instanceof ICotinusEntity ce && ce.owner().isAllowed(entity);
    }
}
