package games.moegirl.sinocraft.sinocore.test.network;

import games.moegirl.sinocraft.sinocore.network.NetworkContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;

public class C2SHelloPacket implements Packet<NetworkContext> {

    private final String message1;
    private final String message2;

    public C2SHelloPacket(String message1, String message2) {
        this.message1 = message1;
        this.message2 = message2;
    }

    public C2SHelloPacket(FriendlyByteBuf buffer) {
        message1 = buffer.readUtf();
        message2 = buffer.readUtf();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeUtf(message1);
        buffer.writeUtf(message2);
    }

    @Override
    public void handle(NetworkContext handler) {
        ServerPlayer sender = handler.sender();
        ChatType.Bound bind = ChatType.bind(ChatType.CHAT, sender);
        sender.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system(message1)), false, bind);
        sender.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system(message2)), false, bind);
        sender.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system("Server thread is " + Thread.currentThread().getName())), false, bind);
    }
}
