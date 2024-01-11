package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class XuanPaperItem extends Item implements DyeableLeatherItem {
    public XuanPaperItem() {
        super(new Properties()
                .stacksTo(1));
    }

    @Override
    public int getColor(ItemStack stack) {
        if (hasCustomColor(stack)) {
            return DyeableLeatherItem.super.getColor(stack);
        } else {
            return SBRConstants.COLOR_WHITE;
        }
    }
}
