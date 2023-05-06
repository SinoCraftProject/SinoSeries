package games.moegirl.sinocraft.sinodivination.old.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class BasePacket<T extends BasePacket<T>> {

    public BasePacket(FriendlyByteBuf buf) {

    }

    public BasePacket() {

    }

    public abstract void encode(FriendlyByteBuf buf);

    public void consume(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> onServer(context, context.getSender()));
        } else {
            context.enqueueWork(() -> onClient(context));
        }
        context.setPacketHandled(true);
    }

    protected void onClient(NetworkEvent.Context context) {
        throw new IllegalStateException("Packet " + getClass().getSimpleName() + " can only execute at server side.");
    }

    protected void onServer(NetworkEvent.Context context, Player sender) {
        throw new IllegalStateException("Packet " + getClass().getSimpleName() + " can only execute at client side.");
    }
}
