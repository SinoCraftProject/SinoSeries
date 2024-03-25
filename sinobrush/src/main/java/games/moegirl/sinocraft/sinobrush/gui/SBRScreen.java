package games.moegirl.sinocraft.sinobrush.gui;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.gui.screen.XuanPaperScreen;
import games.moegirl.sinocraft.sinocore.registry.IScreenRegister;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;

public class SBRScreen {

    public static IScreenRegister SCREENS = RegistryManager.obtainScreen(SinoBrush.MODID);

    public static void register() {
        SCREENS.register(SBRGui.XUAN_PAPER, XuanPaperScreen::new);
        SCREENS.register();
    }
}
