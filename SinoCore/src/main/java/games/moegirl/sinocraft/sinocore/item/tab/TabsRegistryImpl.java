package games.moegirl.sinocraft.sinocore.item.tab;

import games.moegirl.sinocraft.sinocore.utility.Functions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TabsRegistryImpl implements TabsRegistry, CreativeModeTab.DisplayItemsGenerator {

    private final List<DeferredRegister<? extends ItemLike>> deferredRegisters = new ArrayList<>();
    private final List<Supplier<ItemStack>> itemStacks = new ArrayList<>();
    private final List<Supplier<? extends ItemLike>> items = new ArrayList<>();

    private final ResourceLocation name;
    private CreativeModeTab tab;

    private Function<CreativeModeTab.Builder, CreativeModeTab.Builder> builder;

    TabsRegistryImpl(ResourceLocation name, IEventBus bus) {
        this.name = name;
        this.builder = b -> b
                .title(Component.translatable(makeTranslateKey(name)))
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
        if (tab == null) {
            TabsRegistry.super.tab();
        }
        return tab;
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

    @Override
    public TabsRegistry add(Supplier<? extends ItemLike>... items) {
        Collections.addAll(this.items, items);
        return this;
    }

    @Override
    public TabsRegistry add(DeferredRegister<? extends ItemLike> dr) {
        deferredRegisters.add(dr);
        return this;
    }

    @SubscribeEvent
    public void onCreativeModeTabRegister(CreativeModeTabEvent.Register event) {
        TabsRegistry tmp = TabsRegistry.get(name);
        if (tmp instanceof TabsRegistryOps ops && !ops.isEmpty())
            custom(b -> ops.ops.forEach(c -> c.accept(this)));
        tab = event.registerCreativeModeTab(name, builder::apply);
        TabsRegistry.TAB_MAP.put(name, new TabsRegistryEnd(name, tab));
    }

    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        itemStacks.stream()
                .map(Supplier::get)
                .forEach(output::accept);
        items.stream()
                .map(Supplier::get)
                .forEach(output::accept);
        deferredRegisters.stream()
                .map(DeferredRegister::getEntries)
                .flatMap(Collection::stream)
                .map(RegistryObject::get)
                .forEach(output::accept);
    }
}
