package games.moegirl.sinocraft.sinocore.network;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;

import java.util.function.Consumer;

public record PacketTarget(Consumer<Packet<?>> sender, PacketFlow direction) {

    public void send(Packet<?> packet) {
        sender.accept(packet);
    }
}
