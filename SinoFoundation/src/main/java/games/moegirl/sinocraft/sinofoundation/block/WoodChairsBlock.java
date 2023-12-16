package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.utility.ArmorStandHelper;
import games.moegirl.sinocraft.sinocore.utility.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WoodChairsBlock extends HorizontalDirectionalBlock {
    public static final VoxelShape[] SHAPES = new VoxelShape[4];

    static {
        var legHighLeft = Block.box(1, 0, 1, 3, 16, 3);
        var legHighRight = Block.box(1, 0, 13, 3, 16, 15);
        var legLowLeft = Block.box(12, 0, 2, 14, 7, 4);
        var legLowRight = Block.box(12, 0, 12, 14, 7, 14);
        var face = Block.box(3, 7, 2, 15, 9, 14);
        var backUpper = Block.box(1, 14, 3, 2, 15, 13);
        var backLower = Block.box(1, 12, 3, 2, 13, 13);

        var shape = Shapes.or(legHighLeft, legHighRight, legLowLeft, legLowRight, face, backUpper, backLower);
        shape = VoxelShapeHelper.rotateHorizontal(shape, Direction.EAST, Direction.SOUTH);
        for (var i = 0; i < 4; i++) {
            SHAPES[i] = shape;
            shape = VoxelShapeHelper.rotateClockwise(shape);
        }
    }

    public WoodChairsBlock() {
        super(Properties.copy(Blocks.ACACIA_PLANKS)
                .forceSolidOn()
                .dynamicShape()
                .strength(2.5f)
                .noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(FACING).get2DDataValue()];
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        var armorStand = ArmorStandHelper.createDummyArmorStand(level,
                new Vec3(pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5));

        var result = player.startRiding(armorStand);
        if (!result) {
            armorStand.discard();
            return InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }
}
