package games.moegirl.sinocraft.sinocore.network.packet.fabric;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.packet.PlayPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class PacketManagerClient {
    static <T extends CustomPacketPayload> void registerClientPacket(PlayPacket<T> packet) {
        ClientPlayNetworking.registerGlobalReceiver(packet.type(),
                (p, context) -> packet.handler()
                        .accept(p, new PlayNetworkContext(ConnectionProtocol.PLAY,
                                context.client().getConnection().getConnection(), null, context.client())));
    }
}
