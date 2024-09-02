package games.moegirl.sinocraft.sinocore.network.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.neoforge.SinoCoreNeoForge;
import games.moegirl.sinocraft.sinocore.network.PacketTarget;
import games.moegirl.sinocraft.sinocore.network.SinoPacket;
import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.Consumer;

public class NetworkManagerImpl {
    public static <T extends CustomPacketPayload> void send(T packet, ServerPlayer player) {
    }

    public static <T extends CustomPacketPayload> void send(T packet, PacketTarget target) {
    }

    public static <T extends CustomPacketPayload> void sendToServer(T packet) {
    }

    static <T extends CustomPacketPayload> void register(SinoPacket<T> packet) {
        SinoCoreNeoForge.getModBus().addListener((Consumer<RegisterPayloadHandlersEvent>) event -> {
            register(event.registrar(SinoCore.VERSION), packet);
        });
    }

    private static <T extends CustomPacketPayload> void register(PayloadRegistrar registrar, SinoPacket<T> packet) {
        // Todo: qyl27: configuration and login packet.
        if (packet.stages().contains(ConnectionProtocol.PLAY)) {
            if (packet.destinations().contains(PacketFlow.SERVERBOUND)) {
                registrar.playToServer(packet.type(), packet.codec(), (p, context) -> packet.handler().accept(p, new PlayNetworkContext(context.protocol(), context.connection(), (ServerPlayer) context.player(), null)));
            }

            if (packet.destinations().contains(PacketFlow.CLIENTBOUND)) {
                registrar.playToClient(packet.type(), packet.codec(), (p, context) -> packet.handler().accept(p, new PlayNetworkContext(context.protocol(), context.connection(), null, Minecraft.getInstance())));
            }
        }
    }
}
