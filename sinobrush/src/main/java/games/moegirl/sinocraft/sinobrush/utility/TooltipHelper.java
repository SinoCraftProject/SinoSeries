package games.moegirl.sinocraft.sinobrush.utility;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;

public class TooltipHelper {
    public static Component getColor(int color) {
        var r = FastColor.ARGB32.red(color);
        var g = FastColor.ARGB32.green(color);
        var b = FastColor.ARGB32.blue(color);

        return Component.translatable(SBRConstants.Translation.DESCRIPTION_ITEM_COLORED, r, g, b)
                .withStyle(ChatFormatting.GRAY);
    }
}
