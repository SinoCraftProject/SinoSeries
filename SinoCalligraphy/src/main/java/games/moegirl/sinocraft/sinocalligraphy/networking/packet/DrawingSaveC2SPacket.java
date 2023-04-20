package games.moegirl.sinocraft.sinocalligraphy.networking.packet;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.ISimpleDrawing;
import games.moegirl.sinocraft.sinocalligraphy.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.networking.AbstractMessagePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrawingSaveC2SPacket extends AbstractMessagePacket {
    private CompoundTag drawing;

    public DrawingSaveC2SPacket(ISimpleDrawing drawing) {
        this.drawing = drawing.serializeNBT();
    }

    public DrawingSaveC2SPacket(FriendlyByteBuf buf) {
        drawing = buf.readNbt();
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeNbt(drawing);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            if (sender.containerMenu instanceof BrushMenu container) {
                if (container.getPaper().isEmpty()) {
                    SinoCalligraphy.getInstance().getNetworking().send(DrawingSaveResultS2CPacket.noPaper(), sender);
                    return;
                }

                if (container.getInk().isEmpty()) {
                    SinoCalligraphy.getInstance().getNetworking().send(DrawingSaveResultS2CPacket.noInk(), sender);
                    return;
                }

                ItemStack filled = new ItemStack(SCAItems.FILLED_XUAN_PAPER.get());
                var tag = filled.getOrCreateTag();
                tag.put(SCAConstants.DRAWING_TAG_NAME, drawing);
                filled.setTag(tag);
                container.setResult(filled);
                SinoCalligraphy.getInstance().getNetworking().send(DrawingSaveResultS2CPacket.succeed(), sender);
            } else {
                SinoCalligraphy.getInstance().getNetworking().send(DrawingSaveResultS2CPacket.unknownScreen(), sender);
            }
            context.setPacketHandled(true);
        });
    }
}
