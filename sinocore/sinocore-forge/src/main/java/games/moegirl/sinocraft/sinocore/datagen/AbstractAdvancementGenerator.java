package games.moegirl.sinocraft.sinocore.datagen;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.Optional;

public abstract class AbstractAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    protected final String modId;
    protected final ResourceLocation background;

    public AbstractAdvancementGenerator(String modId, ResourceLocation background) {
        this.modId = modId;
        this.background = background;
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> triggerGotItems(ItemLike... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(items);
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> triggerGotItems(TagKey<Item> tag) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(new ItemPredicate(Optional.of(tag),
                Optional.empty(), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                List.of(), List.of(), Optional.empty(), Optional.empty()));
    }

    public String moddedId(String id) {
        return modId + ":" + id;
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType) {
        return display(item, name, desc, frameType, false);
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType,
                               boolean showToast) {
        return display(item, name, desc, frameType, showToast, true);
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType,
                               boolean showToast, boolean announceToChat) {
        return display(item, name, desc, frameType, showToast, announceToChat, false);
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType,
                               boolean showToast, boolean announceToChat, boolean hidden) {
        return new DisplayInfo(new ItemStack(item), Component.translatable(name), Component.translatable(desc),
                background, frameType, showToast, announceToChat, hidden);
    }
}
