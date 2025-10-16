package games.moegirl.sinocraft.sinocore.network.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.network.PacketBuilder;
import games.moegirl.sinocraft.sinocore.network.PacketTarget;
import games.moegirl.sinocraft.sinocore.network.context.*;
import games.moegirl.sinocraft.sinocore.utility.neoforge.ModBusHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.Objects;
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

    public static <T extends CustomPacketPayload> void registerPlay(PacketBuilder<T, RegistryFriendlyByteBuf, ClientPlayNetworkContext, ServerPlayNetworkContext> packet) {
        ModBusHelper.getModBus(packet.getType().id().getNamespace())
                .addListener((Consumer<RegisterPayloadHandlersEvent>) event -> {
                    var type = packet.getType();
                    var codec = packet.getCodec();
                    var registrar = event.registrar(SinoCore.VERSION);

                    var serverHandler = packet.getServerHandler();
                    var clientHandler = packet.getClientHandler();
                    registrar.playBidirectional(type, codec, (p, context) -> {
                        if (context.flow().isClientbound() && clientHandler != null) {
                            clientHandler.accept(p, new ClientPlayNetworkContext(context.connection(), Minecraft.getInstance(), (LocalPlayer) context.player()));
                            return;
                        }

                        if (context.flow().isServerbound() && serverHandler != null) {
                            serverHandler.accept(p, new ServerPlayNetworkContext(context.connection(), Objects.requireNonNull(context.player().getServer()), (ServerPlayer) context.player()));
                        }
                    });
                });
    }

    public static <T extends CustomPacketPayload> void sendConfiguration(T payload, ServerConfigurationPacketListenerImpl handler) {
        handler.send(payload);
    }

    public static <T extends CustomPacketPayload> void sendConfigurationToServer(T payload) {
        Minecraft.getInstance().getConnection().send(payload);
    }

    public static <T extends CustomPacketPayload> void registerConfiguration(PacketBuilder<T, FriendlyByteBuf, ClientConfigurationNetworkContext, ServerConfigurationNetworkContext> packet) {
        ModBusHelper.getModBus(packet.getType().id().getNamespace())
                .addListener((Consumer<RegisterPayloadHandlersEvent>) event -> {
                    var type = packet.getType();
                    var codec = packet.getCodec();
                    var registrar = event.registrar(SinoCore.VERSION);


                    var clientHandler = packet.getClientHandler();
                    var serverHandler = packet.getServerHandler();
                    registrar.configurationBidirectional(type, codec, (p, context) -> {
                        if (context.flow().isClientbound() && clientHandler != null) {
                            clientHandler.accept(p, new ClientConfigurationNetworkContext(context.connection(), Minecraft.getInstance()));
                            return;
                        }

                        if (context.flow().isServerbound() && serverHandler != null) {
                            serverHandler.accept(p, new ServerConfigurationNetworkContext(context.connection(), Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer())));
                        }
                    });
                });
    }
}
