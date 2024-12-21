package games.moegirl.sinocraft.sinobrush.client;

import games.moegirl.sinocraft.sinobrush.gui.SBRScreen;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.client.SinoClientRegister;

public class SinoBrushClient {
    public static void initClient() {
        SBRScreen.register();
    }

    public static void setupClient() {
        SinoClientRegister.registerItemColor(new NormalItemColor(), SBRItems.XUAN_PAPER.get(), SBRItems.INK_BOTTLE.get());
        SinoClientRegister.registerItemColor(new FilledXuanPaperItemColor(), SBRItems.FILLED_XUAN_PAPER.get());
    }
}
