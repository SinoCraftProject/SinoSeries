package games.moegirl.sinocraft.sinotest.network;

import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record C2SHelloPacket(String message1, String message2) implements CustomPacketPayload {

    public static final Type<C2SHelloPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SinoTest.MODID, "hello"));
    public static final StreamCodec<RegistryFriendlyByteBuf, C2SHelloPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, C2SHelloPacket::message1,
            ByteBufCodecs.STRING_UTF8, C2SHelloPacket::message2,
            C2SHelloPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(PlayNetworkContext context) {
        ServerPlayer sender = context.sender();
        ChatType.Bound bind = ChatType.bind(ChatType.CHAT, sender);
        sender.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system(message1)), false, bind);
        sender.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system(message2)), false, bind);
        sender.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system("Server thread is " + Thread.currentThread().getName())), false, bind);
    }
}
