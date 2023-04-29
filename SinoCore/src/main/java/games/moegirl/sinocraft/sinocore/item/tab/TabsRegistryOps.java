package games.moegirl.sinocraft.sinocore.item.tab;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author luqin2007
 */
public class TabsRegistryOps implements TabsRegistry {

    final List<Consumer<TabsRegistry>> ops = new ArrayList<>();
    final ResourceLocation name;

    TabsRegistryOps(ResourceLocation name) {
        this.name = name;
    }

    @Override
    public ResourceLocation name() {
        return name;
    }

    @Override
    public TabsRegistry icon(RegistryObject<? extends ItemLike> item) {
        ops.add(t -> t.icon(item));
        return this;
    }

    @Override
    public TabsRegistry icon(Supplier<ItemStack> item) {
        ops.add(t -> t.icon(item));
        return this;
    }

    @Override
    public TabsRegistry custom(Consumer<CreativeModeTab.Builder> consumer) {
        ops.add(t -> t.custom(consumer));
        return this;
    }

    @Override
    public TabsRegistry addStack(Supplier<ItemStack> stack) {
        ops.add(t -> t.addStack(stack));
        return this;
    }

    @Override
    public TabsRegistry add(Supplier<? extends ItemLike> item) {
        ops.add(t -> t.add(item));
        return this;
    }

    @Override
    public TabsRegistry add(Supplier<? extends ItemLike>... items) {
        ops.add(t -> t.add(items));
        return this;
    }

    @Override
    public TabsRegistry add(DeferredRegister<? extends ItemLike> dr) {
        ops.add(t -> t.add(dr));
        return this;
    }

    public boolean isEmpty() {
        return ops.isEmpty();
    }
}
