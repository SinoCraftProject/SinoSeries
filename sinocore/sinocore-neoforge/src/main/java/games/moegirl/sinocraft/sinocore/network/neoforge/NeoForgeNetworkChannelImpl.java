package games.moegirl.sinocraft.sinocore.network.neoforge;

import games.moegirl.sinocraft.sinocore.network.INetworkChannel;
import games.moegirl.sinocraft.sinocore.network.NetworkContext;
import games.moegirl.sinocraft.sinocore.network.PacketTarget;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.simple.SimpleChannel;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class NeoForgeNetworkChannelImpl implements INetworkChannel {

    private final ResourceLocation id;
    private final SimpleChannel channel;
    private final AtomicInteger packetId;

    public NeoForgeNetworkChannelImpl(ResourceLocation id) {
        this.id = id;
        this.channel = NetworkRegistry.ChannelBuilder.named(id)
                .networkProtocolVersion(() -> "1")
                .simpleChannel();
        this.packetId = new AtomicInteger(0);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public <T extends Packet<NetworkContext>> void registerPacket(PacketFlow direction, Class<T> type, Function<FriendlyByteBuf, T> decoder) {
        channel.messageBuilder(type, packetId.getAndIncrement(), toDirection(direction))
                .encoder(Packet::write)
                .decoder(decoder::apply)
                .consumerMainThread((p, ctx) -> {
                    p.handle(new NetworkContext(ctx.getNetworkManager(), ctx.getSender()));
                    // LexForge 会调用 setPacketHandled(true) 而 NeoForge 不会
                    ctx.setPacketHandled(true);
                })
                .add();
    }

    @Override
    public <T extends Packet<NetworkContext>> void send(T packet, ServerPlayer player) {
        channel.sendTo(packet, player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public <T extends Packet<NetworkContext>> void send(T packet, PacketTarget target) {
        channel.send(toTarget(target), packet);
    }

    @Override
    public <T extends Packet<NetworkContext>> void sendToServer(T packet) {
        channel.sendToServer(packet);
    }

    private PlayNetworkDirection toDirection(PacketFlow direction) {
        return direction == PacketFlow.CLIENTBOUND
                ? PlayNetworkDirection.PLAY_TO_CLIENT
                : PlayNetworkDirection.PLAY_TO_SERVER;
    }

    private PacketDistributor.PacketTarget toTarget(PacketTarget target) {
        return new PacketDistributor<Void>((a, b) -> target.sender(), toDirection(target.direction())).noArg();
    }
}
