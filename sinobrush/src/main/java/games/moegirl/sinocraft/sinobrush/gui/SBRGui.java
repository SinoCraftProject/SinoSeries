package games.moegirl.sinocraft.sinobrush.gui;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.gui.menu.XuanPaperMenu;
import games.moegirl.sinocraft.sinocore.registry.IMenuRegister;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.world.inventory.MenuType;

public class SBRGui {
    public static IMenuRegister MENU_TYPES = RegistryManager.obtainMenu(SinoBrush.MODID);

    public static final IRegRef<MenuType<?>, ?> XUAN_PAPER = MENU_TYPES.register("xuan_paper", XuanPaperMenu::new);

    public static void register() {
        MENU_TYPES.register();
    }
}
