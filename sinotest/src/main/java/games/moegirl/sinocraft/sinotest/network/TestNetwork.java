package games.moegirl.sinocraft.sinotest.network;

import games.moegirl.sinocraft.sinocore.network.INetworkChannel;
import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.network.protocol.PacketFlow;

import static games.moegirl.sinocraft.sinocore.SinoCore.MODID;

public class TestNetwork {

    public static INetworkChannel CHANNEL = NetworkManager.getOrCreateChannel(MODID);

    public static void registerAll() {
        CHANNEL.registerPacket(PacketFlow.CLIENTBOUND, S2CHelloPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, C2SHelloPacket.class);
        TestRegistry.ITEMS.register("test_network", TestNetworkItem::new);
    }
}
