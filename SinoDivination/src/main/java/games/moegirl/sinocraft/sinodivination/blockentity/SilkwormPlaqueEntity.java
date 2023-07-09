package games.moegirl.sinocraft.sinodivination.blockentity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.moegirl.sinocraft.sinodivination.data.gen.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.menu.SilkwormPlaqueMenu;
import games.moegirl.sinocraft.sinodivination.util.ChangableInt;
import games.moegirl.sinocraft.sinodivination.util.container.ComposeItemHandler;
import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.util.container.OutputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.util.container.SlotChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
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
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SilkwormPlaqueEntity extends BlockEntity implements MenuProvider, BlockEntityTicker<SilkwormPlaqueEntity> {

    public static final int SILKWORM_COUNT = 8;

    private final InputOnlyContainer in = new InputOnlyContainer(SILKWORM_COUNT)
            .bindEntityChangeWithUpdate(this)
            .setChecker(SlotChecker.of(SDItems.SILKWORM_BABY));
    private final OutputOnlyContainer out = new OutputOnlyContainer(1).bindEntityChangeWithUpdate(this);
    private final InputOnlyContainer feed = new InputOnlyContainer(1)
            .bindEntityChangeWithUpdate(this)
            .setChecker(SlotChecker.of(ItemTags.LEAVES));
    private final ChangableInt nutrition = new ChangableInt(0);
    private List<Silkworm> silkworms = new ArrayList<>(SILKWORM_COUNT);

    private final IItemHandler handler = new ComposeItemHandler().append(in).append(out).append(feed);

    public SilkwormPlaqueEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
        for (int i = 0; i < SILKWORM_COUNT; i++) {
            silkworms.add(new Silkworm(i));
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(SDLangKeys.SILKWORM_PLAGUE_TITLE);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SilkwormPlaqueMenu(id, inventory, this);
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

    public Silkworm silkworm(int index) {
        return silkworms.get(index);
    }

    public int nutrition() {
        return nutrition.get();
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, SilkwormPlaqueEntity blockEntity) {
        if (!level.isClientSide) {
            boolean sync = false;
            // 营养 <= 90：填充营养
            if (nutrition.get() <= 90) {
                int count = feed.extractItem2(0, ((100 - nutrition.get()) / 10), false).getCount();
                nutrition.add(count * 10);
                sync = nutrition.changed();
            }
            for (Silkworm silkworm : silkworms) {
                silkworm.ready();

                // 输出 线
                int c = silkworm.cached.get();
                if (c > 0) {
                    silkworm.cached.set(out.insertItem2(0, new ItemStack(SDItems.SILK.get(), c), false).getCount());
                }

                // 冷却
                if (silkworm.cooldown > 0) {
                    silkworm.cooldown--;
                    continue;
                }

                if (silkworm.isBlocking()) {
                    continue;
                }

                // 处理
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
                level.setBlockAndUpdate(worldPosition, getBlockState());
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
    public void load(CompoundTag tag) {
        super.load(tag);
        in.load(tag, "in");
        out.load(tag, "out");
        feed.load(tag, "feed");
        nutrition.set(tag.getInt("nutrition"));
        tag.put("silkworm", Silkworm.CODEC.listOf().encodeStart(NbtOps.INSTANCE, silkworms).result().orElseThrow());
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        in.save(tag, "in");
        out.save(tag, "out");
        feed.save(tag, "feed");
        tag.putInt("nutrition", nutrition.get());
        silkworms = Silkworm.CODEC.listOf().decode(NbtOps.INSTANCE, tag.get("silkworm")).result().orElseThrow().getFirst();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (ForgeCapabilities.ITEM_HANDLER == cap) {
            return LazyOptional.of(() -> handler).cast();
        }
        return super.getCapability(cap, side);
    }

    public static class Silkworm {

        public static final Codec<Silkworm> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BYTE.fieldOf("slot").forGetter(p -> (byte) p.index),
                Codec.BYTE.fieldOf("progress").forGetter(p -> (byte) p.progress.get()),
                Codec.BYTE.fieldOf("cooldown").forGetter(p -> (byte) p.cooldown),
                Codec.INT.fieldOf("cached").forGetter(p ->  p.cached.get())
        ).apply(instance, Silkworm::new));

        final int index;
        ChangableInt progress = new ChangableInt(0);
        int cooldown = 0;
        ChangableInt cached = new ChangableInt(0);

        Silkworm(int index) {
            this.index = index;
        }

        public Silkworm(byte index, byte progress, byte cooldown, int cached) {
            this.index = index;
            this.progress.set(progress);
            this.cooldown = cooldown;
            this.cached.set(cached);
        }

        public float progress() {
            return cached.get() == 0 ? ((float) progress.get() / 10) : 1;
        }

        public boolean isBlocking() {
            return cached.get() > 0;
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
