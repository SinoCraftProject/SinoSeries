package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.utility.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InkItem extends Item implements DyeableLeatherItem {
    public InkItem() {
        super(new Properties()
                .sino$tab(SBRItems.SINO_BRUSH_TAB)
                .stacksTo(16));
    }

    @Override
    public int getColor(ItemStack stack) {
        if (stack.isEmpty() || !hasCustomColor(stack)) {
            return SBRConstants.COLOR_BLACK;
        }

        return DyeableLeatherItem.super.getColor(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        var color = getColor(stack);
        tooltipComponents.add(TooltipHelper.getColor(color));
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        var stack = new ItemStack(this);
        setColor(stack, SBRConstants.COLOR_BLACK);
        return stack;
    }
}
