package games.moegirl.sinocraft.sinocore.utility;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
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

    /**
     * 将数据包发送到服务端
     */
    void sendToServer(Object packet);

    /**
     * 将数据包发送到客户端某玩家
     */
    void sendToClient(Object packet, ServerPlayer player);

    /**
     * 注册服务器-客户端双端通信数据包及处理函数
     */
    <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
                      @Nullable Consumer<T> handleClient, @Nullable BiConsumer<T, ServerPlayer> handleServer);

    /**
     * 注册客户端到服务端的数据包类型及处理函数
     */
    default <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, ServerPlayer> handleServer) {
        register(type, encoder, decoder, null, handleServer);
    }

    /**
     * 注册服务端到客户端的数据包类型及处理函数
     */
    default <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, Consumer<T> handleClient) {
        register(type, encoder, decoder, handleClient, null);
    }

    /**
     * 注册数据包类型及处理函数
     * <p>给定类型 T 若实现了 {@code Consumer<ServerPlayer>} 则表示该数据包允许从客户端向服务端发送，{@link Consumer#accept(Object)} 即处理参数</p>
     * <p>给定类型 T 若实现了 {@code Runnable} 则表示该数据包允许从服务端向客户端发送，{@link Runnable#run()} 方法即处理参数</p>
     */
    default <T> void register(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder) {
        boolean toServer = false;
        if (Consumer.class.isAssignableFrom(type))
            for (Type anInterface : type.getGenericInterfaces())
                if (anInterface instanceof ParameterizedType pt && pt.getRawType() == Consumer.class)
                    toServer = pt.getActualTypeArguments()[0] == ServerPlayer.class;
        //noinspection unchecked,rawtypes
        register(type, encoder, decoder,
                Runnable.class.isAssignableFrom(type) ? v -> ((Runnable) v).run() : null,
                toServer ? (v, p) -> ((Consumer) v).accept(p) : null);
    }

    /**
     * 以 {@link SimpleChannel} 为基础的网络
     */
    static SimpleNetworkHandler simple(SimpleChannel channel, AtomicInteger id) {
        return new SimpleNetworkHandler(channel, id);
    }

    /**
     * 以 {@link SimpleChannel} 为基础的网络
     */
    static SimpleNetworkHandler simple(ResourceLocation name) {
        SimpleChannel channel = NetworkRegistry.newSimpleChannel(name, () -> "1", s -> true, s -> true);
        AtomicInteger id = new AtomicInteger();
        return simple(channel, id);
    }

    record SimpleNetworkHandler(SimpleChannel channel, AtomicInteger id) implements NetworkHolder {

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
                                     @Nullable Consumer<T> handleClient, @Nullable BiConsumer<T, ServerPlayer> handleServer) {
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
        }
}
