package games.moegirl.sinocraft.sinocore.network.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.neoforge.SinoCoreNeoForge;
import games.moegirl.sinocraft.sinocore.network.PacketTarget;
import games.moegirl.sinocraft.sinocore.network.packet.SinoPlayPacket;
import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.Consumer;

public class NetworkManagerImpl {
    public static <T extends CustomPacketPayload> void send(T payload, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    public static <T extends CustomPacketPayload> void send(T payload, PacketTarget target) {
        target.send(new ClientboundCustomPayloadPacket(payload));
    }

    public static <T extends CustomPacketPayload> void sendToServer(T payload) {
        PacketDistributor.sendToServer(payload);
    }

    static <T extends CustomPacketPayload> void register(SinoPlayPacket<T> packet) {
        SinoCoreNeoForge.getModBus().addListener((Consumer<RegisterPayloadHandlersEvent>) event -> {
            register(event.registrar(SinoCore.VERSION), packet);
        });
    }

    private static <T extends CustomPacketPayload> void register(PayloadRegistrar registrar, SinoPlayPacket<T> packet) {
        if (packet.destinations().contains(PacketFlow.SERVERBOUND)) {
            registrar.playToServer(packet.type(), packet.codec(), (p, context) -> packet.handler().accept(p, new PlayNetworkContext(context.protocol(), context.connection(), (ServerPlayer) context.player(), null)));
        }

        if (packet.destinations().contains(PacketFlow.CLIENTBOUND)) {
            registrar.playToClient(packet.type(), packet.codec(), (p, context) -> packet.handler().accept(p, new PlayNetworkContext(context.protocol(), context.connection(), null, Minecraft.getInstance())));
        }
    }
}
