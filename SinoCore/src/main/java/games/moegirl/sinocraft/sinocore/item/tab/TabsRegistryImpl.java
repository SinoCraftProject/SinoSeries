package games.moegirl.sinocraft.sinocore.item.tab;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.utility.Functions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TabsRegistryImpl implements TabsRegistry, CreativeModeTab.DisplayItemsGenerator {
    private static final Lazy<Map<ResourceLocation, List<ItemStack>>> TAB_ITEM_MAP = Lazy.of(() -> {
        Map<ResourceLocation, List<ItemStack>> map = new HashMap<>();
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof ITabItem<?> tabs) {
                tabs.acceptTabs((tab, stack) -> map.computeIfAbsent(tab, t -> new ArrayList<>()).add(stack));
            }
        }
        return map;
    });

    private final List<DeferredRegister<? extends ItemLike>> deferredRegisters = new ArrayList<>();
    private final List<Supplier<ItemStack>> itemStacks = new ArrayList<>();
    private final List<Supplier<? extends ItemLike>> items = new ArrayList<>();

    private final ResourceLocation name;
    @Nullable
    private CreativeModeTab tab;

    private Function<CreativeModeTab.Builder, CreativeModeTab.Builder> builder;
    private final ItemStack[] firstStack = new ItemStack[] { ItemStack.EMPTY };

    TabsRegistryImpl(ResourceLocation name, IEventBus bus) {
        this.name = name;
        this.builder = b -> b
                .title(Component.translatable(makeTranslateKey(name)))
                .icon(() -> firstStack[0])
                .displayItems(this);

        bus.register(this);
    }

    private String makeTranslateKey(ResourceLocation loc) {
        return "tab." + loc.getNamespace() + "." + loc.getPath();
    }

    @Override
    public ResourceLocation name() {
        return name;
    }

    @Override
    public CreativeModeTab tab() {
        return Objects.requireNonNull(tab, "Tab is null because it is not created now. You should get it after CreativeModeTabEvent.Register event.");
    }

    @Override
    public TabsRegistry icon(RegistryObject<? extends ItemLike> item) {
        return icon(() -> new ItemStack(item.get()));
    }

    @Override
    public TabsRegistry icon(Supplier<ItemStack> item) {
        Function<CreativeModeTab.Builder, CreativeModeTab.Builder> bb = b -> b.icon(item);
        builder = Functions.compose(builder, bb);
        return this;
    }

    @Override
    public TabsRegistry custom(Consumer<CreativeModeTab.Builder> consumer) {
        builder = Functions.compose(builder, consumer);
        return this;
    }

    @Override
    public TabsRegistry addStack(Supplier<ItemStack> stack) {
        itemStacks.add(stack);
        return this;
    }

    @Override
    public TabsRegistry add(Supplier<? extends ItemLike> item) {
        items.add(item);
        return this;
    }

    @SafeVarargs
    @Override
    public final TabsRegistry add(Supplier<? extends ItemLike>... items) {
        Collections.addAll(this.items, items);
        return this;
    }

    @Override
    public TabsRegistry add(DeferredRegister<? extends ItemLike> dr) {
        deferredRegisters.add(dr);
        return this;
    }

    // Fixme: qyl27: tab registry, use deferred register.
    @SubscribeEvent
    public void onCreativeModeTabRegister(RegisterEvent event) {
        TabsRegistry tmp = TabsRegistry.get(name);
        if (tmp instanceof TabsRegistryOps ops) {
            ops.accept(this);
        }
        tab = builder.apply(CreativeModeTab.builder()).build();
        event.register(Registries.CREATIVE_MODE_TAB, name, () -> tab);
        if (tmp instanceof TabsRegistryOps ops) {
            ops.tab = tab;
        }
    }

    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {

        itemStacks.stream()
                .map(Supplier::get)
                .forEach(stack -> accept(stack, output));
        items.stream()
                .map(Supplier::get)
                .forEach(item -> accept(new ItemStack(item), output));
        deferredRegisters.stream()
                .map(DeferredRegister::getEntries)
                .flatMap(Collection::stream)
                .map(RegistryObject::get)
                .filter(i -> !(i instanceof ITabItem))
                .forEach(item -> accept(new ItemStack(item), output));
        TAB_ITEM_MAP.get().getOrDefault(name, Collections.emptyList())
                .forEach(stack -> accept(stack, output));
    }

    private void accept(ItemStack stack, CreativeModeTab.Output output) {
        if (firstStack[0].isEmpty()) {
            firstStack[0] = stack;
        }
        output.accept(stack);
    }
}
