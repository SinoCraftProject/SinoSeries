package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinocore.block.AbstractEntityBlock;
import games.moegirl.sinocraft.sinodivination.old.blockentity.KettlePotEntity;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.old.data.SDTags;
import games.moegirl.sinocraft.sinodivination.old.util.container.InputOnlyContainer;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Optional;

public class KettlePot extends AbstractEntityBlock<KettlePotEntity> {

    public static final VoxelShape SHAPE = box(1, 0, 1, 14, 7, 14);
    public static final VoxelShape SHAPE_INTERACTION = box(1, 0, 1, 14, 9, 14);

    public KettlePot() {
        super(BlockBehaviour.Properties.of(Material.METAL), SDBlockEntities.KETTLE_POT);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        SDBlockEntities.getKettlePot(pLevel, pPos).ifPresent(entity -> {
            if (pPlayer.isShiftKeyDown()) {
                InputOnlyContainer input = (InputOnlyContainer) entity.getInput();
                for (int i = 0; i < input.getSlots(); i++) {
                    ItemStack stack = input.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(pPlayer, stack);
                        input.setStackInSlot2(i, ItemStack.EMPTY);
                        break;
                    }
                }
            } else if (entity.isReady()) {
                entity.run();
            } else if (FluidUtil.getFluidHandler(pPlayer.getItemInHand(pHand)).isPresent()) {
                ItemStack stack = pPlayer.getItemInHand(pHand);
                // fluid
                FluidTank tank = entity.getTank();
                FluidActionResult result = FluidUtil.tryFillContainer(stack, tank, Integer.MAX_VALUE, pPlayer, true);
                if (result.isSuccess()) {
                    stack.shrink(1);
                    pPlayer.addItem(result.getResult());
                } else {
                    FluidActionResult r = FluidUtil.tryEmptyContainer(stack, tank, Integer.MAX_VALUE, pPlayer, true);
                    if (r.isSuccess()) {
                        stack.shrink(1);
                        pPlayer.addItem(r.getResult());
                    } else {
                        entity.takeResult(pPlayer.getItemInHand(pHand))
                                .ifPresent(ri -> ItemHandlerHelper.giveItemToPlayer(pPlayer, ri));
                    }
                }
            } else {
                entity.takeResult(pPlayer.getItemInHand(pHand))
                        .ifPresent(result -> ItemHandlerHelper.giveItemToPlayer(pPlayer, result));
            }
        });
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (!level.getBlockState(pos.below()).is(SDTags.HEAT_SOURCE)) {
            return;
        }

        double x = pos.getX() + 0.5;
        double y = pos.getY() + random.nextDouble() * 6.0 / 16.0;
        double z = pos.getZ() + 0.5;
        level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
        Optional<KettlePotEntity> optional = level.getBlockEntity(pos, SDBlockEntities.KETTLE_POT.get());
        if (optional.isPresent()) {
            KettlePotEntity pot = optional.get();
            switch (pot.getStatus()) {
                case READY -> {
                    double dx = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    double dz = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    level.addParticle(ParticleTypes.SMOKE, x + dx, y, z + dz, 0, 0, 0);
                }
                case RUNNING -> {
                    double dx = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    double dz = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    level.addParticle(ParticleTypes.FLAME, x + dx, y, z + dz, 0, 0.1, 0);
                    dx = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    dz = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    level.addParticle(ParticleTypes.SMOKE, x + dx, y, z + dz, 0, 0, 0);
                }
                case BLOCKING -> {
                    double dx = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    double dz = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    level.addParticle(ParticleTypes.FLAME, x + dx, y, z + dz, 0, 0.1, 0);
                }
                default -> {
                    double dx = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    double dz = random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
                    level.addParticle(ParticleTypes.SMALL_FLAME, x + dx, y, z + dz, 0, 0.1, 0);
                }
            }
        }
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
        SDBlockEntities.getKettlePot(level, pos).ifPresent(pot -> {
            if (entity instanceof ItemEntity ie) {
                ItemStack item = ie.getItem();
                ItemStack remain = ItemHandlerHelper.insertItem(pot.getInput(), item, false);
                if (remain.isEmpty()) {
                    ie.remove(Entity.RemovalReason.KILLED);
                    pot.setChanged();
                } else if (remain.getCount() < item.getCount()) {
                    ie.setItem(remain);
                    pot.setChanged();
                }
            }
        });
    }
}
