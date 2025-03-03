package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import net.minecraft.network.protocol.PacketFlow;

public class SBRNetworks {

    public static void register() {
        NetworkManager.playPacket(S2CDrawResultPacket.TYPE)
                .codec(S2CDrawResultPacket.STREAM_CODEC)
                .clientHandler(S2CDrawResultPacket::handle)
                .register();

        NetworkManager.playPacket(C2SSaveDrawPacket.TYPE)
                .codec(C2SSaveDrawPacket.STREAM_CODEC)
                .serverHandler(C2SSaveDrawPacket::handle)
                .register();
    }
}
