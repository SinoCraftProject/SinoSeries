package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinobrush.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinobrush.item.FilledXuanPaperItem;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.network.NetworkContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class C2SDrawingPacket implements Packet<NetworkContext> {

    private final Drawing drawing;

    public C2SDrawingPacket(Drawing drawing) {
        this.drawing = drawing;
    }

    public C2SDrawingPacket(FriendlyByteBuf buffer) {
        this.drawing = Drawing.fromTag(buffer.readNbt());
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeNbt(drawing.writeToCompound());
    }

    @Override
    public void handle(NetworkContext handler) {
        ServerPlayer sender = handler.sender();
        if (sender.containerMenu instanceof BrushMenu brushMenu) {
            Container container = brushMenu.inkAndPaperContainer;
            ItemStack paperStack = container.getItem(BrushMenu.PAPER_SLOT);
            ItemStack inkSlot = container.getItem(BrushMenu.INK_SLOT);
            if (paperStack.isEmpty()) {
                SBRNetworks.NETWORKS.send(S2CDrawingPacket.noPaper(), sender);
            } else if (inkSlot.isEmpty()) {
                SBRNetworks.NETWORKS.send(S2CDrawingPacket.noInk(), sender);
            } else if (!container.getItem(BrushMenu.DRAW_SLOT).isEmpty()) {
                SBRNetworks.NETWORKS.send(S2CDrawingPacket.hasDraw(), sender);
            } else {
                drawing.setAuthor(sender);
                paperStack.shrink(1);
                container.setItem(BrushMenu.PAPER_SLOT, paperStack);
                inkSlot.shrink(1);
                container.setItem(BrushMenu.INK_SLOT, inkSlot);
                ItemStack drawStack = new ItemStack(SBRItems.FILLED_XUAN_PAPER.get());
                FilledXuanPaperItem.setDrawing(drawStack, drawing);
                container.setItem(BrushMenu.DRAW_SLOT, drawStack);
                SBRNetworks.NETWORKS.send(S2CDrawingPacket.ok(), sender);
            }
        }
    }
}
