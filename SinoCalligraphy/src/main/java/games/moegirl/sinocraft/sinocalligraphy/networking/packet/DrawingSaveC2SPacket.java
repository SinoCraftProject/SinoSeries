package games.moegirl.sinocraft.sinocalligraphy.networking.packet;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.drawing.DrawingDataVersion;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.ISimpleDrawing;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.traits.IHasInkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.traits.IHasPaperType;
import games.moegirl.sinocraft.sinocalligraphy.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinocalligraphy.item.InkItem;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocalligraphy.item.XuanPaperItem;
import games.moegirl.sinocraft.sinocore.network.AbstractMessagePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.time.ZonedDateTime;
import java.util.function.Supplier;

public class DrawingSaveC2SPacket extends AbstractMessagePacket {
    private final ISimpleDrawing drawing;

    public DrawingSaveC2SPacket(ISimpleDrawing drawing) {
        this.drawing = drawing;
    }

    public DrawingSaveC2SPacket(FriendlyByteBuf buf) {
        drawing = DrawingDataVersion.getLatest().create();
        drawing.deserializeNBT(buf.readNbt());
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeNbt(drawing.serializeNBT());
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            var player = context.getSender();
            if (player.containerMenu instanceof BrushMenu container) {
                var paper = container.getPaper();
                var ink = container.getInk();

                if (paper.isEmpty()) {
                    SinoCalligraphy.getInstance().getNetworking().send(DrawingSaveResultS2CPacket.noPaper(), player);
                    return;
                }

                if (ink.isEmpty()) {
                    SinoCalligraphy.getInstance().getNetworking().send(DrawingSaveResultS2CPacket.noInk(), player);
                    return;
                }

                drawing.setAuthor(player);
                drawing.setZonedDate(ZonedDateTime.now());

                if (drawing instanceof IHasPaperType hasPaperType && paper.getItem() instanceof XuanPaperItem typedPaper) {
                    hasPaperType.setPaperType(typedPaper.getType());
                }

                if (drawing instanceof IHasInkType hasInkType && ink.getItem() instanceof InkItem typedInk) {
                    hasInkType.setInkType(typedInk.getType());
                }

                ItemStack filled = new ItemStack(SCAItems.FILLED_XUAN_PAPER.get());
                var tag = filled.getOrCreateTag();
                tag.put(SCAConstants.DRAWING_TAG_NAME, drawing.serializeNBT());
                filled.setTag(tag);
                container.setResult(filled);
                SinoCalligraphy.getInstance().getNetworking().send(DrawingSaveResultS2CPacket.succeed(), player);
            } else {
                SinoCalligraphy.getInstance().getNetworking().send(DrawingSaveResultS2CPacket.unknownScreen(), player);
            }
            context.setPacketHandled(true);
        });
    }
}
