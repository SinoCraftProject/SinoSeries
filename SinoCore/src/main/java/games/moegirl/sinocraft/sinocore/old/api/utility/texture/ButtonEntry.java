package games.moegirl.sinocraft.sinocore.old.api.utility.texture;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nullable;

public record ButtonEntry(String name, int x, int y, int w, int h,
                          @Nullable String texture, @Nullable String textureHover, @Nullable String texturePressed,
                          @Nullable String textureDisable, @Nullable String tooltip) {

    public Component buildTooltip() {
        if (tooltip == null) {
            return TextComponent.EMPTY;
        }
        return new TranslatableComponent(tooltip);
    }
}
