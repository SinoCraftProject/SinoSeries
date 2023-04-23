package games.moegirl.sinocraft.sinocore.utility.texture;

import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;

public record ButtonEntry(String name, int x, int y, int w, int h, List<String> texture,
                          List<String> textureHover, List<String> texturePressed, List<String> textureDisable,
                          @Nullable String tooltip) {

    public Component buildTooltip() {
        return tooltip == null ? CommonComponents.EMPTY : Component.translatable(tooltip);
    }
}
