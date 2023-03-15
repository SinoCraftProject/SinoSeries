package games.moegirl.sinocraft.sinocore.packet;

import games.moegirl.sinocraft.sinocore.blockentity.ModSignBlockEntity;
import games.moegirl.sinocraft.sinocore.client.screen.ModSignEditScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public record SignEditOpenS2CPacket(BlockPos pos) implements Runnable {

    public static SignEditOpenS2CPacket read(FriendlyByteBuf buf) {
        return new SignEditOpenS2CPacket(buf.readBlockPos());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public void run() {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.level != null && mc.level.getBlockEntity(pos) instanceof ModSignBlockEntity sign) {
            mc.setScreen(new ModSignEditScreen(sign, mc.isTextFilteringEnabled()));
        }
    }
}
