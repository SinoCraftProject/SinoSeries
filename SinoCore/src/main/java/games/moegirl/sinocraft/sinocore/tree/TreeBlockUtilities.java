package games.moegirl.sinocraft.sinocore.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.List;

public class TreeBlockUtilities {

    // <editor-fold desc="Blocks">

    public static Block makeGeneralBlock(TreeBlockType type, BlockSetType setType, BlockBehaviour.Properties properties) {
        return switch (type) {
            case LOG, LOG_WOOD, STRIPPED_LOG, STRIPPED_LOG_WOOD -> new RotatedPillarBlock(properties);
            case PLANKS -> new Block(properties);
            case LEAVES -> new LeavesBlock(properties);
            case SLAB -> new SlabBlock(properties);
            case BUTTON -> new ButtonBlock(properties, setType, 30, true);
            case DOOR -> new DoorBlock(properties, setType);
            case TRAPDOOR -> new TrapDoorBlock(properties, setType);
            case FENCE -> new FenceBlock(properties);
            case FENCE_GATE -> new FenceGateBlock(properties, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);
            case PRESSURE_PLATE -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, properties, setType);
            default -> throw new IllegalArgumentException("Unknown tree block type " + type);
        };
    }

    public static Block makeSignBlock(TreeBlockType type, BlockBehaviour.Properties properties, WoodType woodType) {
        return switch (type) {
            case SIGN -> new StandingSignBlock(properties, woodType);
            case WALL_SIGN -> new WallSignBlock(properties, woodType);
            case HANGING_SIGN -> new CeilingHangingSignBlock(properties, woodType);
            case WALL_HANGING_SIGN -> new WallHangingSignBlock(properties, woodType);
            default -> throw new IllegalArgumentException("Block type " + type + " is not a sign block");
        };
    }

    public static Block sapling(AbstractTreeGrower grower, BlockBehaviour.Properties properties) {
        return new SaplingBlock(grower, properties);
    }

    public static Block pottedSapling(SaplingBlock sapling, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> sapling, properties);
    }

    public static Block stairs(Block block) {
        return new StairBlock(block::defaultBlockState, stairsProp(block));
    }

    public static Block stairs(Block block, BlockBehaviour.Properties properties) {
        return new StairBlock(block::defaultBlockState, properties);
    }

    // </editor-fold>

    // <editor-fold desc="Properties">

    public static BlockBehaviour.Properties logProp() {
        return logProp(MaterialColor.PODZOL, MaterialColor.WOOD);
    }

    public static BlockBehaviour.Properties logProp(MaterialColor color) {
        return logProp(color, MaterialColor.WOOD);
    }

    public static BlockBehaviour.Properties logProp(MaterialColor color, MaterialColor yColor) {
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
        return leavesProp(SoundType.GRASS);
    }

    public static BlockBehaviour.Properties leavesProp(SoundType sound) {
        return BlockBehaviour.Properties.of(Material.LEAVES)
                .strength(0.2F)
                .randomTicks()
                .sound(sound)
                .noOcclusion()
                .isValidSpawn(TreeBlockUtilities::ocelotOrParrot)
                .isSuffocating(TreeBlockUtilities::never)
                .isViewBlocking(TreeBlockUtilities::never);
    }

    public static BlockBehaviour.Properties buttonProp() {
        return BlockBehaviour.Properties.of(Material.DECORATION)
                .noCollission()
                .strength(0.5F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties doorProp() {
        return doorProp(MaterialColor.WOOD);
    }

    public static BlockBehaviour.Properties doorProp(MaterialColor color) {
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
                .isValidSpawn(TreeBlockUtilities::never);
    }

    public static BlockBehaviour.Properties stairsProp(Block block) {
        return BlockBehaviour.Properties.copy(block);
    }

    public static BlockBehaviour.Properties fenceProp() {
        return fenceProp(MaterialColor.WOOD);
    }

    public static BlockBehaviour.Properties fenceProp(MaterialColor color) {
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties pressurePlateProp() {
        return pressurePlateProp(MaterialColor.WOOD);
    }

    public static BlockBehaviour.Properties pressurePlateProp(MaterialColor color) {
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .noCollission()
                .strength(0.5F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties signProp() {
        return signProp(MaterialColor.WOOD);
    }

    public static BlockBehaviour.Properties signProp(MaterialColor color) {
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .noCollission()
                .strength(1.0F)
                .sound(SoundType.WOOD);
    }

    public static BlockBehaviour.Properties wallSignProp(Block lootFrom) {
        return wallSignProp(MaterialColor.WOOD, lootFrom);
    }

    public static BlockBehaviour.Properties wallSignProp(MaterialColor color, Block lootFrom) {
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .noCollission()
                .strength(1.0F)
                .sound(SoundType.WOOD)
                .lootFrom(() -> lootFrom);
    }

    public static BlockBehaviour.Properties hangingSignProp() {
        return hangingSignProp(MaterialColor.WOOD);
    }

    public static BlockBehaviour.Properties hangingSignProp(MaterialColor color) {
        return BlockBehaviour.Properties.of(Material.WOOD, color)
                .noCollission()
                .strength(1.0F)
                .sound(SoundType.HANGING_SIGN)
                .requiredFeatures(FeatureFlags.UPDATE_1_20);
    }

    public static BlockBehaviour.Properties wallHangingSignProp(Block lootFrom) {
        return wallHangingSignProp(MaterialColor.WOOD, lootFrom);
    }

    public static BlockBehaviour.Properties wallHangingSignProp(MaterialColor color, Block lootFrom) {
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

    // </editor-fold>

    // <editor-fold desc="Block Properties Predicates">

    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    public static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }

    public static boolean ocelotOrParrot(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    // </editor-fold>

    // <editor-fold desc="Items">

    public static Item blockItem(Block block) {
        return blockItem(block, itemProp());
    }

    public static Item blockItem(Block block, Item.Properties properties) {
        return new BlockItem(block, properties);
    }

    public static Item doubleBlockItem(Block block) {
        return doubleBlockItem(block, itemProp());
    }

    public static Item signBlockItem(Block standingBlock, Block wallBlock) {
        return new SignItem(itemProp(), standingBlock, wallBlock);
    }

    public static Item hangingSignBlockItem(Block hangingBlock, Block wallBlock) {
        return new HangingSignItem(hangingBlock, wallBlock, itemProp());
    }

    public static Item doubleBlockItem(Block block, Item.Properties properties) {
        return new DoubleHighBlockItem(block, properties);
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

    // <editor-fold desc="Tabs">

    public static List<CreativeModeTab> getDefaultTabs(TreeBlockType type) {
        return switch (type) {
            case LOG -> List.of(CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
            case LOG_WOOD, STRIPPED_LOG, STRIPPED_LOG_WOOD, PLANKS, SLAB, STAIRS, FENCE -> List.of(CreativeModeTabs.BUILDING_BLOCKS);
            case LEAVES, SAPLING -> List.of(CreativeModeTabs.NATURAL_BLOCKS);
            case DOOR, TRAPDOOR, PRESSURE_PLATE, BUTTON, FENCE_GATE -> List.of(CreativeModeTabs.REDSTONE_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
            case SIGN, WALL_SIGN, HANGING_SIGN, WALL_HANGING_SIGN -> List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS);
            case BOAT -> List.of(CreativeModeTabs.TOOLS_AND_UTILITIES);
            case CHEST_BOAT -> List.of(CreativeModeTabs.REDSTONE_BLOCKS, CreativeModeTabs.TOOLS_AND_UTILITIES);
            default -> List.of();
        };
    }

    // </editor-fold>
}
