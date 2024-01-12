package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinobrush.drawing.DrawingVersion;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FilledXuanPaperItem extends XuanPaperItem {
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);

        var drawing = getDrawing(stack);
        tooltip.add(Component.translatable(SBRConstants.Translation.KEY_DRAWING_AUTHOR_LABEL).append(drawing.getAuthor()));

        var date = drawing.getZonedDate();
        tooltip.add(Component.translatable(SBRConstants.Translation.KEY_DRAWING_DATE_LABEL, date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond()));
    }

    @Override
    public Component getName(ItemStack stack) {
        var drawing = getDrawing(stack);
        return drawing.getTitle();
    }

    public Drawing getDrawing(ItemStack stack) {
        if (stack.hasTag()) {
            var tag = stack.getTag();
            assert tag != null;

            var drawingTag = tag.getCompound(SBRConstants.TagName.DRAWING);
            return Drawing.fromTag(drawingTag);
        }

        return Drawing.EMPTY;
    }
}
