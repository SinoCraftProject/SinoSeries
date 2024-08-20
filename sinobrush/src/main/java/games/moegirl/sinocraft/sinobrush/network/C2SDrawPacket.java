package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinobrush.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinobrush.item.FilledXuanPaperItem;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.item.XuanPaperItem;
import games.moegirl.sinocraft.sinobrush.stat.SBRStats;
import games.moegirl.sinocraft.sinocore.network.NetworkContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.time.ZonedDateTime;

public class C2SDrawPacket implements Packet<NetworkContext> {

    private final Drawing drawing;
    private final int brushSlot;

    public C2SDrawPacket(Drawing drawing, int brushSlot) {
        this.drawing = drawing;
        this.brushSlot = brushSlot;
    }

    public C2SDrawPacket(FriendlyByteBuf buffer) {
        this.drawing = Drawing.fromTag(buffer.readNbt());
        this.brushSlot = buffer.readVarInt();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeNbt(drawing.writeToCompound());
        buffer.writeVarInt(brushSlot);
    }

    @Override
    public void handle(NetworkContext handler) {
        ServerPlayer player = handler.sender();
        if (player.containerMenu instanceof BrushMenu brushMenu) {
            Container container = brushMenu.container;
            ItemStack paperStack = container.getItem(BrushMenu.PAPER_SLOT);
            ItemStack inkSlot = container.getItem(BrushMenu.INK_SLOT);
            if (paperStack.isEmpty()) {
                SBRNetworks.NETWORKS.send(S2CDrawResultPacket.noPaper(), player);
            } else if (inkSlot.isEmpty()) {
                SBRNetworks.NETWORKS.send(S2CDrawResultPacket.noInk(), player);
            } else if (!container.getItem(BrushMenu.DRAW_SLOT).isEmpty()) {
                SBRNetworks.NETWORKS.send(S2CDrawResultPacket.hasDraw(), player);
            } else if (!player.getInventory().getItem(brushSlot).is(SBRItems.BRUSH.get())) {
                SBRNetworks.NETWORKS.send(S2CDrawResultPacket.noBrush(), player);
            } else {
                drawing.setAuthor(player);
                drawing.setZonedDate(ZonedDateTime.now());

                var size = SBRConstants.DRAWING_MIN_LENGTH << XuanPaperItem.getExpend(paperStack);
                if (drawing.getWidth() != size || drawing.getHeight() != size) {
                    drawing.resize(size, size);
                }

                drawing.setPaperColor(SBRItems.XUAN_PAPER.get().getColor(paperStack));
                drawing.setInkColor(SBRItems.INK_BOTTLE.get().getColor(inkSlot));

                paperStack.shrink(1);
                container.setItem(BrushMenu.PAPER_SLOT, paperStack);
                inkSlot.shrink(1);
                container.setItem(BrushMenu.INK_SLOT, inkSlot);

                if (!player.isCreative()) {
                    var brush = player.getInventory().getItem(brushSlot);
                    brush.hurtAndBreak(1, player, entity -> {});
                }

                ItemStack drawStack = new ItemStack(SBRItems.FILLED_XUAN_PAPER.get());
                FilledXuanPaperItem.setDrawing(drawStack, drawing);
                container.setItem(BrushMenu.DRAW_SLOT, drawStack);
                player.awardStat(SBRStats.DRAW_BY_BRUSH);
                SBRNetworks.NETWORKS.send(S2CDrawResultPacket.ok(), player);
            }
        }
    }
}
