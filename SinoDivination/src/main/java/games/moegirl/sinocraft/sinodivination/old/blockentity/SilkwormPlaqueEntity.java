package games.moegirl.sinocraft.sinodivination.old.blockentity;

import games.moegirl.sinocraft.sinodivination.old.data.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import games.moegirl.sinocraft.sinodivination.old.menu.SilkwormPlaqueMenu;
import games.moegirl.sinocraft.sinodivination.old.util.ChangableInt;
import games.moegirl.sinocraft.sinodivination.old.util.NbtUtils;
import games.moegirl.sinocraft.sinodivination.old.util.container.ComposeItemHandler;
import games.moegirl.sinocraft.sinodivination.old.util.container.InputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.old.util.container.OutputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.old.util.container.SlotChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SilkwormPlaqueEntity extends BlockEntity implements MenuProvider, BlockEntityTicker<SilkwormPlaqueEntity> {

    public static final int SILKWORM_COUNT = 8;

    private final InputOnlyContainer in = new InputOnlyContainer(SILKWORM_COUNT)
            .bindEntityChangeWithUpdate(this)
            .setChecker(SlotChecker.of(SDItems.SILKWORM_BABY));
    private final OutputOnlyContainer out = new OutputOnlyContainer(1).bindEntityChangeWithUpdate(this);
    private final InputOnlyContainer feed = new InputOnlyContainer(1)
            .bindEntityChangeWithUpdate(this)
            .setChecker(SlotChecker.of(ItemTags.LEAVES));
    private final SilkwormHolder[] silkworms = new SilkwormHolder[SILKWORM_COUNT];
    private final ChangableInt nutrition = new ChangableInt(0);

    private final IItemHandler handler = new ComposeItemHandler().append(in).append(out).append(feed);

    public SilkwormPlaqueEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
        for (int i = 0; i < silkworms.length; i++) {
            silkworms[i] = new SilkwormHolder(i);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(SDLangKeys.SILKWORM_PLAGUE_TITLE);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new SilkwormPlaqueMenu(pContainerId, pInventory, this);
    }

    public InvWrapper in() {
        return in;
    }

    public InvWrapper out() {
        return out;
    }

    public InvWrapper feed() {
        return feed;
    }

    public SilkwormHolder silkworm(int index) {
        return silkworms[index];
    }

    public int nutrition() {
        return nutrition.get();
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, SilkwormPlaqueEntity pBlockEntity) {
        if (!pLevel.isClientSide) {
            boolean sync = false;
            if (nutrition.get() <= 90) {
                int count = feed.extractItem2(0, ((100 - nutrition.get()) / 10), false).getCount();
                nutrition.add(count * 10);
                sync = nutrition.changed();
            }
            for (SilkwormHolder silkworm : silkworms) {
                silkworm.ready();
                int c = silkworm.cached.get();
                if (c > 0) {
                    silkworm.cached.set(out.insertItem2(0, new ItemStack(SDItems.SILK.get(), c), false).getCount());
                }

                if (silkworm.cooldown > 0) {
                    silkworm.cooldown--;
                    continue;
                }

                if (silkworm.isBlocking()) {
                    continue;
                }

                int i = silkworm.index;
                if (!in.getStackInSlot(i).is(SDItems.SILKWORM_BABY.get())) {
                    silkworm.progress.set(0);
                } else if (nutrition.get() > 0) {
                    silkworm.progress.add(1);
                    nutrition.reduce(1);
                    if (silkworm.progress.get() == 10) {
                        silkworm.cached.add(out.insertItem2(0, new ItemStack(SDItems.SILK.get()), false).getCount());
                        silkworm.progress.set(0);
                    }
                    silkworm.cooldown = 10;
                }
                sync |= silkworm.needSync();
            }
            setChanged();
            if (sync) {
                System.out.println("Send to " + worldPosition);
                pLevel.setBlockAndUpdate(worldPosition, getBlockState());
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        System.out.println("Receive at " + worldPosition + "(" + this + ")");
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        in.load(pTag, "in");
        out.load(pTag, "out");
        feed.load(pTag, "feed");
        NbtUtils.loadList(pTag, "silkworm", silkworms);
        nutrition.set(pTag.getInt("nutrition"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        in.save(pTag, "in");
        out.save(pTag, "out");
        feed.save(pTag, "feed");
        NbtUtils.saveList(pTag, "silkworm", silkworms);
        pTag.putInt("nutrition", nutrition.get());
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (ForgeCapabilities.ITEM_HANDLER == cap) {
            return LazyOptional.of(() -> handler).cast();
        }
        return super.getCapability(cap, side);
    }

    public static class SilkwormHolder implements INBTSerializable<CompoundTag> {
        final int index;
        ChangableInt progress = new ChangableInt(0);
        int cooldown = 0;
        ChangableInt cached = new ChangableInt(0);

        SilkwormHolder(int index) {
            this.index = index;
        }

        public float progress() {
            return cached.get() == 0 ? ((float) progress.get() / 10) : 1;
        }

        public boolean isBlocking() {
            return cached.get() > 0;
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putByte("slot", (byte) index);
            nbt.putByte("progress", (byte) progress.get());
            nbt.putByte("cooldown", (byte) cooldown);
            nbt.putInt("cached", cached.get());
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            progress.set(nbt.getByte("progress"));
            cooldown = nbt.getByte("cooldown");
            cached.set(nbt.getInt("cached"));
        }

        public void ready() {
            progress.ready();
            cached.ready();
        }

        public boolean needSync() {
            return progress.changed() || cached.changed();
        }
    }
}
