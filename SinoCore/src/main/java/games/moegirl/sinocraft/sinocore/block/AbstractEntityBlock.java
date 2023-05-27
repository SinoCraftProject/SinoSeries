package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.utility.IGenericClass;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * 带有 {@link  BlockEntity} 的方块
 * <p>
 * <li>可根据泛型获取 BlockEntity 类型</li>
 * <li>实现 getTicker 方法，只需 BlockEntity 实现 BlockEntityTicker 接口即可生效</li>
 * <li>实现 getListener 方法，只需 BlockEntity 实现 GameEventListener 接口即可生效</li>
 * <li>当 BlockEntity 实现 MenuProvider 接口时，右键默认打开 gui</li>
 * <li>渲染模式默认 {@link RenderShape#MODEL}</li>
 * </p>
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
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (MenuProvider.class.isAssignableFrom(typeClass)) {
            if (player instanceof ServerPlayer sp) {
                level.getBlockEntity(pos, entityType.get())
                        .ifPresent(entity -> NetworkHooks.openScreen(sp, (MenuProvider) entity, pos));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T2 extends BlockEntity> BlockEntityTicker<T2> getTicker(Level pLevel, BlockState pState, BlockEntityType<T2> pBlockEntityType) {
        return BlockEntityTicker.class.isAssignableFrom(typeClass) ? ((pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof BlockEntityTicker ticker) {
                ticker.tick(pLevel1, pPos, pState1, pBlockEntity);
            }
        }) : null;
    }

    @Override
    public <T2 extends BlockEntity> GameEventListener getListener(ServerLevel level, T2 blockEntity) {
        return GameEventListener.class.isAssignableFrom(typeClass)
                ? (GameEventListener) blockEntity
                : this instanceof GameEventListener l ? l : null;
    }

    /**
     * 获取对应 BlockEntity，为了安全问题设为 protected
     *
     * @param level 所在世界
     * @param pos   方块位置
     * @return BlockEntity
     */
    protected T getBlockEntity(BlockGetter level, BlockPos pos) {
        return level.getBlockEntity(pos, entityType.get()).orElseThrow();
    }
}
