package games.moegirl.sinocraft.sinocore.item.tab;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author luqin2007
 */
public interface TabsRegistry {

    Map<ResourceLocation, TabsRegistry> TAB_MAP = new HashMap<>();

    static TabsRegistry get(ResourceLocation name) {
        return TAB_MAP.computeIfAbsent(name, TabsRegistryOps::new);
    }

    static TabsRegistry register(ResourceLocation name, IEventBus bus) {
        return new TabsRegistryImpl(name, bus);
    }

    static TabsRegistry register(ResourceLocation name) {
        return register(name, FMLJavaModLoadingContext.get().getModEventBus());
    }

    ResourceLocation name();

    default CreativeModeTab tab() {
        throw new RuntimeException("Tab is null because it is not created now. You should get it after CreativeModeTabEvent.Register event.");
    }

    default TabsRegistry icon(RegistryObject<? extends ItemLike> item) {
        throw new RuntimeException("You can't edit tab " + name() + ", because this tab had been added to minecraft.");
    }

    default TabsRegistry icon(Supplier<ItemStack> item) {
        throw new RuntimeException("You can't edit tab " + name() + ", because this tab had been added to minecraft.");
    }

    default TabsRegistry custom(Consumer<CreativeModeTab.Builder> consumer) {
        throw new RuntimeException("You can't edit tab " + name() + ", because this tab had been added to minecraft.");
    }

    default TabsRegistry addStack(Supplier<ItemStack> stack) {
        throw new RuntimeException("You can't edit tab " + name() + ", because this tab had been added to minecraft.");
    }

    default TabsRegistry add(Supplier<? extends ItemLike> item) {
        throw new RuntimeException("You can't edit tab " + name() + ", because this tab had been added to minecraft.");
    }

    default TabsRegistry add(Supplier<? extends ItemLike>... items) {
        throw new RuntimeException("You can't edit tab " + name() + ", because this tab had been added to minecraft.");
    }

    default TabsRegistry add(DeferredRegister<? extends ItemLike> dr) {
        throw new RuntimeException("You can't edit tab " + name() + ", because this tab had been added to minecraft.");
    }
}
