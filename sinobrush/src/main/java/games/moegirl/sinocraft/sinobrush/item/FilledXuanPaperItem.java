package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.item.component.Drawing;
import games.moegirl.sinocraft.sinobrush.item.component.SBRDataComponents;
import games.moegirl.sinocraft.sinobrush.utility.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FilledXuanPaperItem extends Item {
    public FilledXuanPaperItem() {
        super(new Properties()
                .stacksTo(1)
                .component(SBRDataComponents.DRAWING.get(), new Drawing())
                .sino$tab(SBRItems.SINO_BRUSH_TAB));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        var drawing = getDrawing(stack);
        tooltipComponents.add(Component.translatable(SBRConstants.Translation.DRAWING_AUTHOR_LABEL).append(drawing.author()));

        var date = drawing.getZonedDate();
        tooltipComponents.add(Component.translatable(SBRConstants.Translation.DRAWING_DATE_LABEL, date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond()));

        tooltipComponents.add(TooltipHelper.getColor(drawing.paperColor()));
        tooltipComponents.add(Component
                .translatable(SBRConstants.Translation.DESCRIPTION_FILLED_XUAN_PAPER_SIZE, drawing.width(), drawing.height())
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        var drawing = getDrawing(stack);
        return Component.translatable(SBRConstants.Translation.DRAWING_TITLE_LABEL).append(drawing.title());
    }

    public static void setDrawing(ItemStack stack, Drawing drawing) {
        stack.set(SBRDataComponents.DRAWING.get(), drawing);
    }

    public static Drawing getDrawing(ItemStack stack) {
        if (!stack.is(SBRItems.FILLED_XUAN_PAPER.get())) {
            return new Drawing();
        }

        var drawing = stack.get(SBRDataComponents.DRAWING.get());
        if (drawing != null) {
            return drawing;
        }

        return new Drawing();
    }
}
