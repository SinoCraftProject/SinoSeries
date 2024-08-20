package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinobrush.gui.screen.BrushScreen;
import games.moegirl.sinocraft.sinocore.network.NetworkContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class S2CDrawResultPacket implements Packet<NetworkContext> {

    public static final int STATUS_SUCCEED = 0;
    public static final int STATUS_FAILED_PAPER = 1;
    public static final int STATUS_FAILED_INK = 2;
    public static final int STATUS_FAILED_DRAW = 3;
    public static final int STATUS_NO_BRUSH = 4;

    private final int status;

    S2CDrawResultPacket(int status) {
        this.status = status;
    }

    public S2CDrawResultPacket(FriendlyByteBuf buffer) {
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

    public static S2CDrawResultPacket ok() {
        return new S2CDrawResultPacket(STATUS_SUCCEED);
    }

    public static S2CDrawResultPacket noPaper() {
        return new S2CDrawResultPacket(STATUS_FAILED_PAPER);
    }

    public static S2CDrawResultPacket noInk() {
        return new S2CDrawResultPacket(STATUS_FAILED_INK);
    }

    public static S2CDrawResultPacket hasDraw() {
        return new S2CDrawResultPacket(STATUS_FAILED_DRAW);
    }

    public static S2CDrawResultPacket noBrush() {
        return new S2CDrawResultPacket(STATUS_NO_BRUSH);
    }
}
