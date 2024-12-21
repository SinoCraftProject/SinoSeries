package games.moegirl.sinocraft.sinobrush.client;

import games.moegirl.sinocraft.sinobrush.gui.SBRScreen;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.client.ClientRegister;

public class SinoBrushClient {
    public static void initClient() {
        SBRScreen.register();
    }

    public static void setupClient() {
        ClientRegister.registerItemColor(new NormalItemColor(), SBRItems.XUAN_PAPER.get(), SBRItems.INK_BOTTLE.get());
        ClientRegister.registerItemColor(new FilledXuanPaperItemColor(), SBRItems.FILLED_XUAN_PAPER.get());
    }
}
