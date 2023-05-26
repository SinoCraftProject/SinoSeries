package games.moegirl.sinocraft.sinocore.item.tab;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author luqin2007
 */
public class TabsRegistryOps implements TabsRegistry {

    final List<Consumer<TabsRegistry>> ops = new ArrayList<>();
    final ResourceLocation name;

    volatile boolean locked = false;
    @Nullable
    volatile CreativeModeTab tab = null;

    TabsRegistryOps(ResourceLocation name) {
        this.name = name;
    }

    @Override
    public CreativeModeTab tab() {
        return Objects.requireNonNull(tab, "Tab is null because it is not created now. You should get it after CreativeModeTabEvent.Register event.");
    }

    @Override
    public ResourceLocation name() {
        return name;
    }

    @Override
    public TabsRegistry icon(RegistryObject<? extends ItemLike> item) {
        synchronized (this) {
            checkLocked();
            ops.add(t -> t.icon(item));
        }
        return this;
    }

    @Override
    public TabsRegistry icon(Supplier<ItemStack> item) {
        synchronized (this) {
            checkLocked();
            ops.add(t -> t.icon(item));
        }
        return this;
    }

    @Override
    public TabsRegistry custom(Consumer<CreativeModeTab.Builder> consumer) {
        synchronized (this) {
            checkLocked();
            ops.add(t -> t.custom(consumer));
        }
        return this;
    }

    @Override
    public TabsRegistry addStack(Supplier<ItemStack> stack) {
        synchronized (this) {
            checkLocked();
            ops.add(t -> t.addStack(stack));
        }
        return this;
    }

    @Override
    public TabsRegistry add(Supplier<? extends ItemLike> item) {
        synchronized (this) {
            checkLocked();
            ops.add(t -> t.add(item));
        }
        return this;
    }

    @SafeVarargs
    @Override
    public final TabsRegistry add(Supplier<? extends ItemLike>... items) {
        synchronized (this) {
            checkLocked();
            ops.add(t -> t.add(items));
        }
        return this;
    }

    @Override
    public TabsRegistry add(DeferredRegister<? extends ItemLike> dr) {
        synchronized (this) {
            checkLocked();
            ops.add(t -> t.add(dr));
        }
        return this;
    }

    void accept(TabsRegistryImpl impl) {
        synchronized (this) {
            for (Consumer<TabsRegistry> op : ops) {
                op.accept(impl);
            }
            locked = true;
        }
    }

    private void checkLocked() {
        if (locked) {
            throw new RuntimeException("You can't edit tab " + name() + ", because this tab had been added to minecraft.");
        }
    }
}
