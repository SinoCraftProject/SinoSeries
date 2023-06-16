package games.moegirl.sinocraft.sinocore.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.util.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 用于收集 CreativeTab 的物品
 *
 * @author luqin2007
 */
public class TabItemGenerator implements CreativeModeTab.DisplayItemsGenerator {

    private final List<Supplier<ItemStack>> items = new ArrayList<>();
    private Supplier<ItemStack> display = Lazy.of(() -> items.isEmpty() ? new ItemStack(Items.BARRIER) : items.get(0).get().copy());

    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        items.forEach(sup -> output.accept(sup.get()));
    }

    public synchronized TabItemGenerator addItem(ItemLike item) {
        items.add(() -> new ItemStack(item));
        return this;
    }

    public synchronized TabItemGenerator addItemAsIcon(ItemLike item) {
        setIcon(() -> new ItemStack(item));
        return addItem(item);
    }

    public synchronized TabItemGenerator addItem(Supplier<? extends ItemLike> item) {
        items.add(() -> new ItemStack(item.get()));
        return this;
    }

    public synchronized TabItemGenerator addItemAsIcon(Supplier<? extends ItemLike> item) {
        setIcon(() -> new ItemStack(item.get()));
        return addItem(item);
    }

    public synchronized TabItemGenerator addStack(ItemStack item) {
        items.add(() -> item);
        return this;
    }

    public synchronized TabItemGenerator addStackAsIcon(ItemStack item) {
        setIcon(() -> item);
        return addStack(item);
    }

    public synchronized TabItemGenerator addStack(Supplier<ItemStack> item) {
        items.add(item);
        return this;
    }

    public synchronized TabItemGenerator addStackAsIcon(Supplier<ItemStack> item) {
        setIcon(item);
        return addStack(item);
    }

    /**
     * 将任意物品作为物品栏 icon
     *
     * @param icon 作为图标的 ItemStack
     * @return TabItemGenerator
     */
    public synchronized TabItemGenerator setIcon(Supplier<ItemStack> icon) {
        display = Lazy.of(icon);
        return this;
    }

    /**
     * 获取用于显示的 ItemStack
     *
     * @return ItemStack
     */
    public ItemStack displayItem() {
        return display.get();
    }
}
