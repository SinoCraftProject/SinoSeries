package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinocore.network.INetworkChannel;
import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import net.minecraft.network.protocol.PacketFlow;

public class SBRNetworks {

    public static final INetworkChannel NETWORKS =  NetworkManager.getOrCreateChannel(SinoBrush.MODID);

    public static void register() {
        NETWORKS.registerPacket(PacketFlow.CLIENTBOUND, S2CDrawingPacket.class);
        NETWORKS.registerPacket(PacketFlow.SERVERBOUND, C2SDrawingPacket.class);
    }
}
