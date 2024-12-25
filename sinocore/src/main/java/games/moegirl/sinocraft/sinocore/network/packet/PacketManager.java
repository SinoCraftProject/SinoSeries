package games.moegirl.sinocraft.sinocore.network.packet;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

class PacketManager {
    @ExpectPlatform
    static <T extends CustomPacketPayload> void register(PlayPacket<T> packet) {
        throw new AssertionError();
    }
}
