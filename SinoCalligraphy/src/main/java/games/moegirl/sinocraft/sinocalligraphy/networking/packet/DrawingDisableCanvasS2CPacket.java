package games.moegirl.sinocraft.sinocalligraphy.networking.packet;

import games.moegirl.sinocraft.sinocalligraphy.utility.NetworkHelper;
import games.moegirl.sinocraft.sinocore.network.AbstractMessagePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrawingDisableCanvasS2CPacket extends AbstractMessagePacket {

    public DrawingDisableCanvasS2CPacket() {
    }

    public DrawingDisableCanvasS2CPacket(FriendlyByteBuf buf) {
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            NetworkHelper.onClientDisableCanvas();
            context.setPacketHandled(true);
        });
    }
}
