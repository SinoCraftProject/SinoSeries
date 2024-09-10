package games.moegirl.sinocraft.sinobrush.client;

import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.item.component.Drawing;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class FilledXuanPaperItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        if (itemStack.is(SBRItems.FILLED_XUAN_PAPER.get())) {
            var drawing = Drawing.get(itemStack);
            if (tintIndex == 0) {
                return drawing.paperColor();
            } else if (tintIndex == 1) {
                return drawing.inkColor();
            }
        }

        return -1;
    }
}
