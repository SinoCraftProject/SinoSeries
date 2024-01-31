package games.moegirl.sinocraft.sinocore.datagen.advancement;

import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class DisplayInfoBuilder {

    private ItemStack icon = ItemStack.EMPTY;
    private Component title = Component.empty();
    private Component desc = Component.empty();
    private final ResourceLocation background;
    private FrameType frameType = FrameType.GOAL;
    private boolean showToast = false;
    private boolean announceChat = false;
    private boolean hidden = false;

    public DisplayInfoBuilder(ResourceLocation background) {
        this.background = background;
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

    public DisplayInfoBuilder setFrameType(FrameType frameType) {
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
        return new DisplayInfo(icon, title, desc, background, frameType, showToast, announceChat, hidden);
    }
}
