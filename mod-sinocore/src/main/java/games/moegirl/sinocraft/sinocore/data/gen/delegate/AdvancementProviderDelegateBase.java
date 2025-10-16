package games.moegirl.sinocraft.sinocore.data.gen.delegate;

import games.moegirl.sinocraft.sinocore.data.gen.advancement.AdvancementTree;
import games.moegirl.sinocraft.sinocore.data.gen.advancement.DisplayInfoBuilder;
import games.moegirl.sinocraft.sinocore.data.gen.advancement.IAdvancementGenerator;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AdvancementProviderDelegateBase extends ProviderDelegateBase<AdvancementProviderDelegateBase> {

    protected AdvancementProviderDelegateBase(DataProviderBuilderBase<?, ?> builder) {
        super(builder);
    }

    public abstract void addAdvancement(IAdvancementGenerator generator);

    public void addAdvancementTree(Function<Consumer<AdvancementHolder>, AdvancementTree> tree) {
        addAdvancement((registries, saver, context) -> tree.apply(saver));
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> triggerGotItems(ItemLike... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(items);
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> triggerGotItems(TagKey<Item> tag) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(tag));
    }

    public DisplayInfoBuilder display(ResourceLocation background) {
        return new DisplayInfoBuilder().setBackground(background);
    }
}
