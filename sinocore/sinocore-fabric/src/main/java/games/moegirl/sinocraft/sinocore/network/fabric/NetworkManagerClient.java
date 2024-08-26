package games.moegirl.sinocraft.sinocore.network.fabric;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.SinoPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class NetworkManagerClient {
    static  <T extends CustomPacketPayload> void registerClientPacket(SinoPacket<T> packet) {
        if (packet.stages().contains(ConnectionProtocol.PLAY)) {
            ClientPlayNetworking.registerGlobalReceiver(packet.type(),
                    (p, context) -> packet.handler()
                            .accept(p, new PlayNetworkContext(ConnectionProtocol.PLAY,
                                    context.client().getConnection().getConnection(), null, context.client())));
        }

        if (packet.stages().contains(ConnectionProtocol.CONFIGURATION)) {
            ClientConfigurationNetworking.registerGlobalReceiver(packet.type(),
                    (p, context) -> packet.handler()
                            .accept(p, new PlayNetworkContext(ConnectionProtocol.PLAY,
                                    context.client().getConnection().getConnection(), null, context.client())));
        }

        // Todo: qyl27: client login packet
        if (packet.stages().contains(ConnectionProtocol.LOGIN)) {
        }
    }

    static <T extends CustomPacketPayload> void sendToServer(T packet) {
        ClientPlayNetworking.send(packet);
    }
}
