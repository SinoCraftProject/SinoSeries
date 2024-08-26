package games.moegirl.sinocraft.sinocore.network;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class SinoPacketBuilder<T extends CustomPacketPayload> {
    private final CustomPacketPayload.Type<T> type;

    private final Set<ConnectionProtocol> stages = new HashSet<>();
    private final Set<PacketFlow> destinations = new HashSet<>();

    private StreamCodec<FriendlyByteBuf, T> codec;
    private BiConsumer<T, PlayNetworkContext> handler;

    public SinoPacketBuilder(CustomPacketPayload.Type<T> type) {
        this.type = type;
    }

    /**
     * Add packet listing stage.
     * Now accepts {@link ConnectionProtocol#PLAY} only.
     * @param stage stage.
     * @return Builder.
     */
    // Todo: qyl27: supports ConnectionProtocol.CONFIGURATION and ConnectionProtocol.LOGIN
    public SinoPacketBuilder<T> stage(ConnectionProtocol stage) {
        if (stage != ConnectionProtocol.PLAY) {
            throw new UnsupportedOperationException("Not support for now, call qyl to do that!");
        }

        stages.add(stage);
        return this;
    }

    /**
     * Add packet target.
     * {@link PacketFlow#SERVERBOUND} means packet will be sent to server.
     * {@link PacketFlow#CLIENTBOUND} means packet will be sent to client.
     * @param destination Packet target.
     * @return Builder.
     */
    public SinoPacketBuilder<T> destination(PacketFlow destination) {
        destinations.add(destination);
        return this;
    }

    /**
     * Set packet codec.
     * @param codec StreamCodec.
     * @return Builder.
     */
    public SinoPacketBuilder<T> codec(StreamCodec<FriendlyByteBuf, T> codec) {
        this.codec = codec;
        return this;
    }

    /**
     * Add handler of packet, can be safety composed.
     * @param handler Handler.
     * @return Builder.
     */
    public SinoPacketBuilder<T> handler(BiConsumer<T, PlayNetworkContext> handler) {
        if (this.handler == null) {
            this.handler = handler;
        } else {
            this.handler = this.handler.andThen(handler);
        }
        return this;
    }

    public void register() {
        NetworkManager.register(new SinoPacket<>(type, stages, destinations, codec, handler));
    }
}
