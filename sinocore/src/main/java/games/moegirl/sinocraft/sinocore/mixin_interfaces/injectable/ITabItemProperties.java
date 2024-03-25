package games.moegirl.sinocraft.sinocore.mixin_interfaces.injectable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface ITabItemProperties {

    /**
     * 将物品放入某个 CreativeModeTab
     * @param tab CreativeModeTab
     */
    default Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab) {
        return null;
    }

    /**
     * 将物品放入某个 CreativeModeTab
     * @param tab CreativeModeTab
     * @param sup ItemStack 构造方法
     */
    default Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab, Function<ItemLike, ItemStack> sup) {
        return null;
    }

    /**
     * 将物品作为某个 CreativeModeTab 的图标使用
     * @param tab CreativeModeTab
     */
    default Item.Properties sino$tabIcon(ResourceKey<CreativeModeTab> tab) {
        return null;
    }

    /**
     * 将物品作为某个 CreativeModeTab 的图标使用
     * @param tab CreativeModeTab
     * @param sup ItemStack 构造方法
     */
    default Item.Properties sino$tabIcon(ResourceKey<CreativeModeTab> tab, Function<ItemLike, ItemStack> sup) {
        return null;
    }

    /**
     * 获取所有应加入此物品的 CreativeModeTab
     */
    default List<Pair<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>>> sino$getTabs() {
        return List.of();
    }

    /**
     * 获取所有应把此物品作为图标的 CreativeModeTab
     */
    default Map<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>> sino$getTabIcon() {
        return Map.of();
    }
}
