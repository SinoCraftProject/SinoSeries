package games.moegirl.sinocraft.sinocore.network.fabric;

import games.moegirl.sinocraft.sinocore.network.context.ClientConfigurationNetworkContext;
import games.moegirl.sinocraft.sinocore.network.context.ClientPlayNetworkContext;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.Objects;
import java.util.function.BiConsumer;

public class NetworkManagerClient {
    static <T extends CustomPacketPayload> void sendPlayToServer(T packet) {
        ClientPlayNetworking.send(packet);
    }

    static <T extends CustomPacketPayload> void registerPlayClient(CustomPacketPayload.Type<T> type, BiConsumer<T, ClientPlayNetworkContext> handler) {
        ClientPlayNetworking.registerGlobalReceiver(type,
                (p, context) -> handler
                        .accept(p, new ClientPlayNetworkContext(
                                Objects.requireNonNull(context.client().getConnection()).getConnection(),
                                context.client(), context.player())));
    }

    static <T extends CustomPacketPayload> void sendConfigurationToServer(T packet) {
        ClientConfigurationNetworking.send(packet);
    }

    static <T extends CustomPacketPayload> void registerConfigurationClient(CustomPacketPayload.Type<T> type, BiConsumer<T, ClientConfigurationNetworkContext> handler) {
        ClientConfigurationNetworking.registerGlobalReceiver(type,
                (p, context) -> handler
                        .accept(p, new ClientConfigurationNetworkContext(
                                Objects.requireNonNull(context.client().getConnection()).getConnection(), context.client())));
    }
}
