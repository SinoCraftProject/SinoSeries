package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.utility.shape.VoxelShapeHelper;
import games.moegirl.sinocraft.sinofoundation.SFDConstants;
import games.moegirl.sinocraft.sinofoundation.block.entity.StoveBlockEntity;
import games.moegirl.sinocraft.sinofoundation.block.entity.ticker.StoveBlockEntityTicker;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class StoveBlock extends HorizontalDirectionalBlock implements EntityBlock {
    static {
        var topHoleFull = Block.box(2, 16 - 1, 2, 16 - 2, 16, 16 - 2);
        var topHoleOccludePart1 = Block.box(2, 16 - 1, 2, 4, 16, 4);
        var topHoleOccludePart2 = Block.box(2, 16 - 1, 4, 3, 16, 5);
        var topHoleOccludePart3 = Block.box(4, 16 - 1, 2, 5, 16, 3);
        var topHolePart = Shapes.or(topHoleOccludePart1, topHoleOccludePart2, topHoleOccludePart3);

        var topHole = Shapes.join(topHoleFull, topHolePart, BooleanOp.ONLY_FIRST);
        for (var i = 0; i < 3; i++) {
            topHolePart = VoxelShapeHelper.rotateClockwise(topHolePart);
            topHole = Shapes.join(topHole, topHolePart, BooleanOp.ONLY_FIRST);
        }

        var frontHoleFull = Block.box(4, 2, 0, 4 + 8, 2 + 8, 3);
        var frontHoleOccludePartLeft1 = Block.box(4, 5 + 4, 0, 4 + 3, 2 + 8, 3);
        var frontHoleOccludePartLeft2 = Block.box(4, 5 + 3, 0, 4 + 2, 2 + 7, 3);
        var frontHoleOccludePartLeft3 = Block.box(4, 5 + 2, 0, 4 + 1, 2 + 6, 3);
        var frontHoleOccludePartRight1 = Block.box(4 + 5, 5 + 4, 0, 4 + 5 + 3, 2 + 8, 3);
        var frontHoleOccludePartRight2 = Block.box(4 + 6, 5 + 3, 0, 4 + 6 + 2, 2 + 7, 3);
        var frontHoleOccludePartRight3 = Block.box(4 + 7, 5 + 2, 0, 4 + 7 + 1, 2 + 6, 3);
        var frontHolePartLeft = Shapes.or(frontHoleOccludePartLeft1, frontHoleOccludePartLeft2, frontHoleOccludePartLeft3);
        var frontHolePartRight = Shapes.or(frontHoleOccludePartRight1, frontHoleOccludePartRight2, frontHoleOccludePartRight3);
        var frontHolePartFull = Shapes.or(frontHolePartLeft, frontHolePartRight);
        var frontHole = VoxelShapeHelper.rotateClockwise(Shapes.join(frontHoleFull, frontHolePartFull, BooleanOp.ONLY_FIRST));

        var fullBlock = Block.box(0, 0, 0, 16, 16, 16);
        var lastShape = Shapes.join(fullBlock, topHole, BooleanOp.ONLY_FIRST);
        var east = Shapes.join(lastShape, frontHole, BooleanOp.ONLY_FIRST);
        SHAPE_EAST = east;
        SHAPE_NORTH = VoxelShapeHelper.rotateClockwise(east, 3);
        SHAPE_WEST = VoxelShapeHelper.rotateClockwise(east, 2);
        SHAPE_SOUTH = VoxelShapeHelper.rotateClockwise(east, 1);
    }

    public static final VoxelShape SHAPE_NORTH;
    public static final VoxelShape SHAPE_SOUTH;
    public static final VoxelShape SHAPE_WEST;
    public static final VoxelShape SHAPE_EAST;

    public static final BooleanProperty BURNING = BooleanProperty.create("burning");

    protected StoveBlock() {
        super(Properties.copy(Blocks.BRICKS)
                .requiresCorrectToolForDrops()
                .dynamicShape()
                .strength(3.5f));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var facing = state.getValue(FACING);
        return switch (facing) {
            case NORTH -> SHAPE_NORTH;
            case WEST -> SHAPE_WEST;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            default -> Shapes.block();
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(BURNING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context)
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(BURNING, false);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);

        if (state.getValue(BURNING)) {
            if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.hurt(entity.damageSources().hotFloor(), 0.5f);
            }
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.getValue(BURNING)) {
            return 14;
        }

        return super.getLightEmission(state, level, pos);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        var itemStack = player.getItemInHand(hand);
        var blockEntity = level.getBlockEntity(pos);

        if (blockEntity instanceof StoveBlockEntity stoveBlockEntity) {
            if (!itemStack.isEmpty()) {
                if (FurnaceBlockEntity.isFuel(itemStack)) {
                    var result = stoveBlockEntity.addFuel(itemStack);
                    if (result) {
                        player.setItemInHand(hand, ItemStack.EMPTY);
                        return InteractionResult.SUCCESS;
                    } else {
                        player.displayClientMessage(Component.translatable(SFDConstants.TRANSLATE_STOVE_FULL_OF_ASHES).withStyle(ChatFormatting.GREEN), true);
                        return super.use(state, level, pos, player, hand, hit);
                    }
                } else {
                    player.displayClientMessage(Component.translatable(SFDConstants.TRANSLATE_STOVE_NOT_A_FUEL).withStyle(ChatFormatting.GREEN), true);
                    return super.use(state, level, pos, player, hand, hit);
                }
            } else {
                if (!player.isDescending()) {
                    var ashes = stoveBlockEntity.removeAshes();
                    if (ashes.isEmpty()) {
                        player.displayClientMessage(Component.translatable(SFDConstants.TRANSLATE_STOVE_EMPTY_NOW).withStyle(ChatFormatting.GREEN), true);
                    } else {
                        var result = player.getInventory().add(ashes);
                        if (result) {
                            player.drop(ashes, false);
                        }
                    }
                } else {
                    stoveBlockEntity.removeAllAshes();
                    player.displayClientMessage(Component.translatable(SFDConstants.TRANSLATE_STOVE_EMPTY_NOW).withStyle(ChatFormatting.GREEN), true);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StoveBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return StoveBlockEntityTicker::tick;
    }
}
