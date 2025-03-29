package games.moegirl.sinocraft.sinobrush.client;

import games.moegirl.sinocraft.sinobrush.gui.screen.FanScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ClientHelper {
    public static void showEditingFanScreen(List<Component> lines) {
        Minecraft.getInstance().setScreen(new FanScreen(lines));
    }
}
