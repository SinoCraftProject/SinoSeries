package games.moegirl.sinocraft.sinocalligraphy.networking;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.networking.packet.*;
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
        channel.registerMessage(nextId(), DrawingEnableCanvasS2CPacket.class, DrawingEnableCanvasS2CPacket::serialize, DrawingEnableCanvasS2CPacket::new, DrawingEnableCanvasS2CPacket::handle);
        channel.registerMessage(nextId(), DrawingDisableCanvasS2CPacket.class, DrawingDisableCanvasS2CPacket::serialize, DrawingDisableCanvasS2CPacket::new, DrawingDisableCanvasS2CPacket::handle);
        channel.registerMessage(nextId(), DrawingClearCanvasS2CPacket.class, DrawingClearCanvasS2CPacket::serialize, DrawingClearCanvasS2CPacket::new, DrawingClearCanvasS2CPacket::handle);
    }

    public SimpleChannel getChannel() {
        return channel;
    }

    public void sendToServer(Object message) {
        channel.sendToServer(message);
    }

    public void send(Object message, Player player) {
        channel.sendTo(message, ((ServerPlayer) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
