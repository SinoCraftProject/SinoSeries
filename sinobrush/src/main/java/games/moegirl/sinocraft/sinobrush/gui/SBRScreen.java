package games.moegirl.sinocraft.sinobrush.gui;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.gui.screen.BrushScreen;
import games.moegirl.sinocraft.sinocore.registry.IScreenRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;

public class SBRScreen {

    public static IScreenRegistry SCREENS = RegistryManager.obtainScreen(SinoBrush.MODID);

    public static void register() {
        SCREENS.register(SBRMenu.BRUSH_PAPER, BrushScreen::new);
        SCREENS.register();
    }
}
