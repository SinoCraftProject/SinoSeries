package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinobrush.gui.screen.BrushScreen;
import games.moegirl.sinocraft.sinocore.network.NetworkContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class S2CDrawingPacket implements Packet<NetworkContext> {

    public static final int STATUS_SUCCEED = 0;
    public static final int STATUS_FAILED_PAPER = 1;
    public static final int STATUS_FAILED_INK = 2;
    public static final int STATUS_FAILED_DRAW = 3;

    private final int status;

    S2CDrawingPacket(int status) {
        this.status = status;
    }

    public S2CDrawingPacket(FriendlyByteBuf buffer) {
        status = buffer.readVarInt();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeVarInt(status);
    }

    @Override
    public void handle(NetworkContext handler) {
        Minecraft mc = Minecraft.getInstance();
        Screen screen = mc.screen;
        if (screen instanceof BrushScreen xp) {
            xp.handleServiceData(status);
        }
    }

    public static S2CDrawingPacket ok() {
        return new S2CDrawingPacket(STATUS_SUCCEED);
    }

    public static S2CDrawingPacket noPaper() {
        return new S2CDrawingPacket(STATUS_FAILED_PAPER);
    }

    public static S2CDrawingPacket noInk() {
        return new S2CDrawingPacket(STATUS_FAILED_INK);
    }

    public static S2CDrawingPacket hasDraw() {
        return new S2CDrawingPacket(STATUS_FAILED_DRAW);
    }
}
