package games.moegirl.sinocraft.sinocalligraphy.networking.packet;

import games.moegirl.sinocraft.sinocalligraphy.utility.NetworkHelper;
import games.moegirl.sinocraft.sinocore.networking.AbstractMessagePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrawingSaveResultS2CPacket extends AbstractMessagePacket {

    private Result result;

    public static DrawingSaveResultS2CPacket unknownScreen() {
        return new DrawingSaveResultS2CPacket(Result.UNKNOWN_SCREEN);
    }

    public static DrawingSaveResultS2CPacket noPaper() {
        return new DrawingSaveResultS2CPacket(Result.NO_PAPER);
    }

    public static DrawingSaveResultS2CPacket noInk() {
        return new DrawingSaveResultS2CPacket(Result.NO_INK);
    }

    public static DrawingSaveResultS2CPacket succeed() {
        return new DrawingSaveResultS2CPacket(Result.SUCCEED);
    }

    public DrawingSaveResultS2CPacket(Result result) {
        this.result = result;
    }

    public DrawingSaveResultS2CPacket(FriendlyByteBuf buf) {
        this.result = buf.readEnum(Result.class);
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeEnum(result);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            NetworkHelper.onClientHandleDraw(result);
            context.setPacketHandled(true);
        });
    }

    public enum Result {
        SUCCEED, UNKNOWN_SCREEN, NO_PAPER, NO_INK
    }
}
