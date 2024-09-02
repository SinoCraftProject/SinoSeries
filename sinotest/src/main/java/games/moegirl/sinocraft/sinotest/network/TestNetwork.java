package games.moegirl.sinocraft.sinotest.network;

import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.item.Item;

public class TestNetwork {

    public static IRegRef<Item, TestNetworkItem> ITEM;

    public static void registerAll() {

        NetworkManager.packet(C2SHelloPacket.TYPE)
                .destination(PacketFlow.SERVERBOUND)
                .stage(ConnectionProtocol.PLAY)
                .codec(C2SHelloPacket.CODEC)
                .handler(C2SHelloPacket::handle)
                .register();

        NetworkManager.packet(S2CHelloPacket.TYPE)
                .destination(PacketFlow.CLIENTBOUND)
                .stage(ConnectionProtocol.PLAY)
                .codec(S2CHelloPacket.CODEC)
                .handler(S2CHelloPacket::handle)
                .register();

        ITEM = TestRegistry.ITEMS.register("test_network", TestNetworkItem::new);
    }
}
