package games.moegirl.sinocraft.sinobrush.client;

import games.moegirl.sinocraft.sinobrush.item.FilledXuanPaperItem;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class FilledXuanPaperItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        if (itemStack.is(SBRItems.FILLED_XUAN_PAPER.get())) {
            var drawing = FilledXuanPaperItem.getDrawing(itemStack);
            if (tintIndex == 0) {
                return drawing.getPaperColor();
            } else if (tintIndex == 1) {
                return drawing.getInkColor();
            }
        }

        return -1;
    }
}
