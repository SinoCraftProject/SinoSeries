package games.moegirl.sinocraft.sinocore.network.fabric;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.PacketTarget;
import games.moegirl.sinocraft.sinocore.network.SinoPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class NetworkManagerImpl {

    public static <T extends CustomPacketPayload> void send(T packet, ServerPlayer player) {
        ServerPlayNetworking.send(player, packet);
    }

    public static <T extends CustomPacketPayload> void send(T packet, PacketTarget target) {
        target.send(ServerPlayNetworking.createS2CPacket(packet));
    }

    public static <T extends CustomPacketPayload> void sendToServer(T packet) {
        NetworkManagerClient.sendToServer(packet);
    }

    static <T extends CustomPacketPayload> void register(SinoPacket<T> packet) {
        if (packet.destinations().contains(PacketFlow.CLIENTBOUND)) {
            NetworkManagerClient.registerClientPacket(packet);
        }

        if (packet.destinations().contains(PacketFlow.SERVERBOUND)) {
            if (packet.stages().contains(ConnectionProtocol.PLAY)) {
                ServerPlayNetworking.registerGlobalReceiver(packet.type(),
                        (p, context) -> packet.handler()
                                .accept(p, new PlayNetworkContext(ConnectionProtocol.PLAY,
                                        context.player().connection.connection, context.player(), null)));
            }

            // Todo: qyl27: server configuration and login packet.
        }
    }
}
