package games.moegirl.sinocraft.sinocore.network.packet.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.packet.PlayPacket;
import games.moegirl.sinocraft.sinocore.utility.neoforge.ModBusHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.Consumer;

public class PacketManagerImpl {
    public static <T extends CustomPacketPayload> void register(PlayPacket<T> packet) {
        ModBusHelper.getModBus(packet.type().id().getNamespace())
                .addListener((Consumer<RegisterPayloadHandlersEvent>) event ->
                        register(event.registrar(SinoCore.VERSION), packet));
    }

    private static <T extends CustomPacketPayload> void register(PayloadRegistrar registrar, PlayPacket<T> packet) {
        if (packet.destinations().contains(PacketFlow.SERVERBOUND)) {
            registrar.playToServer(packet.type(), packet.codec(), (p, context) -> packet.handler().accept(p, new PlayNetworkContext(context.protocol(), context.connection(), (ServerPlayer) context.player(), null)));
        }

        if (packet.destinations().contains(PacketFlow.CLIENTBOUND)) {
            registrar.playToClient(packet.type(), packet.codec(), (p, context) -> packet.handler().accept(p, new PlayNetworkContext(context.protocol(), context.connection(), null, Minecraft.getInstance())));
        }
    }
}
