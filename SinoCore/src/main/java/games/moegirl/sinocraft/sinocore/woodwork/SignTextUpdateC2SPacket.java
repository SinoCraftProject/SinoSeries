package games.moegirl.sinocraft.sinocore.woodwork;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record SignTextUpdateC2SPacket(BlockPos pos, String[] lines) {

    public static SignTextUpdateC2SPacket read(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        int i = buf.readVarInt();
        String[] lines = new String[4];
        Arrays.fill(lines, null);
        if ((i & 0b0001) == 0b0001) lines[0] = buf.readUtf();
        if ((i & 0b0010) == 0b0010) lines[1] = buf.readUtf();
        if ((i & 0b0100) == 0b0100) lines[2] = buf.readUtf();
        if ((i & 0b1000) == 0b1000) lines[3] = buf.readUtf();
        return new SignTextUpdateC2SPacket(pos, lines);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        int i = 0b0000;
        if (lines[0] != null) i |= 0b0001;
        if (lines[1] != null) i |= 0b0010;
        if (lines[2] != null) i |= 0b0100;
        if (lines[3] != null) i |= 0b1000;
        buf.writeVarInt(i);
        if (lines[0] != null) buf.writeUtf(lines[0]);
        if (lines[1] != null) buf.writeUtf(lines[1]);
        if (lines[2] != null) buf.writeUtf(lines[2]);
        if (lines[3] != null) buf.writeUtf(lines[3]);
    }

    public void handleServer(ServerPlayer sender) {
        ServerLevel level = sender.getLevel();
        if (level.hasChunkAt(pos) && level.getBlockEntity(pos) instanceof ModSignBlockEntity sign) {
            List<String> messages = Arrays.stream(lines)
                    .map(s -> s == null ? "" : s)
                    .map(ChatFormatting::stripFormatting)
                    .collect(Collectors.toList());
            sender.getTextFilter().processMessageBundle(messages).thenAcceptAsync(texts -> {
                sender.resetLastActionTime();
                BlockState blockstate = level.getBlockState(pos);
                for (int i = 0; i < texts.size(); ++i) {
                    FilteredText text = texts.get(i);
                    if (sender.isTextFilteringEnabled()) {
                        sign.setMessage(i, Component.literal(text.filteredOrEmpty()));
                    } else {
                        sign.setMessage(i, Component.literal(text.raw()), Component.literal(text.filteredOrEmpty()));
                    }
                }

                sign.setChanged();
                level.sendBlockUpdated(pos, blockstate, blockstate, 3);
            }, level.getServer());
            sign.setPlayerWhoMayEdit(null);
        }
    }
}
