package games.moegirl.sinocraft.sinocore.network;

import games.moegirl.sinocraft.sinocore.SinoCorePlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 模仿 Forge 的 PacketDispatcher，几种 MC 内置的发包方法
 *
 * @param senderBuilder 构造器创建方法
 * @param direction     方向，{@link PacketFlow#CLIENTBOUND} 表示从服务端到客户端
 * @param <T>           参数类型，{@link Void} 表示无参数（使用 with(null) 创建）
 */
public record PacketDistributor<T>(Function<T, Consumer<Packet<?>>> senderBuilder, PacketFlow direction) {

    static <T> PacketDistributor<T> serverToClient(Function<T, Consumer<Packet<?>>> senderBuilder) {
        return new PacketDistributor<>(senderBuilder, PacketFlow.CLIENTBOUND);
    }

    static <T> PacketDistributor<T> clientToServer(Function<T, Consumer<Packet<?>>> senderBuilder) {
        return new PacketDistributor<>(senderBuilder, PacketFlow.SERVERBOUND);
    }

    /**
     * （服务器->客户端）发送到指定玩家
     */
    public static final PacketDistributor<ServerPlayer> PLAYER = serverToClient(player ->
            p -> player.connection.send(p));

    /**
     * （服务器->客户端）发送到某个维度的所有玩家
     */
    public static final PacketDistributor<ResourceKey<Level>> DIMENSION = serverToClient(dim ->
            p -> SinoCorePlatform.getServer().getPlayerList().broadcastAll(p, dim));

    /**
     * （服务器->客户端）发送到指定位置指定半径内的所有玩家
     */
    public static final PacketDistributor<TargetPoint> NEAR = serverToClient(tp ->
            p -> SinoCorePlatform.getServer().getPlayerList().broadcast(tp.excluded, tp.x, tp.y, tp.z, tp.radius, tp.dim, p));

    /**
     * （服务器->客户端）发送到服务器中的所有玩家
     */
    public static final PacketDistributor<Void> ALL = serverToClient(__ ->
            p -> SinoCorePlatform.getServer().getPlayerList().broadcastAll(p));

    /**
     * （服务器->客户端）发送到所有追踪某个实体的所有其他玩家
     */
    public static final PacketDistributor<Entity> TRACKING_ENTITY = serverToClient(entity ->
            p -> ((ServerChunkCache) entity.getCommandSenderWorld().getChunkSource()).broadcast(entity, p));

    /**
     * （服务器->客户端）发送到所有追踪某个实体的所有玩家，包括该实体本身
     */
    public static final PacketDistributor<Entity> TRACKING_ENTITY_AND_SELF = serverToClient(entity ->
            p -> ((ServerChunkCache) entity.getCommandSenderWorld().getChunkSource()).broadcastAndSend(entity, p));

    /**
     * （服务器->客户端）发送到所有追踪指定区块的玩家
     */
    public static final PacketDistributor<LevelChunk> TRACKING_CHUNK = serverToClient(chunk ->
            p -> ((ServerChunkCache) chunk.getLevel().getChunkSource()).chunkMap
                    .getPlayers(chunk.getPos(), false)
                    .forEach(pl -> pl.connection.send(p)));

    /**
     * （服务器->客户端）发送到列表中所有 {@link Connection}
     */
    public static final PacketDistributor<List<Connection>> CONNECTION_LIST = serverToClient(list ->
            p -> list.forEach(conn -> conn.send(p)));

    /**
     * 从客户端发送到服务器
     */
    public static final PacketDistributor<Void> SERVER = clientToServer(__ ->
            p -> Minecraft.getInstance().getConnection().send(p));

    public record TargetPoint(ServerPlayer excluded, double x, double y, double z, double radius,
                              ResourceKey<Level> dim) {

        public TargetPoint(double x, double y, double z, double r2, ResourceKey<Level> dim) {
            this(null, x, y, z, r2, dim);
        }
    }

    public PacketTarget with(T input) {
        return new PacketTarget(senderBuilder.apply(input), direction);
    }

    public PacketTarget noArg() {
        return new PacketTarget(senderBuilder.apply(null), direction);
    }
}
