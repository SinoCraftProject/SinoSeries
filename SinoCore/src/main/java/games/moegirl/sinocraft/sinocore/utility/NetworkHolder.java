package games.moegirl.sinocraft.sinocore.utility;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 对 Mod 网络管理器的一个包装
 */
public interface NetworkHolder {

    void sendToServer(Object packet);

    void sendToClient(Object packet, ServerPlayer player);

    <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
                      @Nullable Consumer<T> handleClient, @Nullable BiConsumer<T, ServerPlayer> handleServer);

    default <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, ServerPlayer> handleServer) {
        register(type, encoder, decoder, null, handleServer);
    }

    default <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, Consumer<T> handleClient) {
        register(type, encoder, decoder, handleClient, null);
    }

    default <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder) {
        boolean toServer = false;
        if (Consumer.class.isAssignableFrom(type))
            for (Type anInterface : type.getGenericInterfaces())
                if (anInterface instanceof ParameterizedType pt && pt.getRawType() == Consumer.class)
                    toServer = pt.getActualTypeArguments()[0] == ServerPlayer.class;
        register(type, encoder, decoder,
                Runnable.class.isAssignableFrom(type) ? v -> ((Runnable) v).run() : null,
                toServer ? (v, p) -> ((Consumer) v).accept(p) : null);
    }

    static NetworkHolder simple(SimpleChannel channel, AtomicInteger id) {
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
                                     @Nullable Consumer<T> handleClient,
                                     @Nullable BiConsumer<T, ServerPlayer> handleServer) {
                boolean toClient = handleClient != null;
                boolean toServer = handleServer != null;
                if (toClient && toServer) {
                    channel.registerMessage(id.getAndIncrement(), type, encoder, decoder, (value, c) -> c.get().enqueueWork(() -> {
                        NetworkEvent.Context context = c.get();
                        if (context.getDirection().getReceptionSide().isClient()) {
                            handleClient.accept(value);
                        } else {
                            handleServer.accept(value, context.getSender());
                        }
                        context.setPacketHandled(true);
                    }));
                } else if (toServer) {
                    channel.registerMessage(id.getAndIncrement(), type, encoder, decoder, (value, c) -> c.get().enqueueWork(() -> {
                        NetworkEvent.Context context = c.get();
                        if (context.getDirection().getReceptionSide().isServer()) {
                            handleServer.accept(value, context.getSender());
                        }
                        context.setPacketHandled(true);
                    }), Optional.of(NetworkDirection.PLAY_TO_SERVER));
                } else if (toClient) {
                    channel.registerMessage(id.getAndIncrement(), type, encoder, decoder, (value, c) -> c.get().enqueueWork(() -> {
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
