package games.moegirl.sinocraft.sinocore.network;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.packet.SinoPlayPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class SinoPlayPacketBuilder<T extends CustomPacketPayload> {
    private final CustomPacketPayload.Type<T> type;

    private final Set<PacketFlow> destinations = new HashSet<>();

    private StreamCodec<RegistryFriendlyByteBuf, T> codec;
    private BiConsumer<T, PlayNetworkContext> handler;

    public SinoPlayPacketBuilder(CustomPacketPayload.Type<T> type) {
        this.type = type;
    }

    /**
     * Add packet target.
     * {@link PacketFlow#SERVERBOUND} means packet will be sent to server.
     * {@link PacketFlow#CLIENTBOUND} means packet will be sent to client.
     * @param destination Packet target.
     * @return Builder.
     */
    public SinoPlayPacketBuilder<T> destination(PacketFlow destination) {
        destinations.add(destination);
        return this;
    }

    /**
     * Set packet codec.
     * @param codec StreamCodec.
     * @return Builder.
     */
    public SinoPlayPacketBuilder<T> codec(StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        this.codec = codec;
        return this;
    }

    /**
     * Add handler of packet, can be safety composed.
     * @param handler Handler.
     * @return Builder.
     */
    public SinoPlayPacketBuilder<T> handler(BiConsumer<T, PlayNetworkContext> handler) {
        if (this.handler == null) {
            this.handler = handler;
        } else {
            this.handler = this.handler.andThen(handler);
        }
        return this;
    }

    public void register() {
        NetworkManager.register(new SinoPlayPacket<>(type, destinations, codec, handler));
    }
}
