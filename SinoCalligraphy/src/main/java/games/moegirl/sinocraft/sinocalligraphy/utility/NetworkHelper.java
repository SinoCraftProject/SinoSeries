package games.moegirl.sinocraft.sinocalligraphy.utility;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.gui.screen.BrushScreen;
import games.moegirl.sinocraft.sinocalligraphy.networking.packet.DrawingSaveResultS2CPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.time.Duration;

public class NetworkHelper {
    public static void onClientHandleDraw(DrawingSaveResultS2CPacket.Result result) {
        var screen = Minecraft.getInstance().screen;
        if (screen instanceof BrushScreen brushScreen) {
            switch (result) {
                case NO_INK -> showFail(brushScreen, Component.translatable(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_INK));
                case NO_PAPER -> showFail(brushScreen, Component.translatable(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_PAPER));
                case UNKNOWN_SCREEN -> showFail(brushScreen, Component.translatable(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_UNKNOWN_SCREEN));
                case SUCCEED -> showSuccess(brushScreen, Component.translatable(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_SUCCESS));
            }
        }
    }

    public static void onClientClearCanvas() {
        var screen = Minecraft.getInstance().screen;
        if (screen instanceof BrushScreen brushScreen) {
            brushScreen.getCanvas().clear();
        }
    }

    public static void onClientEnableCanvas(int paperColor, InkType inkType) {
        var screen = Minecraft.getInstance().screen;
        if (screen instanceof BrushScreen brushScreen) {
            if (!brushScreen.getCanvas().isEnabled()) {
                brushScreen.updateCanvas(paperColor, inkType);
                brushScreen.getCanvas().setEnabled(true);
                brushScreen.getTitleBox().setEditable(true);
            }
        }
    }

    public static void onClientDisableCanvas() {
        var screen = Minecraft.getInstance().screen;
        if (screen instanceof BrushScreen brushScreen) {
            brushScreen.getCanvas().setEnabled(false);
            brushScreen.getTitleBox().setEditable(false);
        }
    }

    private static void showFail(BrushScreen screen, Component component) {
        screen.getText().begin(Duration.ofSeconds(2), 0, 255, 0, 0, component);
    }

    private static void showSuccess(BrushScreen screen, Component component) {
        screen.getText().begin(Duration.ofSeconds(2), 0, 0, 255, 0, component);
    }
}
