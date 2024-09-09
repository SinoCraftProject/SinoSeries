package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.item.component.Paper;
import games.moegirl.sinocraft.sinobrush.item.component.SBRDataComponents;
import games.moegirl.sinocraft.sinobrush.utility.ColorHelper;
import games.moegirl.sinocraft.sinobrush.utility.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.List;

public class XuanPaperItem extends Item {
    public XuanPaperItem() {
        super(new Item.Properties()
                .stacksTo(64)
                .component(SBRDataComponents.PAPER.get(), new Paper())
                .component(DataComponents.DYED_COLOR, new DyedItemColor(SBRConstants.COLOR_WHITE, false))
                .sino$tab(SBRItems.SINO_BRUSH_TAB));
    }

    public static boolean canExpend(ItemStack stack) {
        return stack.is(SBRItems.XUAN_PAPER.get()) && getExpend(stack) < SBRConstants.XUAN_PAPER_MAX_EXPEND;
    }

    public static int getExpend(ItemStack stack) {
        if (!stack.is(SBRItems.XUAN_PAPER.get())) {
            return 0;
        }

        var paper = stack.get(SBRDataComponents.PAPER.get());
        if (paper == null) {
            return 0;
        }

        return paper.expands();
    }

    public static void setExpend(ItemStack stack, int expend) {
        if (!stack.is(SBRItems.XUAN_PAPER.get())) {
            return;
        }

        stack.set(SBRDataComponents.PAPER.get(), new Paper(expend));
    }

    public static void expend(ItemStack stack) {
        if (canExpend(stack)) {
            setExpend(stack, getExpend(stack) + 1);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (stack.getItem() instanceof XuanPaperItem) {
            var color = ColorHelper.getColor(stack);
            tooltipComponents.add(TooltipHelper.getColor(color));

            var expend = getExpend(stack);
            if (expend != 0) {
                tooltipComponents.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_XUAN_PAPER_EXPENDED, expend)
                        .withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
