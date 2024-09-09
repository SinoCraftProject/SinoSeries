package games.moegirl.sinocraft.sinobrush;

import com.mojang.logging.LogUtils;
import games.moegirl.sinocraft.sinobrush.data.gen.SBRDataGen;
import games.moegirl.sinocraft.sinobrush.gui.SBRMenu;
import games.moegirl.sinocraft.sinobrush.gui.SBRScreen;
import games.moegirl.sinocraft.sinobrush.handler.CraftingHandlers;
import games.moegirl.sinocraft.sinobrush.handler.RenderHandlers;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.item.component.SBRDataComponents;
import games.moegirl.sinocraft.sinobrush.network.SBRNetworks;
import games.moegirl.sinocraft.sinobrush.stat.SBRStats;
import org.slf4j.Logger;

public class SinoBrush {
    public static final String MOD_NAME = "SinoBrush";
    public static final String MODID = "sinobrush";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SinoBrush() {
        SBRItems.register();
        SBRDataComponents.register();
        SBRMenu.register();
        SBRNetworks.register();
        SBRStats.register();

        CraftingHandlers.register();
        RenderHandlers.register();

        SBRDataGen.register();
    }

    public void init() {
    }

    public void initClient() {
        SBRScreen.register();
    }
}
