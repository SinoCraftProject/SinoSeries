package games.moegirl.sinocraft.sinocore.utility.texture;

import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

/**
 * @author luqin2007
 */
public record EditBoxEntry(String name, int x, int y, int w, int h, @Nullable String title,
                           @Nullable String hint, int maxLength, @Nullable String suggestion, String defVal, int color,
                           int uneditableColor, int fgColor, float alpha, @Nullable String tooltip, boolean bordered) {

    public Component buildTitle() {
        return title == null ? CommonComponents.EMPTY : Component.translatable(title);
    }

    @Nullable
    public Tooltip buildTooltip() {
        return tooltip == null ? null : Tooltip.create(Component.translatable(tooltip));
    }

    @Nullable
    public Component buildHint() {
        return hint == null ? null : Component.translatable(hint);
    }
}
