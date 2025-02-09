package games.moegirl.sinocraft.sinodeco.block;

import com.mojang.serialization.MapCodec;
import games.moegirl.sinocraft.sinocore.helper.shape.BlockShapeHelper;
import games.moegirl.sinocraft.sinocore.helper.shape.VoxelShapeHelper;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WoodenArmchairBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<WoodenArmchairBlock> CODEC = simpleCodec(WoodenArmchairBlock::new);


    // <editor-fold desc="Shapes.">

    public static final VoxelShape[] SHAPES = new VoxelShape[4];

    static {
        var face = box(3, 6, 4, 13, 8, 14);
        var leg1 = box(3, 0, 4, 5, 6, 6);
        var leg2 = box(3, 0, 12, 5, 6, 14);
        var leg3 = box(11, 0, 12, 13, 6, 14);
        var leg4 = box(11, 0, 4, 13, 6, 6);
        var legConnection1 = box(4, 1, 4.5, 12, 2, 5.5);
        var legConnection2 = box(3.5, 2, 6, 4.5, 3, 12);
        var legConnection3 = box(4, 1, 12.5, 12, 2, 13.5);
        var legConnection4 = box(11.5, 2, 6, 12.5, 3, 12);
        var bottom = VoxelShapeHelper.combine(face, leg1, leg2, leg3, leg4,
                legConnection1, legConnection2, legConnection3, legConnection4);

        var back1 = box(3, 8, 13, 13, 20, 14);
        var back2 = box(3, 17, 14, 13, 21.5, 15);
        var back = VoxelShapeHelper.combine(back1, back2);

        var hand1part1 = box(3, 8, 4, 3.5, 10, 13);
        var hand1part2 = box(2.5, 9, 4, 3, 11, 13);
        var hand1part3 = box(2, 10, 4, 2.5, 12, 13);
        var hand1 = VoxelShapeHelper.combine(hand1part1, hand1part2, hand1part3);

        var hand2part1 = box(12.5, 8, 4, 13, 10, 13);
        var hand2part2 = box(13, 9, 4, 13.5, 11, 13);
        var hand2part3 = box(13.5, 10, 4, 14, 12, 13);
        var hand2 = VoxelShapeHelper.combine(hand2part1, hand2part2, hand2part3);

        var north = VoxelShapeHelper.combine(bottom, back, hand1, hand2);

        SHAPES[2] = north;
        SHAPES[3] = BlockShapeHelper.rotateY(north);
        SHAPES[0] = BlockShapeHelper.rotateY(SHAPES[3]);
        SHAPES[1] = BlockShapeHelper.rotateY(SHAPES[0]);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(FACING).get2DDataValue()];
    }

    // </editor-fold>

    public WoodenArmchairBlock() {
        this(Properties.ofFullCopy(Blocks.OAK_PLANKS)
                .forceSolidOn()
                .dynamicShape()
                .strength(2.5f)
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
