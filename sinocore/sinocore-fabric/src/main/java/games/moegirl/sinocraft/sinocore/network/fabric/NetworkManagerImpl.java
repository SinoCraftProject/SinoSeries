package games.moegirl.sinocraft.sinocore.network.fabric;

import games.moegirl.sinocraft.sinocore.network.PacketBuilder;
import games.moegirl.sinocraft.sinocore.network.PacketTarget;
import games.moegirl.sinocraft.sinocore.network.context.ClientConfigurationNetworkContext;
import games.moegirl.sinocraft.sinocore.network.context.ClientPlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.context.ServerConfigurationNetworkContext;
import games.moegirl.sinocraft.sinocore.network.context.ServerPlayNetworkContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

public class NetworkManagerImpl {

    public static <T extends CustomPacketPayload> void send(T payload, ServerPlayer player) {
        ServerPlayNetworking.send(player, payload);
    }

    public static <T extends CustomPacketPayload> void send(T payload, PacketTarget target) {
        target.send(ServerPlayNetworking.createS2CPacket(payload));
    }

    public static <T extends CustomPacketPayload> void sendToServer(T payload) {
        NetworkManagerClient.sendPlayToServer(payload);
    }

    public static <T extends CustomPacketPayload> void registerPlay(PacketBuilder<T, RegistryFriendlyByteBuf, ClientPlayNetworkContext, ServerPlayNetworkContext> packet) {
        var type = packet.getType();

        var clientHandler = packet.getClientHandler();
        if (clientHandler != null) {
            PayloadTypeRegistry.playS2C().register(type, packet.getCodec());

            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                NetworkManagerClient.registerPlayClient(type, clientHandler);
            }
        }

        var serverHandler = packet.getServerHandler();
        if (serverHandler != null) {
            PayloadTypeRegistry.playC2S().register(type, packet.getCodec());

            ServerPlayNetworking.registerGlobalReceiver(type,
                    (p, context) -> serverHandler
                            .accept(p, new ServerPlayNetworkContext(context.player().connection.connection,
                                    context.server(), context.player())));
        }
    }

    public static <T extends CustomPacketPayload> void sendConfiguration(T payload, ServerConfigurationPacketListenerImpl handler) {
        ServerConfigurationNetworking.send(handler, payload);
    }

    public static <T extends CustomPacketPayload> void sendConfigurationToServer(T payload) {
        NetworkManagerClient.sendConfigurationToServer(payload);
    }

    public static <T extends CustomPacketPayload> void registerConfiguration(PacketBuilder<T, FriendlyByteBuf, ClientConfigurationNetworkContext, ServerConfigurationNetworkContext> packet) {
        var type = packet.getType();

        var clientHandler = packet.getClientHandler();
        if (clientHandler != null) {
            PayloadTypeRegistry.configurationS2C().register(type, packet.getCodec());

            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                NetworkManagerClient.registerConfigurationClient(type, clientHandler);
            }
        }

        var serverHandler = packet.getServerHandler();
        if (serverHandler != null) {
            PayloadTypeRegistry.configurationC2S().register(type, packet.getCodec());

            ServerConfigurationNetworking.registerGlobalReceiver(type,
                    (p, context) -> serverHandler
                            .accept(p, new ServerConfigurationNetworkContext(context.networkHandler().connection, context.server())));
        }
    }
}
