package games.moegirl.sinocraft.sinocalligraphy.networking.packet;

import games.moegirl.sinocraft.sinocalligraphy.utility.NetworkHelper;
import games.moegirl.sinocraft.sinocore.networking.AbstractMessagePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrawingEnableCanvasS2CPacket extends AbstractMessagePacket {

    private final boolean isEnabled;

    public DrawingEnableCanvasS2CPacket(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public DrawingEnableCanvasS2CPacket(FriendlyByteBuf buf) {
        isEnabled = buf.readBoolean();
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeBoolean(isEnabled);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            NetworkHelper.onClientEnableCanvas(isEnabled);
            context.setPacketHandled(true);
        });
    }
}
