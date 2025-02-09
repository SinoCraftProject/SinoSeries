package games.moegirl.sinocraft.sinodeco.block;

import com.mojang.serialization.MapCodec;
import games.moegirl.sinocraft.sinodeco.entity.DummyChairEntity;
import games.moegirl.sinocraft.sinodeco.entity.SDEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WoodenArmchairBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<WoodenArmchairBlock> CODEC = simpleCodec(WoodenArmchairBlock::new);

    public WoodenArmchairBlock() {
        this(Properties.ofFullCopy(Blocks.OAK_PLANKS)
                .forceSolidOn()
                .dynamicShape()
                .strength(2.5f)
                .noCollission() // Todo: shape
                .noOcclusion());
    }

    public WoodenArmchairBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                                        Player player, BlockHitResult hitResult) {
        var entities = level.getEntitiesOfClass(DummyChairEntity.class, new AABB(pos));
        if (!entities.isEmpty()) {
            return InteractionResult.FAIL;
        }

        var chair = new DummyChairEntity(SDEntities.DUMMY_CHAIR.get(), level);
        chair.setPos(pos.getCenter());
        chair.setYRot(state.getValue(FACING).toYRot());
        level.addFreshEntity(chair);
        player.startRiding(chair);

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
