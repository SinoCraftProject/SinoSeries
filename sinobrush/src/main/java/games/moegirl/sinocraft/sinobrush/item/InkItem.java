package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InkItem extends Item implements DyeableLeatherItem {
    public InkItem() {
        super(new Properties()
                .stacksTo(16));
    }

    @Override
    public int getColor(ItemStack stack) {
        if (hasCustomColor(stack)) {
            return DyeableLeatherItem.super.getColor(stack);
        } else {
            return SBRConstants.COLOR_BLACK;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        // Todo: Expend.
    }
}
