package games.moegirl.sinocraft.sinobrush.client;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinocore.utility.data.DataComponentHelper;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class NormalItemColor implements ItemColor {
    private static final DyedItemColor DEFAULT = SBRConstants.DEFAULT_COLOR_WHITE;

    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        var color = DataComponentHelper.getDyedColor(itemStack, DEFAULT);
        if (tintIndex == 0) {
            return color.rgb();
        }

        return -1;
    }
}
