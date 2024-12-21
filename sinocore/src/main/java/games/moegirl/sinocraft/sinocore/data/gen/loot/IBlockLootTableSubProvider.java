package games.moegirl.sinocraft.sinocore.data.gen.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.Function;

public interface IBlockLootTableSubProvider {

    HolderLookup.Provider getRegistries();

    LootItemCondition.Builder hasSilkTouch();

    LootItemCondition.Builder hasNoSilkTouch();

    LootItemCondition.Builder hasShears();

    LootItemCondition.Builder hasShearsOrSilkTouch();

    LootItemCondition.Builder hasNoShearsOrSilkTouch();

    default LootItemCondition.Builder ifBlockIs(Block block) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block);
    }

    default <T extends Comparable<T> & StringRepresentable> LootItemCondition.Builder ifBlockIs(Block block, Property<T> property, T value) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
    }

    default LootItemCondition.Builder ifBlockIs(Block block, Property<?> property, String value) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
    }

    default LootItemCondition.Builder ifBlockIs(Block block, Property<Integer> property, int value) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
    }

    default LootItemCondition.Builder ifBlockIs(Block block, Property<Boolean> property, boolean value) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
    }

    default LootItemCondition.Builder ifNot(LootItemCondition.Builder condition) {
        return InvertedLootItemCondition.invert(condition);
    }

    float[] getNormalLeavesSaplingChanges();

    float[] getNormalLeavesStickChanges();

    <T extends FunctionUserBuilder<T>> T applyExplosionDecay(ItemLike item, FunctionUserBuilder<T> builder);

    <T extends ConditionUserBuilder<T>> T applyExplosionCondition(ItemLike item, ConditionUserBuilder<T> builder);

    // rename from createSelfDropDispatchTable
    LootTable.Builder createSelfDropWithConditionTable(Block block, LootItemCondition.Builder conditionBuilder, LootPoolEntryContainer.Builder<?> alternativeBuilder);

    LootTable.Builder createSingleItemTable(ItemLike item);

    LootTable.Builder createSingleItemTable(ItemLike item, NumberProvider count);

    default LootTable.Builder createSingleItemTable(ItemLike item, int count) {
        return createSingleItemTable(item, ConstantValue.exactly(count));
    }

    default LootTable.Builder createSingleItemTable(ItemLike item, float min, float max) {
        return createSingleItemTable(item, UniformGenerator.between(min, max));
    }

    LootTable.Builder createSingleItemTableWithSilkTouch(Block block, ItemLike item);

    LootTable.Builder createSingleItemTableWithSilkTouch(Block block, ItemLike item, NumberProvider count);

    default LootTable.Builder createSingleItemTableWithSilkTouch(Block block, ItemLike item, int count) {
        return createSingleItemTableWithSilkTouch(block, item, ConstantValue.exactly(count));
    }

    default LootTable.Builder createSingleItemTableWithSilkTouch(Block block, ItemLike item, float min, float max) {
        return createSingleItemTableWithSilkTouch(block, item, UniformGenerator.between(min, max));
    }

    // rename from createSilkTouchOnlyTable
    LootTable.Builder createSilkTouchTable(ItemLike item);

    LootTable.Builder createPotFlowerItemTable(ItemLike item);

    LootTable.Builder createSlabItemTable(Block block);

    <T extends Comparable<T> & StringRepresentable> LootTable.Builder createSinglePropConditionTable(Block block, Property<T> property, T value);

    LootTable.Builder createNameableBlockEntityTable(Block block);

    LootTable.Builder createShulkerBoxDrop(Block block);

    LootTable.Builder createBannerDrop(Block block);

    LootTable.Builder createOreDrop(Block block, Item item);

    default LootTable.Builder createOreDrop(Block block, Item item, float min, float max) {
        return createSelfDropWithConditionTable(block, hasSilkTouch(),
                applyExplosionDecay(block, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                        .apply(ApplyBonusCount.addOreBonusCount(getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE)))));
    }

    LootTable.Builder createMushroomBlockDrop(Block block, ItemLike item);

    // rename from createBeeNestDrop
    LootTable.Builder createBeeNestDropTable(Block block);

    // rename from createBeeHiveDrop
    LootTable.Builder createBeeHiveDropTable(Block block);

    // rename from createCaveVinesDrop
    LootTable.Builder createCaveVinesDropTable(Block block);

    LootTable.Builder createGrassDrops(Block block);

    LootTable.Builder createMultifaceBlockDrops(Block block, LootItemCondition.Builder builder);

    LootTable.Builder createStemDrops(Block block, Item item);

    LootTable.Builder createAttachedStemDrops(Block block, Item item);

    // rename from createShearsOnlyDrop
    LootTable.Builder createShearsOnlyDropTable(ItemLike item);

    LootTable.Builder createLeavesDrops(Block leavesBlock, Block saplingBlock, float... chances);

    LootTable.Builder createOakLeavesDrops(Block oakLeavesBlock, Block saplingBlock, float... chances);

    LootTable.Builder createMangroveLeavesDrops(Block block);

    LootTable.Builder createCropDrops(Block cropBlock, Item grownCropItem, Item seedsItem, LootItemCondition.Builder dropGrownCropCondition);

    // rename from createDoublePlantShearsDrop
    LootTable.Builder createDoublePlantShearsDropTable(Block sheared);

    LootTable.Builder createDoublePlantWithSeedDrops(Block block, Block sheared);

    LootTable.Builder createCandleDrops(Block candleBlock);

    LootTable.Builder createPetalsDrops(Block petalBlock);

    LootTable.Builder createCandleCakeDropsTable(Block candleCakeBlock);

    void addNetherVinesDropTable(Block vines, Block plant);

    LootTable.Builder createDoorTable(Block doorBlock);

    void dropPottedContents(Block flowerPot);

    void otherWhenSilkTouch(Block block, Block other);

    void dropOther(Block block, ItemLike item);

    void dropWhenSilkTouch(Block block);

    void dropSelf(Block block);

    void add(Block block, LootTable.Builder builder);

    void add(Block block, Function<Block, LootTable.Builder> factory);

    default void add(Block block) {
        if (block instanceof ILootableBlock lb) {
            add(block, lb.createLootBuilder());
        } else {
            add(block, createSingleItemTable(block));
        }
    }

    boolean isEmpty();

    default BlockLootSubProvider asProvider() {
        return (BlockLootSubProvider) this;
    }
}
