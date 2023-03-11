package games.moegirl.sinocraft.sinocore.old.woodwork;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public record SignEditOpenS2CPacket(BlockPos pos) {

    public static SignEditOpenS2CPacket read(FriendlyByteBuf buf) {
        return new SignEditOpenS2CPacket(buf.readBlockPos());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public void handleClient() {
        SignEditHelper.editSign(pos);
    }
}
