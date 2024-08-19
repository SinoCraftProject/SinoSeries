package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinobrush.utility.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FilledXuanPaperItem extends Item {
    public FilledXuanPaperItem() {
        super(new Properties()
                .sino$tab(SBRItems.SINO_BRUSH_TAB)
                .stacksTo(1));
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        var stack = super.getDefaultInstance();
        setDrawing(stack, new Drawing());
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);

        var drawing = getDrawing(stack);
        tooltip.add(Component.translatable(SBRConstants.Translation.DRAWING_AUTHOR_LABEL).append(drawing.getAuthor()));

        var date = drawing.getZonedDate();
        tooltip.add(Component.translatable(SBRConstants.Translation.DRAWING_DATE_LABEL, date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond()));

        tooltip.add(TooltipHelper.getColor(drawing.getPaperColor()));
        tooltip.add(Component
                .translatable(SBRConstants.Translation.DESCRIPTION_FILLED_XUAN_PAPER_SIZE, drawing.getWidth(), drawing.getHeight())
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        var drawing = getDrawing(stack);
        return Component.translatable(SBRConstants.Translation.DRAWING_TITLE_LABEL).append(drawing.getTitle());
    }

    public static void setDrawing(ItemStack stack, Drawing drawing) {
        stack.getOrCreateTag().put(SBRConstants.TagName.DRAWING, drawing.writeToCompound());
    }

    public static Drawing getDrawing(ItemStack stack) {
        if (stack.hasTag()) {
            var tag = stack.getTag();
            assert tag != null;

            var drawingTag = tag.getCompound(SBRConstants.TagName.DRAWING);
            return Drawing.fromTag(drawingTag);
        }

        return Drawing.EMPTY;
    }
}
