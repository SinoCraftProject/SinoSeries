package games.moegirl.sinocraft.sinocore.item.tab;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author luqin2007
 */
@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public interface TabsRegistry {

    Map<ResourceLocation, TabsRegistry> TAB_MAP = new HashMap<>();

    static TabsRegistry get(ResourceLocation name) {
        synchronized (TabsRegistry.class) {
            return TAB_MAP.computeIfAbsent(name, TabsRegistryOps::new);
        }
    }

    static TabsRegistry register(ResourceLocation name, IEventBus bus) {
        return new TabsRegistryImpl(name, bus);
    }

    ResourceLocation name();

    CreativeModeTab tab();

    TabsRegistry icon(RegistryObject<? extends ItemLike> item);

    TabsRegistry icon(Supplier<ItemStack> item);

    TabsRegistry custom(Consumer<CreativeModeTab.Builder> consumer);

    TabsRegistry addStack(Supplier<ItemStack> stack);

    TabsRegistry add(Supplier<? extends ItemLike> item);

    TabsRegistry add(Supplier<? extends ItemLike>... items);

    TabsRegistry add(DeferredRegister<? extends ItemLike> dr);
}
