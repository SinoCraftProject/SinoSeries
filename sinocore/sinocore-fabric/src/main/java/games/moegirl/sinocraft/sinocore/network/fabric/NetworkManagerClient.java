package games.moegirl.sinocraft.sinocore.network.fabric;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class NetworkManagerClient {
    static <T extends CustomPacketPayload> void sendToServer(T packet) {
        ClientPlayNetworking.send(packet);
    }
}
