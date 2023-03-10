package games.moegirl.sinocraft.sinocore.old.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class PacketBase {
    public PacketBase() {
    }

    public PacketBase(FriendlyByteBuf buf) {
    }

    public abstract void serialize(FriendlyByteBuf buf);

    public abstract void handle(Supplier<NetworkEvent.Context> context);
}
