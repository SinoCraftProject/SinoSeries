package games.moegirl.sinocraft.sinodeco.item;

import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class SDItems {
    public static IRegistry<Item> ITEMS = RegistryManager.obtain(SinoDeco.MODID, Registries.ITEM);
    public static ITabRegistry TABS = RegistryManager.obtainTab(SinoDeco.MODID);

    public static ResourceKey<CreativeModeTab> SINO_DECO_TAB = TABS.register("sinodeco");

    public static void register() {
        ITEMS.register();
        TABS.register();
    }
}
