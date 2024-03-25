package games.moegirl.sinocraft.sinobrush;

import games.moegirl.sinocraft.sinobrush.gui.SBRGui;
import games.moegirl.sinocraft.sinobrush.gui.SBRScreen;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;

public class SinoBrush {
    public static final String MOD_NAME = "SinoBrush";
    public static final String MODID = "sinobrush";

    public SinoBrush() {
    }

    public void init() {
        SBRItems.register();
        SBRGui.register();
    }

    public void initClient() {
        SBRScreen.register();
    }
}
