package games.moegirl.sinocraft.sinocore.mixin.injectables;

import games.moegirl.sinocraft.sinocore.injectables.ITabItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Mixin(Item.Properties.class)
public abstract class TabItemPropertiesMixin implements ITabItemProperties {

    private final List<Pair<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>>> sino$allTabs = new ArrayList<>();
    private final Map<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>> sino$tabIcons = new HashMap<>();

    @Override
    public Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab) {
        sino$allTabs.add(Pair.of(tab, ItemStack::new));
        return sino$getThis();
    }

    @Override
    public Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab, Function<ItemLike, ItemStack> sup) {
        sino$allTabs.add(Pair.of(tab, sup));
        return sino$getThis();
    }

    @Override
    public Item.Properties sino$tabIcon(ResourceKey<CreativeModeTab> tab) {
        sino$tabIcons.put(tab, ItemStack::new);
        return sino$tab(tab);
    }

    @Override
    public Item.Properties sino$tabIcon(ResourceKey<CreativeModeTab> tab, Function<ItemLike, ItemStack> sup) {
        sino$tabIcons.put(tab, sup);
        return sino$tab(tab, sup);
    }

    @Override
    public List<Pair<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>>> sino$getAllTabs() {
        return sino$allTabs;
    }

    @Override
    public Map<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>> sino$getTabIcon() {
        return sino$tabIcons;
    }

    private Item.Properties sino$getThis() {
        return (Item.Properties) (Object) this;
    }
}
