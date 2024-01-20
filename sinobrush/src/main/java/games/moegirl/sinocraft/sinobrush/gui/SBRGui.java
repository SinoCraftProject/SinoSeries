package games.moegirl.sinocraft.sinobrush.gui;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class SBRGui {
    public static IRegistry<MenuType<?>> MENU_TYPES = RegistryManager.obtain(SinoBrush.MODID, Registries.MENU);

//    public static final Supplier<MenuType<?>> BRUSH = MENU_TYPES.register("brush", () -> new MenuType())

    public static void register() {
        MENU_TYPES.register();
    }
}
