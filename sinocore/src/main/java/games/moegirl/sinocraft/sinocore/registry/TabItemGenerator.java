package games.moegirl.sinocraft.sinocore.registry;

import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 用于向特定 CreativeModeTab 中添加物品
 *
 * @author luqin2007
 */
public class TabItemGenerator implements CreativeModeTab.DisplayItemsGenerator {

    private final List<Supplier<ItemStack>> items = new ArrayList<>();
    private Supplier<ItemStack> display = Suppliers.memoize(() -> items.isEmpty() ? new ItemStack(Items.BARRIER) : items.get(0).get().copy());

    /**
     * 用于向 MC 注册该添加器
     */
    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        items.forEach(sup -> output.accept(sup.get()));
    }

    /**
     * 向对应 CreativeModeTab 添加物品
     *
     * @param item 添加的物品
     * @return 该 TabItemGenerator
     */
    public synchronized TabItemGenerator addItem(ItemLike item) {
        items.add(() -> new ItemStack(item));
        return this;
    }

    /**
     * 向对应 CreativeModeTab 添加物品并设置为页面 Icon
     * <p>
     * 注意：仅用于通过 {@link ITabRegistry#register(String)} 或 {@link ITabRegistry#registerForRef(String)}
     * 创建的 CreativeModeTab
     *
     * @param item 添加的物品
     * @return 该 TabItemGenerator
     */
    public synchronized TabItemGenerator addItemAsIcon(ItemLike item) {
        setIcon(() -> new ItemStack(item));
        return addItem(item);
    }

    /**
     * 向对应 CreativeModeTab 添加物品
     *
     * @param item 添加的物品
     * @return 该 TabItemGenerator
     */
    public synchronized TabItemGenerator addItem(Supplier<? extends ItemLike> item) {
        items.add(() -> new ItemStack(item.get()));
        return this;
    }

    /**
     * 向对应 CreativeModeTab 添加物品并设置为页面 Icon
     * <p>
     * 注意：仅用于通过 {@link ITabRegistry#register(String)} 或 {@link ITabRegistry#registerForRef(String)}
     * 创建的 CreativeModeTab
     *
     * @param item 添加的物品
     * @return 该 TabItemGenerator
     */
    public synchronized TabItemGenerator addItemAsIcon(Supplier<? extends ItemLike> item) {
        setIcon(() -> new ItemStack(item.get()));
        return addItem(item);
    }

    /**
     * 向对应 CreativeModeTab 添加物品
     *
     * @param item 添加的物品栈
     * @return 该 TabItemGenerator
     */
    public synchronized TabItemGenerator addStack(ItemStack item) {
        items.add(item::copy);
        return this;
    }

    /**
     * 向对应 CreativeModeTab 添加物品并设置为页面 Icon
     * <p>
     * 注意：仅用于通过 {@link ITabRegistry#register(String)} 或 {@link ITabRegistry#registerForRef(String)}
     * 创建的 CreativeModeTab
     *
     * @param item 添加的物品栈
     * @return 该 TabItemGenerator
     */
    public synchronized TabItemGenerator addStackAsIcon(ItemStack item) {
        setIcon(item::copy);
        return addStack(item);
    }

    /**
     * 向对应 CreativeModeTab 添加物品
     *
     * @param item 添加的物品栈
     * @return 该 TabItemGenerator
     */
    public synchronized TabItemGenerator addStack(Supplier<ItemStack> item) {
        items.add(item);
        return this;
    }

    /**
     * 向对应 CreativeModeTab 添加物品并设置为页面 Icon
     * <p>
     * 注意：仅用于通过 {@link ITabRegistry#register(String)} 或 {@link ITabRegistry#registerForRef(String)}
     * 创建的 CreativeModeTab
     *
     * @param item 添加的物品栈
     * @return 该 TabItemGenerator
     */
    public synchronized TabItemGenerator addStackAsIcon(Supplier<ItemStack> item) {
        setIcon(item);
        return addStack(item);
    }

    /**
     * 将任意物品作为物品栏 icon
     * <p>
     * 注意：仅用于通过 {@link ITabRegistry#register(String)} 或 {@link ITabRegistry#registerForRef(String)}
     * 创建的 CreativeModeTab
     *
     * @param icon 作为图标的 ItemStack
     * @return TabItemGenerator
     */
    public synchronized TabItemGenerator setIcon(Supplier<ItemStack> icon) {
        display = Suppliers.memoize(icon::get);
        return this;
    }

    /**
     * 获取用于显示的 ItemStack
     * <p>
     * 注意：仅用于通过 {@link ITabRegistry#register(String)} 或 {@link ITabRegistry#registerForRef(String)}
     * 创建的 CreativeModeTab
     *
     * @return ItemStack
     */
    public ItemStack displayItem() {
        return display.get();
    }

    /**
     * 用于收集其他 CreativeTab 的物品，不支持 setIcon
     *
     * @author luqin2007
     */
    public static TabItemGenerator vanilla(ResourceKey<CreativeModeTab> key) {
        return new TabItemGenerator() {
            @Override
            public synchronized TabItemGenerator setIcon(Supplier<ItemStack> icon) {
                throw new IllegalCallerException("CreativeTab " + key + " not support operation: set display item");
            }
        };
    }
}
