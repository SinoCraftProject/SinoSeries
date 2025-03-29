package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class SBRItems {
    public static IRegistry<Item> ITEMS = RegistryManager.create(SinoBrush.MODID, Registries.ITEM);
    public static ITabRegistry TABS = RegistryManager.createTab(SinoBrush.MODID);

    public static ResourceKey<CreativeModeTab> SINO_BRUSH_TAB = TABS.register("sinobrush");

    public static IRegRef<Item> FAN = ITEMS.register("fan", FanItem::new);
    public static Supplier<Item> FOLDED_FAN = ITEMS.register("folded_fan", FoldedFanItem::new);
    public static Supplier<XuanPaperItem> XUAN_PAPER = ITEMS.register("xuan_paper", XuanPaperItem::new);
    public static Supplier<Item> FILLED_XUAN_PAPER = ITEMS.register("filled_xuan_paper", FilledXuanPaperItem::new);
    public static Supplier<InkItem> INK_BOTTLE = ITEMS.register("ink_bottle", InkItem::new);
    public static Supplier<Item> BRUSH = ITEMS.register("brush", BrushItem::new);

    public static void register() {
        ITEMS.register();
        TABS.register();
    }
}
