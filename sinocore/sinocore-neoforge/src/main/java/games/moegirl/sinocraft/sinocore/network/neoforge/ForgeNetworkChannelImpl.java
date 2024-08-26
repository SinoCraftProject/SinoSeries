package games.moegirl.sinocraft.sinocore.network.neoforge;

import games.moegirl.sinocraft.sinocore.network.INetworkChannel;
import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.PacketTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Objects;
import java.util.function.Function;

public class ForgeNetworkChannelImpl implements INetworkChannel {

    private final ResourceLocation id;
    private final SimpleChannel channel;

    private int packetId = 0;

    public ForgeNetworkChannelImpl(ResourceLocation id) {
        this.id = id;
        this.channel = NetworkRegistry.ChannelBuilder.named(id)
                .networkProtocolVersion(() -> "1")
                // 当客户端/服务端缺失 Channel 时，允许继续发送
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    private int nextPacketId() {
        return packetId++;
    }

    @Override
    public <T extends Packet<PlayNetworkContext>> void registerPacket(PacketFlow direction, Class<T> type, Function<FriendlyByteBuf, T> decoder) {
        channel.messageBuilder(type, nextPacketId())
                .encoder(Packet::write)
                .decoder(decoder)
                .consumerMainThread((p, ctx) -> p.handle(new PlayNetworkContext(ctx.get().getNetworkManager(), ctx.get().getSender())))
                .add();
    }

    @Override
    public <T extends Packet<PlayNetworkContext>> void send(T packet, ServerPlayer player) {
        channel.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public <T extends Packet<PlayNetworkContext>> void send(T packet, PacketTarget target) {
        target.send(packet);
        // Fixme: qyl27
//        channel.send(toTarget(target), packet);
    }

    @Override
    public <T extends Packet<PlayNetworkContext>> void sendToServer(T packet) {
        Minecraft instance = Minecraft.getInstance();
        ClientPacketListener connection = Objects.requireNonNull(instance).getConnection();
        channel.sendToServer(packet);
    }

    private NetworkDirection toDirection(PacketFlow direction) {
        return direction == PacketFlow.CLIENTBOUND
                ? NetworkDirection.PLAY_TO_CLIENT
                : NetworkDirection.PLAY_TO_SERVER;
    }

    private PacketDistributor.PacketTarget toTarget(PacketTarget target) {
        // Fixme: qyl27
//        return new PacketDistributor.PacketTarget(target.sender(), toDirection(target.direction()));
        return PacketDistributor.SERVER.noArg();
    }
}
