package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.block.BaseChestBlock;
import games.moegirl.sinocraft.sinocore.block.BaseTrappedChestBlock;
import games.moegirl.sinocraft.sinocore.blockentity.BaseChestBlockEntity;
import games.moegirl.sinocraft.sinocore.blockentity.BaseTrappedChestBlockEntity;
import games.moegirl.sinocraft.sinocore.item.TreeChestItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.RegistryManager;

import java.util.List;

public class TreeUtilities {

    // <editor-fold desc="Blocks">

    public static Block rotatedPillar(BlockBehaviour.Properties properties) {
        return new RotatedPillarBlock(properties);
    }

    public static Block normalBlock(BlockBehaviour.Properties properties) {
        return new Block(properties);
    }

    public static Block leaves(BlockBehaviour.Properties properties) {
        return new LeavesBlock(properties);
    }

    public static Block slab(BlockBehaviour.Properties properties) {
        return new SlabBlock(properties);
    }

    public static Block button(Tree tree, BlockBehaviour.Properties properties) {
        return new ButtonBlock(properties, tree.getBlockSetType(), 30, true);
    }

    public static Block door(Tree tree, BlockBehaviour.Properties properties) {
        return new DoorBlock(properties, tree.getBlockSetType());
    }

    public static Block trapdoor(Tree tree, BlockBehaviour.Properties properties) {
        return new TrapDoorBlock(properties, tree.getBlockSetType());
    }

    public static Block fence(BlockBehaviour.Properties properties) {
        return new FenceBlock(properties);
    }

    public static Block fenceGate(Tree tree, BlockBehaviour.Properties properties) {
        return new FenceGateBlock(properties, tree.getWoodType());
    }

