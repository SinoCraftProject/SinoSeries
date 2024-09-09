package games.moegirl.sinocraft.sinobrush.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public class NormalItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        var color = itemStack.get(DataComponents.DYED_COLOR);
        if (color != null && tintIndex == 0) {
            return color.rgb();
        }

        return -1;
    }
}
