package games.moegirl.sinocraft.sinocore.old.api.woodwork;

import games.moegirl.sinocraft.sinocore.api.utility.Id;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public interface NetworkHolder {

    void sendToServer(Object packet);

    void sendToClient(Object packet, ServerPlayer player);

    <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
                      boolean toClient, Consumer<T> handleClient,
                      boolean toServer, BiConsumer<T, ServerPlayer> handleServer);

    default <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, ServerPlayer> handleServer) {
        register(type, encoder, decoder, false, __ -> {
        }, true, handleServer);
    }

    default <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, Consumer<T> handleClient) {
        register(type, encoder, decoder, true, handleClient, false, (_1, _2) -> {
        });
    }

    static NetworkHolder simple(SimpleChannel channel, Id id) {
        return new NetworkHolder() {

            @Override
            public void sendToServer(Object packet) {
                channel.sendToServer(packet);
            }

            @Override
            public void sendToClient(Object packet, ServerPlayer player) {
                channel.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }

            @Override
            public <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
                                     boolean toClient, Consumer<T> handleClient,
                                     boolean toServer, BiConsumer<T, ServerPlayer> handleServer) {
                if (toClient && toServer) {
                    channel.registerMessage(id.nextId(), type, encoder, decoder, (value, c) -> c.get().enqueueWork(() -> {
                        NetworkEvent.Context context = c.get();
                        if (context.getDirection().getReceptionSide().isClient()) {
                            handleClient.accept(value);
                        } else {
                            handleServer.accept(value, context.getSender());
                        }
                        context.setPacketHandled(true);
                    }));
                } else if (toServer) {
                    channel.registerMessage(id.nextId(), type, encoder, decoder, (value, c) -> c.get().enqueueWork(() -> {
                        NetworkEvent.Context context = c.get();
                        if (context.getDirection().getReceptionSide().isServer()) {
                            handleServer.accept(value, context.getSender());
                        }
                        context.setPacketHandled(true);
                    }), Optional.of(NetworkDirection.PLAY_TO_SERVER));
                } else if (toClient) {
                    channel.registerMessage(id.nextId(), type, encoder, decoder, (value, c) -> c.get().enqueueWork(() -> {
                        NetworkEvent.Context context = c.get();
                        if (context.getDirection().getReceptionSide().isClient()) {
                            handleClient.accept(value);
                        }
                        context.setPacketHandled(true);
                    }), Optional.of(NetworkDirection.PLAY_TO_CLIENT));
                }
            }
        };
    }
}
