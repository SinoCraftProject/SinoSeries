package games.moegirl.sinocraft.sinocalligraphy.item;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class XuanPaperItem extends Item implements DyeableLeatherItem {
    public XuanPaperItem() {
        super(new Properties()
                .stacksTo(1)
                .setNoRepair());
    }

    @Override
    public int getColor(ItemStack stack) {
        if (hasCustomColor(stack)) {
            return DyeableLeatherItem.super.getColor(stack);
        } else {
            return SCAConstants.COLOR_WHITE;
        }
    }
}
