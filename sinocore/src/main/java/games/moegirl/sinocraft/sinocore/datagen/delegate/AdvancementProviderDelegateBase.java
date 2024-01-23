package games.moegirl.sinocraft.sinocore.datagen.delegate;

import games.moegirl.sinocraft.sinocore.datagen.advancement.AdvancementTree;
import games.moegirl.sinocraft.sinocore.datagen.advancement.IAdvancementGenerator;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AdvancementProviderDelegateBase extends ProviderDelegateBase<AdvancementProviderDelegateBase> {

    protected AdvancementProviderDelegateBase(String name, Supplier<DataProvider> provider) {
        super(name, provider);
    }

    public abstract void addAdvancement(IAdvancementGenerator generator);

    public void addAdvancement(Consumer<AdvancementTree> advancementBuilder) {
        addAdvancement((registries, saver, helper) -> advancementBuilder.accept(new AdvancementTree(saver)));
    }


    public Criterion<InventoryChangeTrigger.TriggerInstance> triggerGotItems(ItemLike... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(items);
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> triggerGotItems(TagKey<Item> tag) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(new ItemPredicate(Optional.of(tag),
                Optional.empty(), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                List.of(), List.of(), Optional.empty(), Optional.empty()));
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType, ResourceLocation background) {
        return display(item, name, desc, frameType, background, false);
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType, ResourceLocation background,
                               boolean showToast) {
        return display(item, name, desc, frameType, background, showToast, true);
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType, ResourceLocation background,
                               boolean showToast, boolean announceToChat) {
        return display(item, name, desc, frameType, background, showToast, announceToChat, false);
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType, ResourceLocation background,
                               boolean showToast, boolean announceToChat, boolean hidden) {
        return new DisplayInfo(new ItemStack(item), Component.translatable(name), Component.translatable(desc),
                background, frameType, showToast, announceToChat, hidden);
    }
}
