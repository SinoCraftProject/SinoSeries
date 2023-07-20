package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.block.AbstractEntityBlock;
import games.moegirl.sinocraft.sinodivination.data.SDTags;
import games.moegirl.sinocraft.sinodivination.blockentity.KettlePotEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;

@SuppressWarnings("deprecation")
public class KettlePot extends AbstractEntityBlock<KettlePotEntity> {

    public static final VoxelShape SHAPE = box(1, 0, 1, 14, 7, 14);
    public static final VoxelShape SHAPE_INTERACTION = box(1, 0, 1, 14, 9, 14);

    public KettlePot() {
        super(BlockBehaviour.Properties.copy(Blocks.ANVIL), SDBlockEntities.KETTLE_POT);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        KettlePotEntity entity = getBlockEntity(level, pos);
//        if (player.isShiftKeyDown()) {
//            // Shift 右键 取出物品
//            InputOnlyContainer input = (InputOnlyContainer) entity.getInput();
//            for (int i = 0; i < input.getSlots(); i++) {
//                ItemStack stack = input.getStackInSlot(i);
//                if (!stack.isEmpty()) {
//                    ItemHandlerHelper.giveItemToPlayer(player, stack);
//                    input.setStackInSlot2(i, ItemStack.EMPTY);
//                    break;
//                }
//            }
//        } else
        if (entity.isReady()) {
            // 可进行 运行
            entity.run();
        } else if (FluidUtil.getFluidHandler(player.getItemInHand(hand)).isPresent()) {
            // 导入流体
            ItemStack stack = player.getItemInHand(hand);
            FluidTank tank = entity.getTank();
            FluidActionResult result = FluidUtil.tryFillContainer(stack, tank, Integer.MAX_VALUE, player, true);
            if (result.isSuccess()) {
                stack.shrink(1);
                player.addItem(result.getResult());
            }
        } else {
            // 取出结果
            entity.takeResult(player.getItemInHand(hand)).ifPresent(result -> ItemHandlerHelper.giveItemToPlayer(player, result));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (!level.getBlockState(pos.below()).is(SDTags.HEAT_SOURCE)) {
            return;
        }

        // 黑烟
        double x = pos.getX() + 0.5;
        double y = pos.getY() + random.nextDouble() * 6.0 / 16.0;
        double z = pos.getZ() + 0.5;
        level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
        level.getBlockEntity(pos, SDBlockEntities.KETTLE_POT.get()).ifPresent(pot -> {
            double dx = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
            double dz = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
            switch (pot.getStatus()) {
                case READY -> level.addParticle(ParticleTypes.SMOKE, x + dx, y, z + dz, 0, 0, 0);
                case RUNNING -> {
                    double dx2 = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    double dz2 = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    level.addParticle(ParticleTypes.FLAME, x + dx2, y, z + dz2, 0, 0.1, 0);
                    level.addParticle(ParticleTypes.SMOKE, x + dx2, y, z + dz2, 0, 0, 0);
                }
                case BLOCKING -> level.addParticle(ParticleTypes.FLAME, x + dx, y, z + dz, 0, 0.1, 0);
                default -> level.addParticle(ParticleTypes.SMALL_FLAME, x + dx, y, z + dz, 0, 0.1, 0);
            }
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return SHAPE_INTERACTION;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        KettlePotEntity pot = getBlockEntity(level, pos);
        // 直接投入物品
        if (entity instanceof ItemEntity ie) {
            ItemStack item = ie.getItem();
            ItemStack remain = ItemHandlerHelper.insertItem(pot.getInput(), item, false);
            if (remain.isEmpty()) {
                ie.discard();
                pot.setChanged();
            } else if (remain.getCount() < item.getCount()) {
                ie.setItem(remain);
                pot.setChanged();
            }
        }
    }
}
