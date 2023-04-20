package games.moegirl.sinocraft.sinocalligraphy.networking;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.networking.packet.DrawingSaveC2SPacket;
import games.moegirl.sinocraft.sinocalligraphy.networking.packet.DrawingSaveResultS2CPacket;
import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class SCANetworking {
    private final SimpleChannel channel;

    private static int ID = 0;
    private static int nextId() {
        return ID++;
    }

    public SCANetworking() {
        channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(SinoCalligraphy.MODID, "channel"),
                () -> SinoCore.VERSION,
                SinoCore.VERSION::equals,
                SinoCore.VERSION::equals
        );

        channel.registerMessage(nextId(), DrawingSaveC2SPacket.class, DrawingSaveC2SPacket::serialize, DrawingSaveC2SPacket::new, DrawingSaveC2SPacket::handle);
        channel.registerMessage(nextId(), DrawingSaveResultS2CPacket.class, DrawingSaveResultS2CPacket::serialize, DrawingSaveResultS2CPacket::new, DrawingSaveResultS2CPacket::handle);
    }

    public SimpleChannel getChannel() {
        return channel;
    }

    public void send(Object message) {
        channel.sendToServer(message);
    }

    public void send(Object message, Player player) {
        channel.sendTo(message, ((ServerPlayer) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
