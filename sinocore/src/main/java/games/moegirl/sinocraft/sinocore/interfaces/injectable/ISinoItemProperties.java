package games.moegirl.sinocraft.sinocore.interfaces.injectable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface ISinoItemProperties {

    /**
     * 将物品放入某个 CreativeModeTab
     * @param tab CreativeModeTab
     */
    default Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab) {
        return sino$tab(tab, ItemStack::new);
    }

    /**
     * 将物品放入某个 CreativeModeTab
     * @param tab CreativeModeTab
     * @param asIcon 同时作为图标使用
     */
    default Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab, boolean asIcon) {
        return sino$tab(tab, ItemStack::new, asIcon);
    }

    /**
     * 将物品放入某个 CreativeModeTab
     * @param tab CreativeModeTab
     * @param sup ItemStack 构造方法
     */
    default Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab, Function<ItemLike, ItemStack> sup) {
        return sino$tab(tab, sup, false);
    }

    /**
     * 将物品放入某个 CreativeModeTab
     * @param tab CreativeModeTab
     * @param sup ItemStack 构造方法
     * @param asIcon 同时作为图标使用
     */
    default Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab, Function<ItemLike, ItemStack> sup, boolean asIcon) {
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
