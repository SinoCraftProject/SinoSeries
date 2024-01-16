package games.moegirl.sinocraft.sinocore.injectables;

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
     * 将物品注册到某个 CreativeModeTab
     * @param tab CreativeModeTab
     */
    Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab);

    /**
     * 将物品注册到某个 CreativeModeTab
     * @param tab CreativeModeTab
     * @param sup ItemStack 构造方法
     */
    Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab, Function<ItemLike, ItemStack> sup);

    /**
     * 将物品注册到某个 CreativeModeTab 并作为图标使用
     * @param tab CreativeModeTab
     */
    Item.Properties sino$tabIcon(ResourceKey<CreativeModeTab> tab);

    /**
     * 将物品注册到某个 CreativeModeTab 并作为图标使用
     * @param tab CreativeModeTab
     * @param sup ItemStack 构造方法
     */
    Item.Properties sino$tabIcon(ResourceKey<CreativeModeTab> tab, Function<ItemLike, ItemStack> sup);

    /**
     * 获取所有被注册到的 CreativeModeTab 用于注册
     */
    List<Pair<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>>> sino$getAllTabs();

    /**
     * 获取注册的 CreativeModeTab 图标
     */
    Map<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>> sino$getTabIcon();
}
