package games.moegirl.sinocraft.sinocore.datagen.delegate;

import games.moegirl.sinocraft.sinocore.datagen.advancement.AdvancementTree;
import games.moegirl.sinocraft.sinocore.datagen.advancement.DisplayInfoBuilder;
import games.moegirl.sinocraft.sinocore.datagen.advancement.IAdvancementGenerator;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AdvancementProviderDelegateBase extends ProviderDelegateBase<AdvancementProviderDelegateBase> {

    protected AdvancementProviderDelegateBase(DataProviderBuilderBase<?, ?> builder) {
        super(builder);
    }

    public abstract void addAdvancement(IAdvancementGenerator generator);

    public void addAdvancementTree(Function<Consumer<Advancement>, AdvancementTree> tree) {
        addAdvancement((registries, saver, context) -> tree.apply(saver));
    }

    public InventoryChangeTrigger.TriggerInstance triggerGotItems(ItemLike... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(items);
    }

    public InventoryChangeTrigger.TriggerInstance triggerGotItems(TagKey<Item> tag) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(new ItemPredicate(tag,
                null, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY));
    }

    public DisplayInfoBuilder display(ResourceLocation background) {
        return new DisplayInfoBuilder(background);
    }
}
