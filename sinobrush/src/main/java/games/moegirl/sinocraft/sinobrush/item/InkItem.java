package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.utility.ColorHelper;
import games.moegirl.sinocraft.sinobrush.utility.TooltipHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.List;

public class InkItem extends Item {
    public InkItem() {
        super(new Properties()
                .component(DataComponents.DYED_COLOR, new DyedItemColor(SBRConstants.COLOR_BLACK, false))
                .sino$tab(SBRItems.SINO_BRUSH_TAB)
                .stacksTo(16));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        var color = ColorHelper.getColor(stack);
        tooltipComponents.add(TooltipHelper.getColor(color));
    }
}
