package games.moegirl.sinocraft.sinocalligraphy.block;

import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.item.tab.ITabItem;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class PaperDryingRackBlock extends HorizontalDirectionalBlock {
    /**
     * qyl27: State is an int value shows drying rack's state.
     * 0: No paper.
     * 1-3: Paper drying.
     * 4: Paper dried.
     */
    public static IntegerProperty PROCESS = IntegerProperty.create("process", 0, 4);

    protected PaperDryingRackBlock() {
        super(BlockBehaviour.Properties.of(Material.WOOD)
                .destroyTime(2.5f)
                .randomTicks()
                .sound(SoundType.WOOD)
                .noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(PROCESS);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(PROCESS, 0);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        var process = state.getValue(PROCESS);

        if (!newState.is(state.getBlock())) {
            if (!level.isClientSide() && process == 4) {
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(),
                        new ItemStack(SCAItems.EMPTY_XUAN_PAPER.get())));
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        var handStack = player.getItemInHand(hand);

        int process = state.getValue(PROCESS);

        // Todo: Better crafting.
        if (process == 0 && handStack.getItem() == SCAItems.WOOD_PULP_BUCKET.get()) {
            setState(level, pos, state, 1);
            if (level.isClientSide()) {
                level.playSound(player, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else {
                level.scheduleTick(pos, this, 20 * 15);

                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
            }
            return InteractionResult.SUCCESS;
        }

        if (process == 4) {
            setState(level, pos, state, 0);
            if (level.isClientSide()) {
                level.playSound(player, pos, SoundEvents.VILLAGER_WORK_CARTOGRAPHER, SoundSource.PLAYERS, 1.0f, 1.0f);
            } else {
                setState(level, pos, state, 0);
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(),
                        new ItemStack(SCAItems.EMPTY_XUAN_PAPER.get())));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private void setState(Level level, BlockPos pos, BlockState state, int stateValue) {
        level.setBlock(pos, state.setValue(PROCESS, stateValue), Block.UPDATE_NEIGHBORS);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int process = state.getValue(PROCESS);

        if (process > 0 && process < 4) {
            setState(level, pos, state, process + 1);
        }

        if (process == 3) {
            if (level.isClientSide()) {
                for (Player e : level.getPlayers(player ->
                        player.position().distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <= 5)) {
                    level.playSound(e, pos, SoundEvents.VILLAGER_WORK_CARTOGRAPHER,
                            SoundSource.BLOCKS, 1.0f, 1.0f);
                }
            }
        }
    }
}
