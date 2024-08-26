package games.moegirl.sinocraft.sinocore.network;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.Set;
import java.util.function.BiConsumer;

public record SinoPacket<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type,
                                                        Set<ConnectionProtocol> stages,
                                                        Set<PacketFlow> destinations,
                                                        StreamCodec<FriendlyByteBuf, T> codec,
                                                        BiConsumer<T, PlayNetworkContext> handler) {
}
