package games.moegirl.sinocraft.sinocalligraphy.networking.packet;

import games.moegirl.sinocraft.sinocalligraphy.utility.NetworkHelper;
import games.moegirl.sinocraft.sinocore.networking.AbstractMessagePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrawingClearCanvasS2CPacket extends AbstractMessagePacket {

    public DrawingClearCanvasS2CPacket() {
    }

    public DrawingClearCanvasS2CPacket(FriendlyByteBuf buf) {
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            NetworkHelper.onClientClearCanvas();
            context.setPacketHandled(true);
        });
    }
}
