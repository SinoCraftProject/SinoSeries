package games.moegirl.sinocraft.sinodivination.old.util;

import games.moegirl.sinocraft.sinodivination.old.block.base.Crop;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.Supplier;

public class Lootables {

    public static LootItemCondition.Builder isGrown(Crop<?> crop) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(crop.self())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(crop.getAgeProperty(), crop.getMaxAge()));
    }

    public static LootPoolSingletonContainer.Builder<?> item(ItemLike item) {
        return LootItem.lootTableItem(item);
    }

    public static LootPoolSingletonContainer.Builder<?> item(Supplier<? extends ItemLike> item) {
        return LootItem.lootTableItem(item.get());
    }

    public static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int min, int max) {
        return LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(range(min, max)));
    }

    public static LootPoolSingletonContainer.Builder<?> item(Supplier<? extends ItemLike> item, int min, int max) {
        return LootItem.lootTableItem(item.get()).apply(SetItemCountFunction.setCount(range(min, max)));
    }

    public static NumberProvider range(int min, int max) {
        if (min == max) {
            return ConstantValue.exactly(max);
        } else {
            return UniformGenerator.between(Math.min(min, max), Math.max(min, max));
        }
    }
}
