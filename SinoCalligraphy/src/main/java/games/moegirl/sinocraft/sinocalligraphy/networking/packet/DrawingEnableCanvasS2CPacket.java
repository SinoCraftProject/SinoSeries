package games.moegirl.sinocraft.sinocalligraphy.networking.packet;

import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.PaperType;
import games.moegirl.sinocraft.sinocalligraphy.utility.NetworkHelper;
import games.moegirl.sinocraft.sinocore.networking.AbstractMessagePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrawingEnableCanvasS2CPacket extends AbstractMessagePacket {

    private final PaperType paperType;
    private final InkType inkType;

    public DrawingEnableCanvasS2CPacket(PaperType paperType, InkType inkType) {
        this.paperType = paperType;
        this.inkType = inkType;
    }

    public DrawingEnableCanvasS2CPacket(FriendlyByteBuf buf) {
        paperType = buf.readEnum(PaperType.class);
        inkType = buf.readEnum(InkType.class);
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeEnum(paperType);
        buf.writeEnum(inkType);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            NetworkHelper.onClientEnableCanvas(paperType, inkType);
            context.setPacketHandled(true);
        });
    }
}
