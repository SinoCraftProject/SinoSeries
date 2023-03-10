package games.moegirl.sinocraft.sinocore.old.api.utility;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

/**
 * A helper for create lootable table, copy from {@link BlockLoot}
 *
 * @author luqin
 */
public enum BlockLootables {

    INSTANCE;

    /**
     * A condition to check silk touch enchantment
     */
    public static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item()
            .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    /**
     * A condition to check no silk touch enchantment
     */
    public static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    /**
     * A condition to check shears tools
     */
    public static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    /**
     * A condition to check shears tools or silk touch enchantment
     */
    public static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    /**
     * A condition to check no shears tools or silk touch enchantment
     */
    public static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    /**
     * default chances array for leaves to drop sapling
     */
    public static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

    /**
     * Unwrap with explosion
     *
     * @param explosionResistant true if it is explosion resistant
     * @param function           loot builder without explosion
     * @return loot table container builder
     */
    public <T> T applyExplosionDecay(boolean explosionResistant, FunctionUserBuilder<T> function) {
        return explosionResistant ? function.unwrap() : function.apply(ApplyExplosionDecay.explosionDecay());
    }

    /**
     * Unwrap with explosion
     *
     * @param explosionResistant true if it is explosion resistant
     * @param condition          loot builder without explosion
     * @return loot table container builder
     */
    public <T> T applyExplosionCondition(boolean explosionResistant, ConditionUserBuilder<T> condition) {
        return explosionResistant ? condition.unwrap() : condition.when(ExplosionCondition.survivesExplosion());
    }

