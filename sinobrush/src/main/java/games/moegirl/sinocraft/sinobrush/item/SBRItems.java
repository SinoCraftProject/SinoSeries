package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class SBRItems {
    public static IRegistry<Item> ITEMS = RegistryManager.obtain(SinoBrush.MODID, Registries.ITEM);
    public static ITabRegistry TABS = RegistryManager.obtainTab(SinoBrush.MODID);

    public static Supplier<Item> FAN = ITEMS.register("fan", FanItem::new);
    public static Supplier<Item> FOLDED_FAN = ITEMS.register("folded_fan", FoldedFanItem::new);
    public static Supplier<Item> XUAN_PAPER = ITEMS.register("xuan_paper", XuanPaperItem::new);
    public static Supplier<Item> FILLED_XUAN_PAPER = ITEMS.register("filled_xuan_paper", FilledXuanPaperItem::new);
    public static Supplier<Item> INK = ITEMS.register("ink", InkItem::new);
    public static Supplier<Item> BRUSH = ITEMS.register("brush", InkItem::new);

    public static ResourceKey<CreativeModeTab> SINO_BRUSH_TAB = TABS.register("sinobrush");

    public static void register() {
        TABS.tabItems(SINO_BRUSH_TAB)
                .addItemAsIcon(BRUSH)
                .addItem(FAN)
                .addItem(FOLDED_FAN)
                .addItem(XUAN_PAPER)
                .addItem(INK);

        ITEMS.register();
        TABS.register();
    }
}
