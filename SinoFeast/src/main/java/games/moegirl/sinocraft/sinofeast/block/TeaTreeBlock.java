package games.moegirl.sinocraft.sinofeast.block;

import games.moegirl.sinocraft.sinofeast.item.SFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TeaTreeBlock extends BushBlock implements BonemealableBlock {
    public static final BooleanProperty WILD = BooleanProperty.create("wild");
    public static final BooleanProperty SEEDED = BooleanProperty.create("seeded");
    public static final IntegerProperty PERIODS = IntegerProperty.create("periods", 0, 60);
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 15);     // qyl27: according to document.

    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);

    public TeaTreeBlock() {
        super(Properties.copy(Blocks.GRASS)
                .offsetType(OffsetType.NONE)
                .forceSolidOn()
                .randomTicks());

        registerDefaultState(defaultBlockState()
                .setValue(WILD, false)      // Todo: qyl27: world generation, true here.
                .setValue(SEEDED, false)
                .setValue(PERIODS, 0)
                .setValue(STAGE, 4));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WILD);
        builder.add(SEEDED);
        builder.add(PERIODS);
        builder.add(STAGE);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND);    // Todo: qyl27: may change?
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        // qyl27: Entity walk slow inside block.
        if (getStage(state) != Stage.SEEDLING
                && entity instanceof LivingEntity
                && entity.getType() != EntityType.FOX
                && entity.getType() != EntityType.BEE) {
            entity.makeStuckInBlock(state, new Vec3(0.8, 1.0, 0.8));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        var periods = state.getValue(PERIODS);
        if (periods < 60) {
            periods += 1;
        }

        if (player.getItemInHand(hand).isEmpty()) {
            if (getStageValue(state) == 6) {
                if (!level.isClientSide()) {
                    var count = level.getRandom().nextInt(1, 5);
                    var entity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(SFItems.TEA_BUDS.get(), count));
                    level.addFreshEntity(entity);
                }

                level.setBlock(pos, state
                        .setValue(PERIODS, periods)
                        .setValue(STAGE, 4), UPDATE_ALL);
                return InteractionResult.SUCCESS;
            } else if (getStageValue(state) == 7) {
                if (!level.isClientSide()) {
                    var count = level.getRandom().nextInt(1, 5);
                    var entity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(SFItems.TEA_FRESH_LEAVES.get(), count));
                    level.addFreshEntity(entity);
                }

                level.setBlock(pos, state
                        .setValue(PERIODS, periods)
                        .setValue(STAGE, 4), UPDATE_ALL);
                return InteractionResult.SUCCESS;
            } else if (getStageValue(state) == 8) {
                if (!level.isClientSide()) {
                    var count = level.getRandom().nextInt(1, 3);
                    var entity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(SFItems.TEA_FRESH_LEAVES.get(), count));
                    level.addFreshEntity(entity);
                }

                if (!level.isClientSide()) {
                    var count = level.getRandom().nextInt(1, 4);
                    var entity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(SFItems.CRUSHED_TEA_FRESH_LEAVES.get(), count));
                    level.addFreshEntity(entity);
                }

                level.setBlock(pos, state
                        .setValue(PERIODS, periods)
                        .setValue(STAGE, 4), UPDATE_ALL);
                return InteractionResult.SUCCESS;
            } else if (getStageValue(state) == 12) {
                if (!level.isClientSide()) {
                    var count = level.getRandom().nextInt(1, 5);
                    var entity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(SFItems.TEA_SEED.get(), count));
                    level.addFreshEntity(entity);
                }

                level.setBlock(pos, state
                        .setValue(PERIODS, periods)
                        .setValue(STAGE, 4)
                        .setValue(SEEDED, true), UPDATE_ALL);

                var probability = calcAging(state);
                var rand = level.getRandom().nextInt(0, 100);
                if (rand <= probability) {
                    level.setBlock(pos, state.setValue(STAGE, 13), UPDATE_ALL);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        grow(state, level, pos, random);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return getStage(state) == Stage.FLOURISHING;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return getStage(state) == Stage.FLOURISHING;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.setBlock(pos, state.setValue(STAGE, 9).setValue(SEEDED, true), UPDATE_ALL);
    }

    public Stage getStage(BlockState state) {
        return Stage.fromInt(state.getValue(STAGE));
    }

    public int getStageValue(BlockState state) {
        return state.getValue(STAGE);
    }

    public boolean isSeeded(BlockState state) {
        return state.getValue(SEEDED);
    }

    public boolean isWild(BlockState state) {
        return state.getValue(WILD);
    }

    public int getPeriod(BlockState state) {
        return state.getValue(PERIODS);
    }

    public void grow(BlockState state, Level level, BlockPos pos, RandomSource random) {
        var stageValue = getStageValue(state);
        var periods = getPeriod(state);

        if (periods < 60) {
            periods += 1;
        }

        if (stageValue == 8) {
            var probability = calcAging(state);
            var rand = random.nextInt(0, 100);
            if (rand <= probability) {
                level.setBlock(pos, state.setValue(STAGE, 13), UPDATE_ALL);
                return;
            }

            level.setBlock(pos, state.setValue(STAGE, 4).setValue(PERIODS, periods), UPDATE_ALL);
            return;
        }

        if (stageValue == 15) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);
            return;
        }

        if (stageValue != 12) {
            level.setBlock(pos, state.setValue(STAGE, stageValue + 1), UPDATE_ALL);
        }
    }

    public int calcAging(BlockState state) {
        if (isWild(state)) {
            if (isSeeded(state)) {
                return 25 + getPeriod(state) * 5;
            } else {
                return -1;
            }
        } else {
            if (isSeeded(state)) {
                return 15 + getPeriod(state) * 3;
            } else {
                if (getPeriod(state) < 16) {
                    return -1;
                } else {
                    return 10 + (getPeriod(state) - 15) * 2;
                }
            }
        }
    }

    public enum Stage {
        SEEDLING(true, false, 4),       // 幼苗期  0 - 3
        GROWING(true, false, 2),        // 生长期  4 - 5
        MATURE(true, true, 1),          // 成熟期  6 - 6
        FLOURISHING(false, true, 2),    // 茂盛期  7 - 8
        FLOWERING(true, false, 2),      // 开花期  9 - 10
        SEED(false, true, 2),           // 结籽期 11 - 12
        AGING(false, false, 3),         // 衰老期 13 - 15
        ;

        private final boolean isGrowing;
        private final boolean harvestable;
        private final int stages;

        Stage(boolean isGrowing, boolean harvestable, int stages) {
            this.isGrowing = isGrowing;
            this.harvestable = harvestable;
            this.stages = stages;
        }

        public boolean isGrowing() {
            return isGrowing;
        }

        public boolean isHarvestable() {
            return harvestable;
        }

        public int getStages() {
            return stages;
        }

        public static Stage fromInt(int stage) {
            return switch (stage) {
                case 0, 1, 2, 3 -> SEEDLING;
                case 4, 5 -> GROWING;
                case 6 -> MATURE;
                case 7, 8 -> FLOURISHING;
                case 9, 10 -> FLOWERING;
                case 11, 12 -> SEED;
                case 13, 14, 15 -> AGING;
                default -> throw new RuntimeException("Argument out of bounds: " + stage);
            };
        }
    }
}