    /**
     * Drop 1 {@code item}
     *
     * @param item               item
     * @param explosionResistant true if it is explosion resistant
     * @return loot table builder
     */
    public Builder createSingleItemTable(ItemLike item, boolean explosionResistant) {
        return LootTable.lootTable().withPool(applyExplosionCondition(explosionResistant, LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(item))));
    }

    /**
     * Drop 1 {@code item}, block is not explosion resistant
     *
     * @param item item
     * @return loot table builder
     */
    public Builder createSingleItemTable(ItemLike item) {
        return createSingleItemTable(item, false);
    }

    /**
     * If the condition from {@code conditionBuilder} succeeds, drops 1 {@code block}.
     * Otherwise, drops loot specified by {@code alternativeEntryBuilder}.
     *
     * @param block                   block
     * @param conditionBuilder        condition if drop itself
     * @param alternativeEntryBuilder loot pool if condition failed
     * @return loot table builder
     */
    public Builder createSelfDropDispatchTable(Block block,
                                               LootItemCondition.Builder conditionBuilder,
                                               LootPoolEntryContainer.Builder<?> alternativeEntryBuilder) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block).when(conditionBuilder).otherwise(alternativeEntryBuilder)));
    }

    /**
     * If the block is mined with Silk Touch, drops 1 {@code block}.
     * Otherwise, drops loot specified by {@code alternativeEntryBuilder}.
     *
     * @param block                   block
     * @param alternativeEntryBuilder loot pool if not with sick touch
     * @return loot table builder
     */
    public Builder createSilkTouchDispatchTable(Block block,
                                                LootPoolEntryContainer.Builder<?> alternativeEntryBuilder) {
        return createSelfDropDispatchTable(block, HAS_SILK_TOUCH, alternativeEntryBuilder);
    }

    /**
     * If the block is mined with Shears, drops 1 {@code block}.
     * Otherwise, drops loot specified by {@code alternativeEntryBuilder}.
     *
     * @param block                   block
     * @param alternativeEntryBuilder loot pool if not with shears
     * @return loot table builder
     */
    public Builder createShearsDispatchTable(Block block, LootPoolEntryContainer.Builder<?> alternativeEntryBuilder) {
        return createSelfDropDispatchTable(block, HAS_SHEARS, alternativeEntryBuilder);
    }

    /**
     * If the block is mined either with Silk Touch or Shears, drops 1 {@code block}.
     * Otherwise, drops loot specified by {@code alternativeEntryBuilder}.
     *
     * @param block                   block
     * @param alternativeEntryBuilder loot pool if not with silk touch or shears
     * @return loot table builder
     */
    public Builder createSilkTouchOrShearsDispatchTable(Block block, LootPoolEntryContainer.Builder<?> alternativeEntryBuilder) {
        return createSelfDropDispatchTable(block, HAS_SHEARS_OR_SILK_TOUCH, alternativeEntryBuilder);
    }

    /**
     * If the block is mined with Silk Touch, drops 1 {@code block}.
     * Otherwise, drops 1 {@code alternativeItem}
     *
     * @param block              block
     * @param alternativeItem    item if no silk touch
     * @param explosionResistant true if block is explosion resistant
     * @return loot table builder
     */
    public Builder createSingleItemTableWithSilkTouch(Block block, ItemLike alternativeItem, boolean explosionResistant) {
        LootPoolSingletonContainer.Builder<?> alternative = LootItem.lootTableItem(alternativeItem);
        return createSilkTouchDispatchTable(block, applyExplosionCondition(explosionResistant, alternative));
    }

    /**
     * If the block is mined with Silk Touch, drops 1 {@code block}.
     * Otherwise, drops 1 {@code alternativeItem}, block is not explosion resistant
     *
     * @param block           block
     * @param alternativeItem item if no silk touch
     * @return loot table builder
     */
    public Builder createSingleItemTableWithSilkTouch(Block block, ItemLike alternativeItem) {
        return createSingleItemTableWithSilkTouch(block, alternativeItem, false);
    }

    /**
     * Create the builder dropped item with count
     *
     * @param item               item
     * @param count              count
     * @param explosionResistant true if block is explosion resistant
     * @return loot table builder
     */
    public Builder createSingleItemTable(ItemLike item, NumberProvider count, boolean explosionResistant) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(count));
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(applyExplosionDecay(explosionResistant, builder)));
    }

    /**
     * Create the builder dropped item with count, block is not explosion resistant
     *
     * @param item  item
     * @param count count
     * @return loot table builder
     */
    public Builder createSingleItemTable(ItemLike item, NumberProvider count) {
        return createSingleItemTable(item, count, false);
    }

    /**
     * If the block is mined with Silk Touch, drops 1 {@code block}.
     * Otherwise, drops {@code count} {@code alternativeItem}s
     *
     * @param block              block
     * @param alternativeItem    item if not with silk touch
     * @param count              alternative item count
     * @param explosionResistant true if block is explosion resistant
     * @return loot table builder
     */
    public Builder createSingleItemTableWithSilkTouch(Block block, ItemLike alternativeItem, NumberProvider count,
                                                      boolean explosionResistant) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(alternativeItem)
                .apply(SetItemCountFunction.setCount(count));
        return createSilkTouchDispatchTable(block, applyExplosionDecay(explosionResistant, builder));
    }

    /**
     * If the block is mined with Silk Touch, drops 1 {@code block}.
     * Otherwise, drops {@code count} {@code alternativeItem}s,
     * block is not explosion resistant
     *
     * @param block           block
     * @param alternativeItem item if not with silk touch
     * @param count           alternative item count
     * @return loot table builder
     */
    public Builder createSingleItemTableWithSilkTouch(Block block, ItemLike alternativeItem, NumberProvider count) {
        return createSingleItemTableWithSilkTouch(block, alternativeItem, count, false);
    }

    /**
     * Drop item only if with silk touch enchantment
     *
     * @param item item if with silk touch
     * @return loot table builder
     */
    public Builder createSilkTouchOnlyTable(ItemLike item) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .when(HAS_SILK_TOUCH)
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(item)));
    }

    /**
     * Create for potted block, drop flower pot and plant in the flower pot, like flower, sapling, ...
     *
     * @param plant              plant in flower pot
     * @param explosionResistant true if block is explosion resistant
     * @return loot table builder
     */
    public Builder createPotFlowerItemTable(ItemLike plant, boolean explosionResistant) {
        LootPool.Builder potLoot = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(Blocks.FLOWER_POT));
        LootPool.Builder plantLoot = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(plant));
        return LootTable.lootTable()
                .withPool(applyExplosionCondition(explosionResistant, potLoot))
                .withPool(applyExplosionCondition(explosionResistant, plantLoot));
    }

    /**
     * Create for potted block, drop flower pot and plant in the flower pot, like flower, sapling, ...
     * <p>block is not explosion resistant</p>
     *
     * @param plant plant in flower pot
     * @return loot table builder
     */
    public Builder createPotFlowerItemTable(ItemLike plant) {
        return createPotFlowerItemTable(plant, false);
    }

    /**
     * Create for slab block, drop two if is full block state.
     *
     * @param block              block
     * @param explosionResistant true if block is explosion resistant</p>
     * @return loot table builder
     */
    public Builder createSlabItemTable(Block block, boolean explosionResistant) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(block).apply(
                SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))));
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(applyExplosionDecay(explosionResistant, builder)));
    }

    /**
     * Create for slab block, drop two if is full block state, block is not explosion resistant
     *
     * @param block block
     * @return loot table builder
     */
    public Builder createSlabItemTable(Block block) {
        return createSlabItemTable(block, false);
    }

    /**
     * Create for a block with special property value, drop 1 block
     *
     * @param block              block
     * @param property           special property
     * @param value              property value
     * @param explosionResistant true if block is explosion resistant
     * @param <T>                property value type
     * @return loot table builder
     */
    public <T extends Comparable<T> & StringRepresentable> Builder createSinglePropConditionTable(Block block,
                                                                                                  Property<T> property,
                                                                                                  T value,
                                                                                                  boolean explosionResistant) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(block)
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value)));
        return LootTable.lootTable().withPool(applyExplosionCondition(explosionResistant, LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(builder)));
    }

    /**
     * Create for a block with special property value, drop 1 block, block is not explosion resistant
     *
     * @param block    block
     * @param property special property
     * @param value    property value
     * @param <T>      property value type
     * @return loot table builder
     */
    public <T extends Comparable<T> & StringRepresentable> Builder createSinglePropConditionTable(Block block,
                                                                                                  Property<T> property,
                                                                                                  T value) {
        return createSinglePropConditionTable(block, property, value, false);
    }

    /**
     * Create for a block, drop itself, and copy block entity name to dropped item stack name
     *
     * @param block              block
     * @param explosionResistant true if block is explosion resistant
     * @return loot table builder
     */
    public Builder createNameableBlockEntityTable(Block block, boolean explosionResistant) {
        LootItemConditionalFunction.Builder<?> builder = CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY);
        return LootTable.lootTable().withPool(applyExplosionCondition(explosionResistant, LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block).apply(builder))));
    }

    /**
     * Create for a block, drop itself, and copy block entity name to dropped item stack name,
     * block is not explosion resistant
     *
     * @param block block
     * @return loot table builder
     */
    public Builder createNameableBlockEntityTable(Block block) {
        return createNameableBlockEntityTable(block, false);
    }

    /**
     * Create drop like Shulker Box
     *
     * @param block              block
     * @param explosionResistant true if block is explosion resistant
     * @return loot table builder
     */
    public Builder createShulkerBoxDrop(Block block, boolean explosionResistant) {
        return LootTable.lootTable().withPool(applyExplosionCondition(explosionResistant, LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                .copy("Lock", "BlockEntityTag.Lock")
                                .copy("LootTable", "BlockEntityTag.LootTable")
                                .copy("LootTableSeed", "BlockEntityTag.LootTableSeed"))
                        .apply(SetContainerContents.setContents(BlockEntityType.SHULKER_BOX)
                                .withEntry(DynamicLoot.dynamicEntry(ShulkerBoxBlock.CONTENTS))))));
    }

    /**
     * Create drop like Shulker Box, block is not explosion resistant
     *
     * @param block block
     * @return loot table builder
     */
    public Builder createShulkerBoxDrop(Block block) {
        return createShulkerBoxDrop(block, false);
    }

    /**
     * Drop item as ore, drop item directly, like redstone
     * <p>Apply fortune and silk touch enchantment</p>
     *
     * @param block              block
     * @param droppedItem        dropped item
     * @param min                min count
     * @param max                max count
     * @param explosionResistant true if block is explosion resistant
     * @return loot table builder
     */
    public Builder createOreDrops(Block block, ItemLike droppedItem, int min, int max, boolean explosionResistant) {
        return createSilkTouchDispatchTable(block, applyExplosionDecay(explosionResistant,
                LootItem.lootTableItem(droppedItem)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    /**
     * Drop item as ore, drop item directly, like redstone, block is not explosion resistant
     * <p>Apply fortune and silk touch enchantment</p>
     *
     * @param block   block
     * @param oreItem dropped item
     * @param min     min count
     * @param max     max count
     * @return loot table builder
     */
    public Builder createOreDrops(Block block, ItemLike oreItem, int min, int max) {
        return createOreDrops(block, oreItem, min, max, false);
    }

    /**
     * Drop item as ore, drop one item directly, like diamond
     * <p>Apply fortune and silk touch enchantment</p>
     *
     * @param block              ore block
     * @param oreItem            dropped item
     * @param explosionResistant true if block is explosion resistant
     * @return loot table builder
     */
    public Builder createOreDrop(Block block, ItemLike oreItem, boolean explosionResistant) {
        return createSilkTouchDispatchTable(block, applyExplosionDecay(explosionResistant, LootItem.lootTableItem(oreItem)
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    /**
     * Drop item as ore, drop one item directly, like diamond, block is not explosion resistant
     * <p>Apply fortune and silk touch enchantment</p>
     *
     * @param block   ore block
     * @param oreItem dropped item
     * @return loot table builder
     */
    public Builder createOreDrop(Block block, ItemLike oreItem) {
        return createOreDrop(block, oreItem, false);
    }

    /**
     * Drop item as a banner
     * <p>Copy Patterns nbt</p>
     *
     * @param block              banner block
     * @param explosionResistant true if block is explosion resistant
     * @return loot table builder
     */
    public Builder createBannerDrop(Block block, boolean explosionResistant) {
        return LootTable.lootTable().withPool(applyExplosionCondition(explosionResistant, LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Patterns", "BlockEntityTag.Patterns")))));
    }

    /**
     * Drop item as a banner, block is not explosion resistant
     * <p>Copy Patterns nbt</p>
     *
     * @param block banner block
     * @return loot table builder
     */
    public Builder createBannerDrop(Block block) {
        return createBannerDrop(block, false);
    }

    /**
     * Create a loot for block only drop use shear
     *
     * @param item item to drop
     * @return loot table builder
     */
    public Builder createShearsOnlyDrop(ItemLike item) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(HAS_SHEARS)
                .add(LootItem.lootTableItem(item)));
    }

    /**
     * Used for all leaves, drops self with silk touch, otherwise drops the second Block param with the passed chances
     * for fortune levels, adding in sticks.
     *
     * @param leaves             leaves block
     * @param sapling            sapling block
     * @param explosionResistant true if block is explosion resistant (maybe exist resistant tree sapling?)
     * @param chances            chances array by fortune level to drop sapling
     * @return loot table builder
     */
    public Builder createLeavesDrops(Block leaves, Block sapling, boolean explosionResistant, float... chances) {
        LootItemCondition.Builder builder = BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE,
                0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F);
        LootPoolSingletonContainer.Builder<?> stick = LootItem.lootTableItem(Items.STICK)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)));
        return createSilkTouchOrShearsDispatchTable(leaves, applyExplosionCondition(explosionResistant,
                LootItem.lootTableItem(sapling))
                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances)))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
                        .add(applyExplosionDecay(explosionResistant, stick).when(builder)));
    }

    /**
     * Used for all leaves, drops self with silk touch, otherwise drops the second Block param with the default chances
     * for fortune levels, adding in sticks
     *
     * @param leaves             leaves block
     * @param sapling            sapling block
     * @param explosionResistant true if block is explosion resistant (maybe exist resistant tree sapling?)
     * @return loot table builder
     */
    public Builder createLeavesDrops(Block leaves, Block sapling, boolean explosionResistant) {
        return createLeavesDrops(leaves, sapling, explosionResistant, NORMAL_LEAVES_SAPLING_CHANCES);
    }

    /**
     * Used for all leaves, drops self with silk touch, otherwise drops the second Block param with the passed chances
     * for fortune levels, adding in sticks, block is not explosion resistant
     *
     * @param leaves  leaves block
     * @param sapling sapling block
     * @param chances chances array by fortune level to drop sapling
     * @return loot table builder
     */
    public Builder createLeavesDrops(Block leaves, Block sapling, float... chances) {
        return createLeavesDrops(leaves, sapling, false, chances);
    }

    /**
     * Used for all leaves, drops self with silk touch, otherwise drops the second Block param with the default chances
     * for fortune levels, adding in sticks, block is not explosion resistant
     *
     * @param leaves  leaves block
     * @param sapling sapling block
     * @return loot table builder
     */
    public Builder createLeavesDrops(Block leaves, Block sapling) {
        return createLeavesDrops(leaves, sapling, false, NORMAL_LEAVES_SAPLING_CHANCES);
    }

    /**
     * If {@code isGrown} fails (i.e. crop is not ready), drops 1 {@code seed}.
     * If {@code isGrown} succeeds (i.e. crop is ready), drops 1 {@code crop}, and 0-3 {@code seed} with fortune applied.
     *
     * @param crop               grown crop result item
     * @param seed               seed item
     * @param isGrown            condition check crop is grown
     * @param explosionResistant true if block is explosion resistant (maybe exist resistant crop?)
     * @return loot table builder
     */
    public Builder createCropDrops(ItemLike crop, ItemLike seed, LootItemCondition.Builder isGrown, boolean explosionResistant) {
        return applyExplosionDecay(explosionResistant, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(crop)
                        .when(isGrown)
                        .otherwise(LootItem.lootTableItem(seed))))
                .withPool(LootPool.lootPool().when(isGrown).add(LootItem.lootTableItem(seed)
                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    /**
     * If {@code isGrown} fails (i.e. crop is not ready), drops 1 {@code seed}.
     * If {@code isGrown} succeeds (i.e. crop is ready), drops 1 {@code crop}, and 0-3 {@code seed} with fortune applied,
     * block is not explosion resistant
     *
     * @param crop    grown crop result item
     * @param seed    seed item
     * @param isGrown condition check crop is grown
     * @return loot table builder
     */
    public Builder createCropDrops(ItemLike crop, ItemLike seed, LootItemCondition.Builder isGrown) {
        return createCropDrops(crop, seed, isGrown, false);
    }

    /**
     * A loot table for door
     *
     * @param door door block
     * @return loot table builder
     */
    public Builder createDoorTable(Block door) {
        return createSinglePropConditionTable(door, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }

    /**
     * A loot table with no item
     *
     * @return loot table builder
     */
    public Builder noDrop() {
        return LootTable.lootTable();
    }
}