    public static Block pressurePlate(Tree tree, BlockBehaviour.Properties properties) {
        return new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, properties, tree.getBlockSetType());
    }

    public static Block standingSign(Tree tree, BlockBehaviour.Properties properties) {
        return new StandingSignBlock(properties, tree.getWoodType());
    }

    public static Block wallSign(Tree tree, BlockBehaviour.Properties properties) {
        return new WallSignBlock(properties, tree.getWoodType());
    }

    public static Block ceilingHangingSign(Tree tree, BlockBehaviour.Properties properties) {
        return new CeilingHangingSignBlock(properties, tree.getWoodType());
    }

    public static Block wallHangingSign(Tree tree, BlockBehaviour.Properties properties) {
        return new WallHangingSignBlock(properties, tree.getWoodType());
    }

    public static Block sapling(Tree tree, BlockBehaviour.Properties properties) {
        return new SaplingBlock(tree.getGrower(), properties);
    }

    public static Block pottedSapling(Tree tree, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> tree.getBlock(TreeBlockType.SAPLING), properties);
    }

    public static Block stairs(Tree tree, BlockBehaviour.Properties properties) {
        return new StairBlock(() -> tree.getBlock(TreeBlockType.PLANKS).defaultBlockState(), properties);
    }

    public static Block chest(Tree tree, BlockBehaviour.Properties properties) {
        return new BaseChestBlock(properties, tree);
    }

    public static Block trappedChest(Tree tree, BlockBehaviour.Properties properties) {
        return new BaseTrappedChestBlock(properties, tree);
    }

    // </editor-fold>

    // <editor-fold desc="BlockEntities">

    public static BlockEntityType<?> chestEntity(Tree tree) {
        Lazy<BlockEntityType<BlockEntity>> chestEntity = Lazy.of(() -> tree.getBlockEntityType(TreeBlockType.CHEST));
        return BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier<BlockEntity>)
                        (pos, state) -> new BaseChestBlockEntity(chestEntity.get(), pos, state), tree.getBlock(TreeBlockType.CHEST))
                .build(null);
    }

    public static BlockEntityType<?> trappedChestEntity(Tree tree) {
        Lazy<BlockEntityType<BlockEntity>> chestEntity = Lazy.of(() -> tree.getBlockEntityType(TreeBlockType.TRAPPED_CHEST));
        return BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier<BlockEntity>)
                        (pos, state) -> new BaseTrappedChestBlockEntity(chestEntity.get(), pos, state), tree.getBlock(TreeBlockType.CHEST))
                .build(null);
    }

    // </editor-fold>

    // <editor-fold desc="Properties">

    public static BlockBehaviour.Properties logProp() {
        MaterialColor color = MaterialColor.PODZOL;
        MaterialColor yColor = MaterialColor.WOOD;
        return BlockBehaviour.Properties.of(Material.WOOD, axis ->
                        axis.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? yColor : color)
                .strength(2.0F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties woodProp() {
        return BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD)
                .strength(2.0F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties planksProp() {
        return BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD)
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties slabProp() {
        return planksProp();
    }

    public static BlockBehaviour.Properties leavesProp() {
        SoundType sound = SoundType.GRASS;
        return BlockBehaviour.Properties.of(Material.LEAVES)
                .strength(0.2F)
                .randomTicks()
                .sound(sound)
                .noOcclusion()
                .isValidSpawn(TreeUtilities::ocelotOrParrot)
                .isSuffocating(TreeUtilities::never)
                .isViewBlocking(TreeUtilities::never);
    }

    public static BlockBehaviour.Properties buttonProp() {
        return BlockBehaviour.Properties.of(Material.DECORATION)
                .noCollission()
                .strength(0.5F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties doorProp() {
        MaterialColor color = MaterialColor.WOOD;
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .strength(3.0F)
                .sound(SoundType.WOOD)
                .noOcclusion();
    }

    public static BlockBehaviour.Properties trapdoorProp() {
        return BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD)
                .strength(3.0F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .isValidSpawn(TreeUtilities::never);
    }

    public static BlockBehaviour.Properties stairsProp() {
        return planksProp();
    }

    public static BlockBehaviour.Properties fenceProp() {
        MaterialColor color = MaterialColor.WOOD;
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties pressurePlateProp() {
        MaterialColor color = MaterialColor.WOOD;
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .noCollission()
                .strength(0.5F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties signProp() {
        MaterialColor color = MaterialColor.WOOD;
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .noCollission()
                .strength(1.0F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties wallSignProp(Tree tree) {
        MaterialColor color = MaterialColor.WOOD;
        Block lootFrom = tree.getBlock(TreeBlockType.SIGN);
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .noCollission()
                .strength(1.0F)
                .sound(SoundType.WOOD)
                .lootFrom(() -> lootFrom);
    }

    public static BlockBehaviour.Properties hangingSignProp() {
        MaterialColor color = MaterialColor.WOOD;
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .noCollission()
                .strength(1.0F)
                .sound(SoundType.HANGING_SIGN)
                .requiredFeatures(FeatureFlags.UPDATE_1_20);
    }

    public static BlockBehaviour.Properties wallHangingSignProp(Tree tree) {
        MaterialColor color = MaterialColor.WOOD;
        Block lootFrom = tree.getBlock(TreeBlockType.HANGING_SIGN);
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .noCollission()
                .strength(1.0F)
                .sound(SoundType.HANGING_SIGN)
                .lootFrom(() -> lootFrom)
                .requiredFeatures(FeatureFlags.UPDATE_1_20);
    }

    public static BlockBehaviour.Properties saplingProp() {
        return BlockBehaviour.Properties.of(Material.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS);
    }

    public static BlockBehaviour.Properties pottedSaplingProp() {
        return BlockBehaviour.Properties.of(Material.DECORATION)
                .instabreak()
                .noOcclusion();
    }

    public static BlockBehaviour.Properties chestProp() {
        return BlockBehaviour.Properties.copy(Blocks.CHEST);
    }

    public static BlockBehaviour.Properties trappedChestProp() {
        return BlockBehaviour.Properties.copy(Blocks.TRAPPED_CHEST);
    }

    // </editor-fold>

    // <editor-fold desc="Block Properties Predicates">

    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    public static boolean ocelotOrParrot(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    // </editor-fold>

    // <editor-fold desc="Items">

    public static Item blockItem(Tree tree, TreeBlockType type) {
        return new BlockItem(tree.getBlock(type), itemProp());
    }

    public static Item doubleBlockItem(Tree tree, TreeBlockType type) {
        return new DoubleHighBlockItem(tree.getBlock(type), itemProp());
    }

    public static Item signBlockItem(Tree tree) {
        return new SignItem(itemProp(), tree.getBlock(TreeBlockType.SIGN), tree.getBlock(TreeBlockType.WALL_SIGN));
    }

    public static Item hangingSignBlockItem(Tree tree) {
        return new HangingSignItem(tree.getBlock(TreeBlockType.HANGING_SIGN), tree.getBlock(TreeBlockType.WALL_HANGING_SIGN), itemProp());
    }

    public static Item chestItem(Tree tree) {
        return new TreeChestItem(itemProp(), tree, TreeBlockType.CHEST);
    }

    public static Item trappedChestItem(Tree tree) {
        return new TreeChestItem(itemProp(), tree, TreeBlockType.TRAPPED_CHEST);
    }

    public static Item.Properties itemProp() {
        return new Item.Properties();
    }

    // Todo: qyl27: Not implemented for now.
//    public static @Nullable Item makeItem(TreeBlockType type, Item.Properties properties) {
//        return switch (type) {
//            case BOAT -> null;
//            case CHEST_BOAT -> null;
//            default -> null;
//        };
//    }


    // </editor-fold>

    // <editor-fold desc="Feature Configuration">

    public static TreeConfiguration.TreeConfigurationBuilder create(Tree tree, TrunkPlacer trunkPlacer, FoliagePlacer foliagePlacer, FeatureSize minimumSize) {
        SimpleStateProvider trunkProvider = BlockStateProvider.simple(tree.getBlock(TreeBlockType.LOG));
        SimpleStateProvider foliageProvider = BlockStateProvider.simple(tree.getBlock(TreeBlockType.LEAVES));
        return new TreeConfiguration.TreeConfigurationBuilder(trunkProvider, trunkPlacer, foliageProvider, foliagePlacer, minimumSize);
    }

    public static TreeConfiguration.TreeConfigurationBuilder copy(Tree tree, ResourceKey<ConfiguredFeature<?, ?>> copyFrom, TrunkPlacer trunkPlacer, FoliagePlacer foliagePlacer, FeatureSize minimumSize) {
        TreeConfiguration.TreeConfigurationBuilder builder = create(tree, trunkPlacer, foliagePlacer, minimumSize);
        ConfiguredFeature<?, ?> feature = RegistryManager.ACTIVE.getRegistry(Registries.CONFIGURED_FEATURE).getDelegateOrThrow(copyFrom).get();
        if (feature.config() instanceof TreeConfiguration configuration) {
            if (configuration.forceDirt) builder.forceDirt();
            if (configuration.ignoreVines) builder.ignoreVines();
            builder.decorators(List.copyOf(configuration.decorators));
            builder.dirt(configuration.dirtProvider);
        }
        return builder;
    }

    // </editor-fold>
}