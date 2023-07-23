package games.moegirl.sinocraft.sinocalligraphy.networking.packet;

import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.utility.NetworkHelper;
import games.moegirl.sinocraft.sinocore.network.AbstractMessagePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrawingEnableCanvasS2CPacket extends AbstractMessagePacket {

    private final int paperColor;
    private final InkType inkType;

    public DrawingEnableCanvasS2CPacket(int paperColor, InkType inkType) {
        this.paperColor = paperColor;
        this.inkType = inkType;
    }

    public DrawingEnableCanvasS2CPacket(FriendlyByteBuf buf) {
        paperColor = buf.readInt();
        inkType = buf.readEnum(InkType.class);
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(paperColor);
        buf.writeEnum(inkType);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            NetworkHelper.onClientEnableCanvas(paperColor, inkType);
            context.setPacketHandled(true);
        });
    }
}
