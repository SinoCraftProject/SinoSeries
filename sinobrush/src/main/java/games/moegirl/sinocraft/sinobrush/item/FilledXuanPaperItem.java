package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
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

//        var tag = stack.getOrCreateTag();
//        if (!tag.contains(SCAConstants.DRAWING_TAG_NAME)) {
//            return;
//        }
//
//        var nbt = tag.getCompound(SCAConstants.DRAWING_TAG_NAME);
//        var drawing = DrawingDataVersion.getLatest().fromTag(nbt);
//
//        tooltip.add(Component.translatable(SCAConstants.TRANSLATE_DRAWING_AUTHOR_KEY, drawing.getAuthor().getString()));
//
//        var date = drawing.getZonedDate();
//        tooltip.add(Component.translatable(SCAConstants.TRANSLATE_DRAWING_DATE_KEY, date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond()));
    }

//    @Override
//    public Component getName(ItemStack stack) {
//        var tag = stack.getOrCreateTag();
//        if (!tag.contains(SBRConstants.TAG_NAME_DRAWING)) {
//            return super.getName(stack);
//        }
//
//        var nbt = tag.getCompound(SBRConstants.TAG_NAME_DRAWING);
//        var drawing = DrawingDataVersion.getLatest().fromTag(nbt);
//
//        return drawing.getTitle();
//    }
}
