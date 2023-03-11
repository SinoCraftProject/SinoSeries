package games.moegirl.sinocraft.sinocore.old.utility.texture;

import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public record ButtonEntry(String name, int x, int y, int w, int h,
                          @Nullable String texture, @Nullable String textureHover, @Nullable String texturePressed,
                          @Nullable String textureDisable, @Nullable String tooltip) {

    public Component buildTooltip() {
        return tooltip == null ? Component.empty() : Component.translatable(tooltip);
    }
}
