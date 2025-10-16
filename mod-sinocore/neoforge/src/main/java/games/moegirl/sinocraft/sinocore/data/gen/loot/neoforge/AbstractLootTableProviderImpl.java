package games.moegirl.sinocraft.sinocore.data.gen.loot.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LootTableProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.NeoForgeLootTableProviderDelegate;
import games.moegirl.sinocraft.sinocore.data.gen.loot.IBlockLootTableSubProvider;
import games.moegirl.sinocraft.sinocore.data.gen.loot.IEntityLootTableSubProvider;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class AbstractLootTableProviderImpl {

    public static LootTableProviderDelegateBase createDelegate(DataGenContext context) {
        if (context instanceof NeoForgeDataGenContext impl) {
            return new NeoForgeLootTableProviderDelegate(impl);
        }
        throw new ClassCastException("Can't cast " + context + " to ForgeDataGenContextImpl at Forge Platform. " +
                "Use SinoCorePlatform#buildDataGeneratorContext to create this context. " +
                "Don't use context implemented yourself, because it contains different information in different platform");
    }

    public static IBlockLootTableSubProvider createBlockSubProvider(HolderLookup.Provider registries) {
        return new BlockLootTableSubProviderImpl(registries);
    }

    public static IEntityLootTableSubProvider createEntitySubProvider(HolderLookup.Provider registries) {
        return new EntityLootTableSubProviderImpl(registries);
    }

    static class BlockLootTableSubProviderImpl extends BlockLootSubProvider implements IBlockLootTableSubProvider {

        final static float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};

        final Set<Block> blocks = new HashSet<>();
        final Set<Block> blockView = Collections.unmodifiableSet(blocks);

        protected BlockLootTableSubProviderImpl(HolderLookup.Provider registries) {
            super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        public <T extends FunctionUserBuilder<T>> T applyExplosionDecay(ItemLike item, FunctionUserBuilder<T> builder) {
            return super.applyExplosionDecay(item, builder);
        }

        @Override
        public <T extends ConditionUserBuilder<T>> T applyExplosionCondition(ItemLike item, ConditionUserBuilder<T> builder) {
            return super.applyExplosionCondition(item, builder);
        }

        @Override
        public LootTable.Builder createSelfDropWithConditionTable(Block block, LootItemCondition.Builder conditionBuilder, LootPoolEntryContainer.Builder<?> alternativeBuilder) {
            return createSelfDropDispatchTable(block, conditionBuilder, alternativeBuilder);
        }

        @Override
        public HolderLookup.Provider getRegistries() {
            return this.registries;
        }

        @Override
        public LootItemCondition.Builder hasSilkTouch() {
            return super.hasSilkTouch();
        }

        @Override
        public LootItemCondition.Builder hasNoSilkTouch() {
            return super.doesNotHaveSilkTouch();
        }

        @Override
        public LootItemCondition.Builder hasShears() {
            return HAS_SHEARS;
        }

        @Override
        public LootItemCondition.Builder hasShearsOrSilkTouch() {
            return HAS_SHEARS.or(this.hasSilkTouch());
        }

        @Override
        public LootItemCondition.Builder hasNoShearsOrSilkTouch() {
            return hasShearsOrSilkTouch().invert();
        }

        @Override
        public float[] getNormalLeavesSaplingChanges() {
            return NORMAL_LEAVES_SAPLING_CHANCES;
        }

        @Override
        public float[] getNormalLeavesStickChanges() {
            return NORMAL_LEAVES_STICK_CHANCES;
        }

        @Override
        public LootTable.Builder createSingleItemTable(ItemLike item) {
            return super.createSingleItemTable(item);
        }

        @Override
        public LootTable.Builder createSingleItemTableWithSilkTouch(Block block, ItemLike item) {
            return super.createSingleItemTableWithSilkTouch(block, item);
        }

        @Override
        public LootTable.Builder createSingleItemTable(ItemLike item, NumberProvider count) {
            return super.createSingleItemTable(item, count);
        }

        @Override
        public LootTable.Builder createSingleItemTableWithSilkTouch(Block block, ItemLike item, NumberProvider count) {
            return super.createSingleItemTableWithSilkTouch(block, item, count);
        }

        @Override
        public LootTable.Builder createSilkTouchTable(ItemLike item) {
            return createSilkTouchOnlyTable(item);
        }

        @Override
        public LootTable.Builder createPotFlowerItemTable(ItemLike item) {
            return super.createPotFlowerItemTable(item);
        }

        @Override
        public LootTable.Builder createSlabItemTable(Block block) {
            return super.createSlabItemTable(block);
        }

        @Override
        public <T extends Comparable<T> & StringRepresentable> LootTable.Builder createSinglePropConditionTable(Block block, Property<T> property, T value) {
            return super.createSinglePropConditionTable(block, property, value);
        }

        @Override
        public LootTable.Builder createNameableBlockEntityTable(Block block) {
            return super.createNameableBlockEntityTable(block);
        }

        @Override
        public LootTable.Builder createShulkerBoxDrop(Block block) {
            return super.createShulkerBoxDrop(block);
        }

        @Override
        public LootTable.Builder createCopperOreDrops(Block block) {
            return super.createCopperOreDrops(block);
        }

        @Override
        public LootTable.Builder createLapisOreDrops(Block block) {
            return super.createLapisOreDrops(block);
        }

        @Override
        public LootTable.Builder createRedstoneOreDrops(Block block) {
            return super.createRedstoneOreDrops(block);
        }

        @Override
        public LootTable.Builder createBannerDrop(Block block) {
            return super.createBannerDrop(block);
        }

        @Override
        public LootTable.Builder createOreDrop(Block block, Item item) {
            return super.createOreDrop(block, item);
        }

        @Override
        public LootTable.Builder createMushroomBlockDrop(Block block, ItemLike item) {
            return super.createMushroomBlockDrop(block, item);
        }

        @Override
        public LootTable.Builder createBeeNestDropTable(Block block) {
            return createBeeNestDrop(block);
        }

        @Override
        public LootTable.Builder createBeeHiveDropTable(Block block) {
            return createBeeHiveDrop(block);
        }

        @Override
        public LootTable.Builder createCaveVinesDropTable(Block block) {
            return createCaveVinesDrop(block);
        }

        @Override
        public LootTable.Builder createGrassDrops(Block block) {
            return super.createGrassDrops(block);
        }

        @Override
        public LootTable.Builder createMultifaceBlockDrops(Block block, LootItemCondition.Builder builder) {
            return super.createMultifaceBlockDrops(block, builder);
        }

        @Override
        public LootTable.Builder createShearsOnlyDropTable(ItemLike item) {
            return createShearsOnlyDrop(item);
        }

        @Override
        public LootTable.Builder createLeavesDrops(Block leavesBlock, Block saplingBlock, float... chances) {
            return super.createLeavesDrops(leavesBlock, saplingBlock, chances);
        }

        @Override
        public LootTable.Builder createOakLeavesDrops(Block oakLeavesBlock, Block saplingBlock, float... chances) {
            return super.createOakLeavesDrops(oakLeavesBlock, saplingBlock, chances);
        }

        @Override
        public LootTable.Builder createMangroveLeavesDrops(Block block) {
            return super.createMangroveLeavesDrops(block);
        }

        @Override
        public LootTable.Builder createCropDrops(Block cropBlock, Item grownCropItem, Item seedsItem, LootItemCondition.Builder dropGrownCropCondition) {
            return super.createCropDrops(cropBlock, grownCropItem, seedsItem, dropGrownCropCondition);
        }

        @Override
        public LootTable.Builder createDoublePlantShearsDropTable(Block sheared) {
            return createDoublePlantShearsDrop(sheared);
        }

        @Override
        public LootTable.Builder createDoublePlantWithSeedDrops(Block block, Block sheared) {
            return super.createDoublePlantWithSeedDrops(block, sheared);
        }

        @Override
        public LootTable.Builder createCandleDrops(Block candleBlock) {
            return super.createCandleDrops(candleBlock);
        }

        @Override
        public LootTable.Builder createPetalsDrops(Block petalBlock) {
            return super.createPetalsDrops(petalBlock);
        }

        @Override
        public LootTable.Builder createCandleCakeDropsTable(Block candleCakeBlock) {
            return createCandleCakeDrops(candleCakeBlock);
        }

        @Override
        public void addNetherVinesDropTable(Block vines, Block plant) {
            super.addNetherVinesDropTable(vines, plant);
        }

        @Override
        public LootTable.Builder createDoorTable(Block doorBlock) {
            return super.createDoorTable(doorBlock);
        }

        @Override
        public void dropPottedContents(Block flowerPot) {
            super.dropPottedContents(flowerPot);
        }

        @Override
        public void otherWhenSilkTouch(Block block, Block other) {
            super.otherWhenSilkTouch(block, other);
        }

        @Override
        public void dropOther(Block block, ItemLike item) {
            super.dropOther(block, item);
        }

        @Override
        public void dropWhenSilkTouch(Block block) {
            super.dropWhenSilkTouch(block);
        }

        @Override
        public void dropSelf(Block block) {
            super.dropSelf(block);
        }

        @Override
        public void add(Block block, Function<Block, LootTable.Builder> factory) {
            super.add(block, factory);
        }

        @Override
        public void add(Block block, LootTable.Builder builder) {
            super.add(block, builder);
            blocks.add(block);
        }

        @Override
        protected void generate() {
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return blockView;
        }

        @Override
        public boolean isEmpty() {
            return blocks.isEmpty();
        }
    }

    static class EntityLootTableSubProviderImpl extends EntityLootSubProvider implements IEntityLootTableSubProvider {

        private final Set<EntityType<?>> entityTypes = new HashSet<>();
        private final Set<EntityType<?>> entityView = Collections.unmodifiableSet(entityTypes);

        protected EntityLootTableSubProviderImpl(HolderLookup.Provider registries) {
            super(FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        public HolderLookup.Provider getRegistries() {
            return this.registries;
        }

        @Override
        public EntityPredicate.Builder isEntityOnFire() {
            return EntityPredicate.Builder.entity().flags(net.minecraft.advancements.critereon.EntityFlagsPredicate.Builder.flags().setOnFire(true));
        }

        @Override
        public LootTable.Builder createTableLikeSheep(ItemLike item) {
            return createSheepTable(item);
        }

        @Override
        public void generate() {
        }

        @Override
        public boolean canHaveLootTable(EntityType<?> entity) {
            return super.canHaveLootTable(entity);
        }

        @Override
        public LootItemCondition.Builder killedByFrog() {
            return super.killedByFrog();
        }

        @Override
        public LootItemCondition.Builder killedByFrogVariant(ResourceKey<FrogVariant> frogVariant) {
            return super.killedByFrogVariant(frogVariant);
        }

        @Override
        public void add(EntityType<?> entity, LootTable.Builder builder) {
            super.add(entity, builder);
        }

        @Override
        public void add(EntityType<?> entity, ResourceKey<LootTable> lootTableLocation, LootTable.Builder builder) {
            super.add(entity, lootTableLocation, builder);
            entityTypes.add(entity);
        }

        @Override
        protected Stream<EntityType<?>> getKnownEntityTypes() {
            return entityView.stream();
        }

        @Override
        public boolean isEmpty() {
            return entityTypes.isEmpty();
        }
    }
}
