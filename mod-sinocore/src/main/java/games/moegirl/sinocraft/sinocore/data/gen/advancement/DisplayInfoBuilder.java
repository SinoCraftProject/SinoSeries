package games.moegirl.sinocraft.sinocore.data.gen.advancement;

import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DisplayInfoBuilder {

    private ItemStack icon = ItemStack.EMPTY;
    private Component title = Component.empty();
    private Component desc = Component.empty();
    @Nullable
    private ResourceLocation background = null;
    private AdvancementType frameType = AdvancementType.GOAL;
    private boolean showToast = false;
    private boolean announceChat = false;
    private boolean hidden = false;

    public DisplayInfoBuilder() {
    }

    public DisplayInfoBuilder setBackground(@Nullable ResourceLocation background) {
        this.background = background;
        return this;
    }

    public DisplayInfoBuilder setIcon(ItemStack icon) {
        this.icon = icon;
        return this;
    }

    public DisplayInfoBuilder setIcon(ItemLike icon) {
        return setIcon(new ItemStack(icon));
    }

    public DisplayInfoBuilder setTitle(Component title) {
        this.title = title;
        return this;
    }

    public DisplayInfoBuilder setDesc(Component desc) {
        this.desc = desc;
        return this;
    }

    public DisplayInfoBuilder setFrameType(AdvancementType frameType) {
        this.frameType = frameType;
        return this;
    }

    public DisplayInfoBuilder showToast() {
        this.showToast = true;
        return this;
    }

    public DisplayInfoBuilder announceChat() {
        this.announceChat = true;
        return this;
    }

    public DisplayInfoBuilder hidden() {
        this.hidden = true;
        return this;
    }

    public DisplayInfo build() {
        return new DisplayInfo(icon, title, desc, Optional.ofNullable(background), frameType, showToast, announceChat, hidden);
    }
}
