package games.moegirl.sinocraft.sinocore.network.fabric;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.PacketTarget;
import games.moegirl.sinocraft.sinocore.network.packet.PlayPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class NetworkManagerImpl {

    public static <T extends CustomPacketPayload> void send(T payload, ServerPlayer player) {
        ServerPlayNetworking.send(player, payload);
    }

    public static <T extends CustomPacketPayload> void send(T payload, PacketTarget target) {
        target.send(ServerPlayNetworking.createS2CPacket(payload));
    }

    public static <T extends CustomPacketPayload> void sendToServer(T payload) {
        NetworkManagerClient.sendToServer(payload);
    }

}
