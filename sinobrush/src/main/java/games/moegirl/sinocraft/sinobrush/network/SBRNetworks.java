package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import net.minecraft.network.protocol.PacketFlow;

public class SBRNetworks {

    public static void register() {
        NetworkManager.playPacket(S2CDrawResultPacket.TYPE)
                .destination(PacketFlow.SERVERBOUND)
                .codec(S2CDrawResultPacket.STREAM_CODEC)
                .handler(S2CDrawResultPacket::handle)
                .register();

        NetworkManager.playPacket(C2SSaveDrawPacket.TYPE)
                .destination(PacketFlow.SERVERBOUND)
                .codec(C2SSaveDrawPacket.STREAM_CODEC)
                .handler(C2SSaveDrawPacket::handle)
                .register();
    }
}
