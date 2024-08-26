package games.moegirl.sinocraft.sinotest.network;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class S2CHelloPacket implements Packet<PlayNetworkContext> {

    private final String message;

    public S2CHelloPacket(String message) {
        this.message = message;
    }

    public S2CHelloPacket(FriendlyByteBuf buffer) {
        message = buffer.readUtf();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeUtf(message);
    }

    @Override
    public void handle(PlayNetworkContext handler) {
        TestNetwork.CHANNEL.sendToServer(new C2SHelloPacket("Client thread is " + Thread.currentThread().getName(), message));
    }
}
