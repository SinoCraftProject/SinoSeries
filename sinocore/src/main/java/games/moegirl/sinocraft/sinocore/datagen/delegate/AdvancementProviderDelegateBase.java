package games.moegirl.sinocraft.sinocore.datagen.delegate;

import games.moegirl.sinocraft.sinocore.datagen.advancement.AdvancementTree;
import games.moegirl.sinocraft.sinocore.datagen.advancement.DisplayInfoBuilder;
import games.moegirl.sinocraft.sinocore.datagen.advancement.IAdvancementGenerator;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AdvancementProviderDelegateBase extends ProviderDelegateBase<AdvancementProviderDelegateBase> {

    protected AdvancementProviderDelegateBase(DataProviderBuilderBase<?, ?> builder) {
        super(builder);
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

    public DisplayInfoBuilder display(ResourceLocation background) {
        return new DisplayInfoBuilder(background);
    }
}
