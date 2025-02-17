package games.moegirl.sinocraft.sinodeco.item;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class SDItems {
    public static final IRegistry<Item> ITEMS = RegistryManager.create(SinoDeco.MODID, Registries.ITEM);
    public static final ITabRegistry TABS = RegistryManager.createTab(SinoDeco.MODID);

    public static final ResourceKey<CreativeModeTab> SINO_DECO_TAB = TABS.register("sinodeco");

    // Todo: texture
    public static final IRegRef<Item> PEACH_BOAT = ITEMS.register("peach_boat", () -> new Item(new Item.Properties()));
    public static final IRegRef<Item> PEACH_CHEST_BOAT = ITEMS.register("peach_chest_boat", () -> new Item(new Item.Properties()));

    public static void register() {
        ITEMS.register();
        TABS.register();
    }
}
