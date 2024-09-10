package games.moegirl.sinocraft.sinotest.network;

import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record S2CHelloPacket(String message) implements CustomPacketPayload {

    public static final Type<S2CHelloPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SinoTest.MODID, "hello"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CHelloPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, S2CHelloPacket::message,
            S2CHelloPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(PlayNetworkContext context) {
        NetworkManager.sendToServer(new C2SHelloPacket("Client thread is " + Thread.currentThread().getName(), message));
    }
}
