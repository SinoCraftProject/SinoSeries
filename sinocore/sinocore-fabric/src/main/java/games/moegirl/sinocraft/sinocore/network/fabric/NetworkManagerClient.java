package games.moegirl.sinocraft.sinocore.network.fabric;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.packet.SinoPlayPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class NetworkManagerClient {
    static <T extends CustomPacketPayload> void registerClientPacket(SinoPlayPacket<T> packet) {
        ClientPlayNetworking.registerGlobalReceiver(packet.type(),
                (p, context) -> packet.handler()
                        .accept(p, new PlayNetworkContext(ConnectionProtocol.PLAY,
                                context.client().getConnection().getConnection(), null, context.client())));
    }

    static <T extends CustomPacketPayload> void sendToServer(T packet) {
        ClientPlayNetworking.send(packet);
    }
}
