package games.moegirl.sinocraft.sinocore.network;

import games.moegirl.sinocraft.sinocore.network.context.*;
import games.moegirl.sinocraft.sinocore.utility.ISelf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class PacketBuilder<PACKET extends CustomPacketPayload,
        BUF extends FriendlyByteBuf,
        CLIENT_CONTEXT extends ClientNetworkContext,
        SERVER_CONTEXT extends ServerNetworkContext> {
    private final CustomPacketPayload.Type<PACKET> type;
    private final Consumer<PacketBuilder<PACKET, BUF, CLIENT_CONTEXT, SERVER_CONTEXT>> registerConsumer;

    private StreamCodec<BUF, PACKET> codec;

    @Nullable
    private BiConsumer<PACKET, CLIENT_CONTEXT> clientHandler;

    @Nullable
    private BiConsumer<PACKET, SERVER_CONTEXT> serverHandler;

    @ApiStatus.Internal
    PacketBuilder(Consumer<PacketBuilder<PACKET, BUF, CLIENT_CONTEXT, SERVER_CONTEXT>> registerConsumer,
                  CustomPacketPayload.Type<PACKET> type) {
        this.registerConsumer = registerConsumer;
        this.type = type;
    }

    public PacketBuilder<PACKET, BUF, CLIENT_CONTEXT, SERVER_CONTEXT> codec(StreamCodec<BUF, PACKET> codec) {
        this.codec = codec;
        return this;
    }

    public CustomPacketPayload.Type<PACKET> getType() {
        return type;
    }

    public StreamCodec<BUF, PACKET> getCodec() {
        return codec;
    }

    public PacketBuilder<PACKET, BUF, CLIENT_CONTEXT, SERVER_CONTEXT> clientHandler(BiConsumer<PACKET, CLIENT_CONTEXT> handler) {
        if (clientHandler == null) {
            clientHandler = handler;
        } else {
            clientHandler = clientHandler.andThen(handler);
        }
        return this;
    }

    public PacketBuilder<PACKET, BUF, CLIENT_CONTEXT, SERVER_CONTEXT> serverHandler(BiConsumer<PACKET, SERVER_CONTEXT> handler) {
        if (serverHandler == null) {
            serverHandler = handler;
        } else {
            serverHandler = serverHandler.andThen(handler);
        }
        return this;
    }

    public @Nullable BiConsumer<PACKET, CLIENT_CONTEXT> getClientHandler() {
        return clientHandler;
    }

    public @Nullable BiConsumer<PACKET, SERVER_CONTEXT> getServerHandler() {
        return serverHandler;
    }

    public void register() {
        registerConsumer.accept(this);
    }
}
