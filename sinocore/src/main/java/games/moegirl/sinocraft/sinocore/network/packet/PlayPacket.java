package games.moegirl.sinocraft.sinocore.network.packet;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.Set;
import java.util.function.BiConsumer;

public record PlayPacket<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type,
                                                        Set<PacketFlow> destinations,
                                                        StreamCodec<RegistryFriendlyByteBuf, T> codec,
                                                        BiConsumer<T, PlayNetworkContext> handler) {
}
