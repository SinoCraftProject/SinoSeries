package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.util.RecipeProcessor;
import games.moegirl.sinocraft.sinodivination.util.container.ComposeItemHandler;
import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.util.container.OutputOnlyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TripodEntity extends BlockEntity implements BlockEntityTicker<TripodEntity>, GameEventListener {

    private final TripodProcessor processor = new TripodProcessor();
    final InputOnlyContainer in = new InputOnlyContainer(1)
            .addChecker((slot, stack) -> processor.getStatus() == RecipeProcessor.Status.IDLE)
            .bindEntityChangeWithUpdate(this);
    final OutputOnlyContainer out = new OutputOnlyContainer(1).bindEntityChangeWithUpdate(this);
    private final ComposeItemHandler compose = new ComposeItemHandler().append(in).append(out);

    // 每一部分结构是否正确
    private final boolean[][] isStructurePartCurrently = new boolean[10][5];
    // 正确的结构数量。 -1 表示未检查状态，50 表示结构完整
    private int currentlyStructurePart = -1;

    public TripodEntity(BlockEntityType<?> entityType, BlockPos worldPosition, BlockState blockState) {
        super(entityType, worldPosition, blockState);
    }

    public ItemStack takeItem() {
        if (out.getStackInSlot(0).isEmpty()) {
            return in.setStackInSlot2(0, ItemStack.EMPTY);
        } else {
            return out.setStackInSlot2(0, ItemStack.EMPTY);
        }
    }

    public ItemStack getItem() {
        return in.getStackInSlot(0).copy();
    }

    public ItemStack putItem(ItemStack stack) {
        return in.insertItem2(0, stack, false);
    }

    int getProgress() {
        return processor.getProgress();
    }

    RecipeProcessor.Status getStatus() {
        return processor.getStatus();
    }

    public void detachStructure() {
        if (currentlyStructurePart != -1) {
            if (level == null) return;

            if (isStructurePartCurrently[getStructurePartIndex(0, 4)][0]) {
                level.getBlockEntity(worldPosition.offset(0, 0, 4), SDBlockEntities.ALTAR.get()).ifPresent(altar -> altar.setDirection(Direction.UP));
            }
            if (isStructurePartCurrently[getStructurePartIndex(0, -4)][0]) {
                level.getBlockEntity(worldPosition.offset(0, 0, -4), SDBlockEntities.ALTAR.get()).ifPresent(altar -> altar.setDirection(Direction.UP));
            }
            if (isStructurePartCurrently[getStructurePartIndex(4, 0)][0]) {
                level.getBlockEntity(worldPosition.offset(4, 0, 0), SDBlockEntities.ALTAR.get()).ifPresent(altar -> altar.setDirection(Direction.UP));
            }
            if (isStructurePartCurrently[getStructurePartIndex(-4, 0)][0]) {
                level.getBlockEntity(worldPosition.offset(-4, 0, 0), SDBlockEntities.ALTAR.get()).ifPresent(altar -> altar.setDirection(Direction.UP));
            }
            currentlyStructurePart = -1;
        }
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState blockState, TripodEntity tripodEntity) {

        // 检查结构完整性
        if (currentlyStructurePart == -1) {
            currentlyStructurePart = 0;
            // 鼎及顶部空气
            int p = getStructurePartIndex(0, 0);
            isStructurePartCurrently[0][p] = level.getBlockState(pos).is(SDBlocks.TRIPOD.get());
            if (isStructurePartCurrently[0][p]) currentlyStructurePart++;
            for (int i = 1; i < 10; i++) {
                isStructurePartCurrently[i][p] = level.getBlockState(pos.offset(0, i, 0)).isAir();
                if (isStructurePartCurrently[i][p]) currentlyStructurePart++;
            }

            // 祭坛及顶部空气
            int[][] offsets = new int[][]{{0, 4}, {0, -4}, {4, 0}, {-4, 0}};
            for (int[] o : offsets) {
                BlockPos pp = pos.offset(o[0], 0, o[1]);
                p = getStructurePartIndex(o[0], o[1]);
                isStructurePartCurrently[0][p] = level.getBlockState(pp).is(SDBlocks.ALTAR.get());
                if (isStructurePartCurrently[0][p]) currentlyStructurePart++;
                for (int i = 1; i < 10; i++) {
                    isStructurePartCurrently[i][p] = level.getBlockState(pp.offset(0, i, 0)).isAir();
                    if (isStructurePartCurrently[i][p]) currentlyStructurePart++;
                }
            }
        }

        if (level.isClientSide) {
            return;
        }

        if (currentlyStructurePart == 50) {
            processor.tick(tripodEntity);
        } else {
            processor.reset();
        }

        if (processor.isDataChanged()) {
            setChanged();
        }

        if (processor.isStatusChanged()) {
            level.setBlockAndUpdate(worldPosition, getBlockState());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        in.save(tag, "InSlot");
        out.save(tag, "OutSlot");
        processor.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        in.load(tag, "InSlot");
        out.load(tag, "OutSlot");
        processor.load(tag);
        currentlyStructurePart = -1;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyOptional.of(() -> compose).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public PositionSource getListenerSource() {
        return new BlockPositionSource(worldPosition);
    }

    @Override
    public int getListenerRadius() {
        return 10;
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, GameEvent gameEvent, GameEvent.Context context, Vec3 pos) {
        if (currentlyStructurePart != -1) {
            // 检查结构完整性
            if (GameEvent.BLOCK_PLACE.equals(gameEvent) || GameEvent.BLOCK_DESTROY.equals(gameEvent) || GameEvent.BLOCK_CHANGE.equals(gameEvent)) {
                int dx = (int) pos.x - worldPosition.getX();
                int dy = (int) pos.x - worldPosition.getY();
                int dz = (int) pos.x - worldPosition.getZ();
                if ((dy >= 0 && dy <= 9) && ((dx == 0 && dz == 0) // 鼎位置
                        // 祭坛位置
                        || (dx == 0 && (dz == 4 || dz == -4))
                        || (dz == 0 && (dx == 4 || dx == -4)))) {
                    int p = getStructurePartIndex(dx, dy);
                    boolean current;
                    if (dz == 0) {
                        // 四座祭坛 / 鼎
                        if (p == 0) {
                            current = level.getBlockState(new BlockPos((int) pos.x, (int) pos.y, (int) pos.z)).is(SDBlocks.TRIPOD.get());
                        } else {
                            current = level.getBlockState(new BlockPos((int) pos.x, (int) pos.y, (int) pos.z)).is(SDBlocks.ALTAR.get());
                        }
                    } else {
                        // 空气
                        current = level.getBlockState(new BlockPos((int) pos.x, (int) pos.y, (int) pos.z)).isAir();
                    }

                    if (current != isStructurePartCurrently[dz][p]) {
                        isStructurePartCurrently[dz][p] = current;
                        currentlyStructurePart += (current ? 1 : -1);
                    }
                }
            }
        }
        return false;
    }

    private int getStructurePartIndex(int dx, int dz) {
        return dx == 4 ? 1 : dx == -4 ? 2 : dz == 4 ? 3 : dz == -4 ? 4 : 0;
    }
}
