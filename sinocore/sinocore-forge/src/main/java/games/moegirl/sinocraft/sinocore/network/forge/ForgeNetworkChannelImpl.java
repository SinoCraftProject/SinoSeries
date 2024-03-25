package games.moegirl.sinocraft.sinocore.network.forge;

import games.moegirl.sinocraft.sinocore.network.INetworkChannel;
import games.moegirl.sinocraft.sinocore.network.NetworkContext;
import games.moegirl.sinocraft.sinocore.network.PacketTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

import java.util.Objects;
import java.util.function.Function;

public class ForgeNetworkChannelImpl implements INetworkChannel {

    private final ResourceLocation id;
    private final SimpleChannel channel;

    public ForgeNetworkChannelImpl(ResourceLocation id) {
        this.id = id;
        this.channel = ChannelBuilder.named(id)
                .networkProtocolVersion(1)
                // 当客户端/服务端缺失 Channel 时，允许继续发送
                .optional()
                .simpleChannel();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public <T extends Packet<NetworkContext>> void registerPacket(PacketFlow direction, Class<T> type, Function<FriendlyByteBuf, T> decoder) {
        channel.messageBuilder(type, toDirection(direction))
                .encoder(Packet::write)
                .decoder(decoder)
                .consumerMainThread((p, ctx) -> p.handle(new NetworkContext(ctx.getConnection(), ctx.getSender())))
                .add();
    }

    @Override
    public <T extends Packet<NetworkContext>> void send(T packet, ServerPlayer player) {
        channel.send(packet, player.connection.getConnection());
    }

    @Override
    public <T extends Packet<NetworkContext>> void send(T packet, PacketTarget target) {
        channel.send(packet, toTarget(target));
    }

    @Override
    public <T extends Packet<NetworkContext>> void sendToServer(T packet) {
        Minecraft instance = Minecraft.getInstance();
        ClientPacketListener connection = Objects.requireNonNull(instance).getConnection();
        channel.send(packet, Objects.requireNonNull(connection).getConnection());
    }

    private NetworkDirection toDirection(PacketFlow direction) {
        return direction == PacketFlow.CLIENTBOUND
                ? NetworkDirection.PLAY_TO_CLIENT
                : NetworkDirection.PLAY_TO_SERVER;
    }

    private PacketDistributor.PacketTarget toTarget(PacketTarget target) {
        return new PacketDistributor.PacketTarget(target.sender(), toDirection(target.direction()));
    }
}
