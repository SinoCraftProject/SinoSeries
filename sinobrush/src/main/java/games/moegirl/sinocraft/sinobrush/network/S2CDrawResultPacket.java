package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.gui.screen.BrushScreen;
import games.moegirl.sinocraft.sinocore.network.context.PlayNetworkContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record S2CDrawResultPacket(Status status) implements CustomPacketPayload {

    public static final Type<S2CDrawResultPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SinoBrush.MODID, "draw_result"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CDrawResultPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(RegistryFriendlyByteBuf buf, S2CDrawResultPacket packet) {
            buf.writeEnum(packet.status());
        }

        @Override
        public @NotNull S2CDrawResultPacket decode(RegistryFriendlyByteBuf buf) {
            return new S2CDrawResultPacket(buf.readEnum(Status.class));
        }
    };

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum Status {
        SUCCEED,
        FAILED_MISSING_PAPER,
        FAILED_MISSING_INK,
        FAILED_DRAW,
        FAILED_MISSING_BRUSH
    }

    public void handle(PlayNetworkContext handler) {
        Minecraft mc = Minecraft.getInstance();
        Screen screen = mc.screen;
        if (screen instanceof BrushScreen xp) {
            xp.handleServiceData(status);
        }
    }

    public static S2CDrawResultPacket ok() {
        return new S2CDrawResultPacket(Status.SUCCEED);
    }

    public static S2CDrawResultPacket noPaper() {
        return new S2CDrawResultPacket(Status.FAILED_MISSING_PAPER);
    }

    public static S2CDrawResultPacket noInk() {
        return new S2CDrawResultPacket(Status.FAILED_MISSING_INK);
    }

    public static S2CDrawResultPacket hasDraw() {
        return new S2CDrawResultPacket(Status.FAILED_DRAW);
    }

    public static S2CDrawResultPacket noBrush() {
        return new S2CDrawResultPacket(Status.FAILED_MISSING_BRUSH);
    }
}
