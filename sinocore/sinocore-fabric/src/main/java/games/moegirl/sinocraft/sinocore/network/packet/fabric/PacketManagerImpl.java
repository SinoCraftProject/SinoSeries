package games.moegirl.sinocraft.sinocore.network.packet.fabric;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.fabric.NetworkManagerClient;
import games.moegirl.sinocraft.sinocore.network.packet.PlayPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class PacketManagerImpl {
    public static <T extends CustomPacketPayload> void register(PlayPacket<T> packet) {
        if (packet.destinations().contains(PacketFlow.CLIENTBOUND)) {
            PayloadTypeRegistry.playS2C().register(packet.type(), packet.codec());

            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                PacketManagerClient.registerClientPacket(packet);
            }
        }

        if (packet.destinations().contains(PacketFlow.SERVERBOUND)) {
            PayloadTypeRegistry.playC2S().register(packet.type(), packet.codec());
            ServerPlayNetworking.registerGlobalReceiver(packet.type(),
                    (p, context) -> packet.handler()
                            .accept(p, new PlayNetworkContext(ConnectionProtocol.PLAY,
                                    context.player().connection.connection, context.player(), null)));
        }
    }
}
