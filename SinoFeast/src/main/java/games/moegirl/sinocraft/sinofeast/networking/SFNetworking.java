package games.moegirl.sinocraft.sinofeast.networking;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.networking.packet.S2CSyncFoodTastePacket;
import games.moegirl.sinocraft.sinofeast.networking.packet.S2CSyncPlayerPreferencePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class SFNetworking {
    private final SimpleChannel channel;

    private static int ID = 0;
    private static int nextId() {
        return ID++;
    }

    public SFNetworking() {
        channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(SinoFeast.MODID, "channel"),
                () -> SinoCore.VERSION,
                SinoCore.VERSION::equals,
                SinoCore.VERSION::equals
        );

        channel.registerMessage(nextId(), S2CSyncFoodTastePacket.class, S2CSyncFoodTastePacket::serialize, S2CSyncFoodTastePacket::new, S2CSyncFoodTastePacket::handle);
        channel.registerMessage(nextId(), S2CSyncPlayerPreferencePacket.class, S2CSyncPlayerPreferencePacket::serialize, S2CSyncPlayerPreferencePacket::new, S2CSyncPlayerPreferencePacket::handle);
    }

    public SimpleChannel getChannel() {
        return channel;
    }

    public void sendToServer(Object message) {
        channel.sendToServer(message);
    }

    public void sendTo(Object message, Player player) {
        channel.sendTo(message, ((ServerPlayer) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public void sendToAll(Object message) {
        channel.send(PacketDistributor.ALL.noArg(), message);
    }
}
