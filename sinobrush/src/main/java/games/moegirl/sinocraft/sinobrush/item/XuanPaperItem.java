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

public class XuanPaperItem extends Item implements DyeableLeatherItem {
    public XuanPaperItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor(ItemStack stack) {
        if (hasCustomColor(stack)) {
            return DyeableLeatherItem.super.getColor(stack);
        } else {
            return SBRConstants.COLOR_WHITE;
        }
    }

    public int getExpend(ItemStack stack) {
        if (stack.hasTag()) {
            var tag = stack.getTag();
            assert tag != null;

            var paper = tag.getCompound(SBRConstants.TagName.XUAN_PAPER);
            var expends = paper.getInt(SBRConstants.TagName.XUAN_PAPER_EXPENDS);
            if (expends > 0 && expends < SBRConstants.XUAN_PAPER_MAX_EXPEND) {
                return expends;
            }
        }

        return 1;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        // Todo: Expend.
    }
}
