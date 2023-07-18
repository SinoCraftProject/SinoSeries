package games.moegirl.sinocraft.sinofeast.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 15); // qyl27: according to document.
    public static final IntegerProperty AGING_PROBABILITY = IntegerProperty.create("aging_probability", 0, 100);
    public static final BooleanProperty AGING = BooleanProperty.create("aging");

    public TeaTreeBlock() {
        super(Properties.copy(Blocks.GRASS)
                .offsetType(OffsetType.NONE)
                .forceSolidOn()
                .randomTicks());

        registerDefaultState(defaultBlockState().setValue(STAGE, 4)
                .setValue(AGING_PROBABILITY, 0)
                .setValue(AGING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(STAGE);
        builder.add(AGING_PROBABILITY);
        builder.add(AGING);
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
            entity.makeStuckInBlock(state, new Vec3(0.8F, 1.0D, 0.8F));
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return super.use(state, level, pos, player, hand, hit);
    }
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        grow(state, level, pos);
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
        level.setBlock(pos, setStageValue(state, 9), UPDATE_ALL);
    }

    public Stage getStage(BlockState state) {
        return Stage.fromInt(state.getValue(STAGE));
    }

    public BlockState setStageValue(BlockState state, int stage) {
        return state.setValue(STAGE, stage);
    }

    public int getStageValue(BlockState state) {
        return state.getValue(STAGE);
    }

    public void grow(BlockState state, Level level, BlockPos pos) {
        var stageValue = getStageValue(state);
        if (stageValue == 8) {
            level.setBlock(pos, setStageValue(state, 4), UPDATE_ALL);
            return;
        }

        if (stageValue == 12) {
            level.setBlock(pos, setStageValue(state, 4), UPDATE_ALL);
            return;
        }

        level.setBlock(pos, setStageValue(state, stageValue + 1), UPDATE_ALL);
    }

    public enum Stage {
        SEEDLING(true, false, 4),       // 幼苗期
        GROWING(true, false, 2),        // 生长期
        MATURE(true, true, 1),          // 成熟期
        FLOURISHING(false, true, 2),    // 茂盛期
        FLOWERING(true, false, 2),      // 开花期
        SEED(false, true, 2),           // 结籽期
        AGING(false, false, 3),         // 衰老期
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
