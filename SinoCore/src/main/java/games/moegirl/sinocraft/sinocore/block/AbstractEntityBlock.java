package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.utility.IGenericClass;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * 带有 {@link  BlockEntity} 的方块
 * <p>可根据泛型获取 BlockEntity 类型</p>
 * <p>实现 getTicker 与 getListener 方法，只需 BlockEntity 实现相关接口即可生效</p>
 * <p>渲染模式默认 {@link RenderShape#MODEL}</p>
 *
 * @see BlockEntity
 * @see BlockEntityTicker
 * @see GameEventListener
 */
public abstract class AbstractEntityBlock<T extends BlockEntity> extends BaseEntityBlock implements IGenericClass {

    protected final Lazy<BlockEntityType<T>> entityType;
    private final Class<?> typeClass;

    public AbstractEntityBlock(Properties properties, Supplier<BlockEntityType<T>> entityType) {
        super(properties);
        this.entityType = Lazy.of(entityType);
        this.typeClass = getGenericClass(0);
    }

    public AbstractEntityBlock(Supplier<BlockEntityType<T>> entityType) {
        this(Properties.of(Material.METAL), entityType);
    }

    public AbstractEntityBlock(Material material, float strength, Supplier<BlockEntityType<T>> entityType) {
        this(Properties.of(material).strength(strength), entityType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return entityType.get().create(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T2 extends BlockEntity> BlockEntityTicker<T2> getTicker(Level pLevel, BlockState pState, BlockEntityType<T2> pBlockEntityType) {
        return BlockEntityTicker.class.isAssignableFrom(typeClass) ? ((pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof BlockEntityTicker ticker) {
                ticker.tick(pLevel1, pPos, pState1, pBlockEntity);
            }
        }) : null;
    }

    @Override
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel level, T blockEntity) {
        return GameEventListener.class.isAssignableFrom(typeClass)
                ? (GameEventListener) blockEntity
                : this instanceof GameEventListener l ? l : null;
    }
}
