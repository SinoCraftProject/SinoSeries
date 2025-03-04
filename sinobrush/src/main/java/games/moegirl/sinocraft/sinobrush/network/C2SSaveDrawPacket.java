package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.drawing.MutableDrawing;
import games.moegirl.sinocraft.sinobrush.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.item.XuanPaperItem;
import games.moegirl.sinocraft.sinobrush.item.component.Drawing;
import games.moegirl.sinocraft.sinobrush.stat.SBRStats;
import games.moegirl.sinocraft.sinobrush.utility.ColorHelper;
import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import games.moegirl.sinocraft.sinocore.network.context.ServerPlayNetworkContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

public record C2SSaveDrawPacket(MutableDrawing drawing, int brushSlot) implements CustomPacketPayload {

    public static final Type<C2SSaveDrawPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SinoBrush.MODID, "save_draw"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SSaveDrawPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(RegistryFriendlyByteBuf buf, C2SSaveDrawPacket packet) {
            MutableDrawing.STREAM_CODEC.encode(buf, packet.drawing());
            buf.writeVarInt(packet.brushSlot());
        }

        @Override
        public @NotNull C2SSaveDrawPacket decode(RegistryFriendlyByteBuf buf) {
            return new C2SSaveDrawPacket(MutableDrawing.STREAM_CODEC.decode(buf),
                    buf.readVarInt());
        }
    };

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(ServerPlayNetworkContext handler) {
        ServerPlayer player = handler.getPlayer();
        if (player != null && player.containerMenu instanceof BrushMenu brushMenu) {
            Container container = brushMenu.container;
            ItemStack paperStack = container.getItem(BrushMenu.PAPER_SLOT);
            ItemStack inkSlot = container.getItem(BrushMenu.INK_SLOT);
            if (paperStack.isEmpty()) {
                NetworkManager.send(S2CDrawResultPacket.noPaper(), player);
            } else if (inkSlot.isEmpty()) {
                NetworkManager.send(S2CDrawResultPacket.noInk(), player);
            } else if (!container.getItem(BrushMenu.DRAW_SLOT).isEmpty()) {
                NetworkManager.send(S2CDrawResultPacket.hasDraw(), player);
            } else if (!player.getInventory().getItem(brushSlot).is(SBRItems.BRUSH.get())) {
                NetworkManager.send(S2CDrawResultPacket.noBrush(), player);
            } else {
                drawing.setAuthor(player);
                drawing.setZonedDate(ZonedDateTime.now());

                var size = SBRConstants.DRAWING_MIN_LENGTH << XuanPaperItem.getExpend(paperStack);
                if (drawing.getWidth() != size || drawing.getHeight() != size) {
                    drawing.resize(size, size);
                }

                drawing.setPaperColor(ColorHelper.getColor(paperStack));
                drawing.setInkColor(ColorHelper.getColor(inkSlot));

                paperStack.shrink(1);
                container.setItem(BrushMenu.PAPER_SLOT, paperStack);
                inkSlot.shrink(1);
                container.setItem(BrushMenu.INK_SLOT, inkSlot);

                if (!player.isCreative()) {
                    var brush = player.getInventory().getItem(brushSlot);
                    brush.hurtAndBreak(1, player, brushSlot == Inventory.SLOT_OFFHAND ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND);
                }

                ItemStack drawStack = new ItemStack(SBRItems.FILLED_XUAN_PAPER.get());
                Drawing.set(drawStack, drawing.toImmutable());
                container.setItem(BrushMenu.DRAW_SLOT, drawStack);
                player.awardStat(SBRStats.DRAW_BY_BRUSH);
                NetworkManager.send(S2CDrawResultPacket.ok(), player);
            }
        }
    }
}
