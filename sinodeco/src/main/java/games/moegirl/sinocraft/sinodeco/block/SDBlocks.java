package games.moegirl.sinocraft.sinodeco.block;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import games.moegirl.sinocraft.sinodeco.block.abstracted.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class SDBlocks {
    public static final IRegistry<Block> BLOCKS = RegistryManager.obtain(SinoDeco.MODID, Registries.BLOCK);

    public static void register() {
        BLOCKS.register();
    }

//    public static final IRegRef<Block> WOODEN_TABLE = BLOCKS.register("wooden_table", WoodenTableBlock::new);
    public static final IRegRef<Block> ACACIA_WOOD_TABLE = BLOCKS.register("acacia_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> BAMBOO_WOOD_TABLE = BLOCKS.register("bamboo_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> BIRCH_WOOD_TABLE = BLOCKS.register("birch_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> CHERRY_WOOD_TABLE = BLOCKS.register("cherry_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> CRIMSON_WOOD_TABLE = BLOCKS.register("crimson_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> DARK_OAK_WOOD_TABLE = BLOCKS.register("dark_oak_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> JUNGLE_WOOD_TABLE = BLOCKS.register("jungle_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> MANGROVE_WOOD_TABLE = BLOCKS.register("mangrove_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> OAK_WOOD_TABLE = BLOCKS.register("oak_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> SPRUCE_WOOD_TABLE = BLOCKS.register("spruce_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> WARPED_WOOD_TABLE = BLOCKS.register("warped_wood_table", WoodenTableBlock::new);
    public static final IRegRef<Block> PEACH_WOOD_TABLE = BLOCKS.register("peach_wood_table", WoodenTableBlock::new);

    public static final IRegRef<Block> ACACIA_WOOD_ARMCHAIR = BLOCKS.register("acacia_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> BAMBOO_WOOD_ARMCHAIR = BLOCKS.register("bamboo_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> BIRCH_WOOD_ARMCHAIR = BLOCKS.register("birch_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> CHERRY_WOOD_ARMCHAIR = BLOCKS.register("cherry_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> CRIMSON_WOOD_ARMCHAIR = BLOCKS.register("crimson_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> DARK_OAK_WOOD_ARMCHAIR = BLOCKS.register("dark_oak_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> JUNGLE_WOOD_ARMCHAIR = BLOCKS.register("jungle_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> MANGROVE_WOOD_ARMCHAIR = BLOCKS.register("mangrove_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> OAK_WOOD_ARMCHAIR = BLOCKS.register("oak_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> SPRUCE_WOOD_ARMCHAIR = BLOCKS.register("spruce_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> WARPED_WOOD_ARMCHAIR = BLOCKS.register("warped_wood_armchair", WoodenArmchairBlock::new);
    public static final IRegRef<Block> PEACH_WOOD_ARMCHAIR = BLOCKS.register("peach_wood_armchair", WoodenArmchairBlock::new);

    public static final IRegRef<Block> MARBLE_BLOCK = BLOCKS.register("marble_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).instrument(NoteBlockInstrument.HARP).requiresCorrectToolForDrops().strength(0.8F)));
    public static final IRegRef<Block> CHISELED_MARBLE = BLOCKS.register("chiseled_marble", () -> new Block(BlockBehaviour.Properties.ofFullCopy(MARBLE_BLOCK.get())));
    public static final IRegRef<RotatedPillarBlock> MARBLE_PILLAR = BLOCKS.register("marble_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(MARBLE_BLOCK.get())));
    public static final IRegRef<ModStairBlock> MARBLE_STAIRS = BLOCKS.register("marble_stairs", () -> new ModStairBlock(MARBLE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(MARBLE_BLOCK.get())));
    public static final IRegRef<SlabBlock> MARBLE_SLAB = BLOCKS.register("marble_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MARBLE_BLOCK.get()).strength(2.0F, 6.0F)));
    public static final IRegRef<Block> SMOOTH_MARBLE = BLOCKS.register("smooth_marble", () -> new Block(BlockBehaviour.Properties.ofFullCopy(MARBLE_BLOCK.get()).strength(2.0F, 6.0F)));
    public static final IRegRef<ModStairBlock> SMOOTH_MARBLE_STAIRS = BLOCKS.register("smooth_marble_stairs", () -> new ModStairBlock(SMOOTH_MARBLE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SMOOTH_MARBLE.get())));
    public static final IRegRef<SlabBlock> SMOOTH_MARBLE_SLAB = BLOCKS.register("smooth_marble_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SMOOTH_MARBLE.get())));
    public static final IRegRef<Block> MARBLE_BRICKS = BLOCKS.register("marble_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(MARBLE_BLOCK.get())));
    public static final IRegRef<Block> MARBLE_BALUSTRADE = BLOCKS.register("marble_balustrade", () -> new MarbleBalustradeBlock(BlockBehaviour.Properties.ofFullCopy(MARBLE_BLOCK.get()).forceSolidOn()));

    private static final BlockSetType PEACH_BLOCK_SET_TYPE = new BlockSetType("peach");
    private static final WoodType PEACH_WOOD_TYPE = new WoodType("peach", PEACH_BLOCK_SET_TYPE);
    public static final IRegRef<Block> PEACH_LEAVES = BLOCKS.register("peach_leaves", () -> new LeavesBlock(leavesProps()));    // Todo: texture
    public static final IRegRef<Block> PEACH_SAPLING = BLOCKS.register("peach_sapling", () -> new Block(saplingProps()));    // Todo: TreeGrower and texture
    public static final IRegRef<RotatedPillarBlock> PEACH_LOG = BLOCKS.register("peach_log", () -> log(MapColor.TERRACOTTA_PINK, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final IRegRef<Block> PEACH_WOOD = BLOCKS.register("peach_wood", () -> new Block(logProps().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final IRegRef<RotatedPillarBlock> STRIPPED_PEACH_LOG = BLOCKS.register("stripped_peach_log", () -> log(MapColor.TERRACOTTA_PINK, MapColor.TERRACOTTA_PINK));
    public static final IRegRef<Block> STRIPPED_PEACH_WOOD = BLOCKS.register("stripped_peach_wood", () -> new Block(logProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<Block> PEACH_PLANKS = BLOCKS.register("peach_planks", () -> new Block(plankProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<SDStairBlock> PEACH_STAIRS = BLOCKS.register("peach_stairs", () -> new SDStairBlock(PEACH_PLANKS.get().defaultBlockState(), plankProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<SlabBlock> PEACH_SLAB = BLOCKS.register("peach_slab", () -> new SlabBlock(plankProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<FenceBlock> PEACH_FENCE = BLOCKS.register("peach_fence", () -> new FenceBlock(fenceProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<FenceGateBlock> PEACH_FENCE_GATE = BLOCKS.register("peach_fence_gate", () -> new FenceGateBlock(PEACH_WOOD_TYPE, fenceProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<SDDoorBlock> PEACH_DOOR = BLOCKS.register("peach_door", () -> new SDDoorBlock(PEACH_BLOCK_SET_TYPE, doorProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<SDTrapdoorBlock> PEACH_TRAPDOOR = BLOCKS.register("peach_trapdoor", () -> new SDTrapdoorBlock(PEACH_BLOCK_SET_TYPE, trapdoorProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<SDPressurePlateBlock> PEACH_PRESSURE_PLATE = BLOCKS.register("peach_pressure_plate", () -> new SDPressurePlateBlock(PEACH_BLOCK_SET_TYPE, pressurePlateProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<SDButtonBlock> PEACH_BUTTON = BLOCKS.register("peach_button", () -> new SDButtonBlock(PEACH_BLOCK_SET_TYPE, 15, buttonProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<StandingSignBlock> PEACH_SIGN = BLOCKS.register("peach_sign", () -> new StandingSignBlock(PEACH_WOOD_TYPE, signProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<WallSignBlock> PEACH_WALL_SIGN = BLOCKS.register("peach_wall_sign", () -> new WallSignBlock(PEACH_WOOD_TYPE, wallSignProps(PEACH_SIGN.get()).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<CeilingHangingSignBlock> PEACH_HANGING_SIGN = BLOCKS.register("peach_hanging_sign", () -> new CeilingHangingSignBlock(PEACH_WOOD_TYPE, signProps().mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<WallHangingSignBlock> PEACH_WALL_HANGING_SIGN = BLOCKS.register("peach_wall_hanging_sign", () -> new WallHangingSignBlock(PEACH_WOOD_TYPE, wallSignProps(PEACH_HANGING_SIGN.get()).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final IRegRef<Block> PEACH_CHEST = BLOCKS.register("peach_chest", () -> new Block(chestProps().mapColor(MapColor.TERRACOTTA_PINK)));  // Todo: Chest block

    private static RotatedPillarBlock log(MapColor topMapColor, MapColor sideMapColor) {
        return new RotatedPillarBlock(logProps()
                .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor));
    }

    private static BlockBehaviour.Properties logProps() {
        return BlockBehaviour.Properties.of()
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties leavesProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isValidSpawn(SDBlocks::ocelotOrParrot)
                .isSuffocating(SDBlocks::never)
                .isViewBlocking(SDBlocks::never)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor(SDBlocks::never);
    }

    private static BlockBehaviour.Properties saplingProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties plankProps() {
        return logProps().explosionResistance(3.0F);
    }

    private static BlockBehaviour.Properties fenceProps() {
        return plankProps().forceSolidOn();
    }

    private static BlockBehaviour.Properties buttonProps() {
        return BlockBehaviour.Properties.of()
                .noCollission()
                .strength(0.5F)
                .pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties pressurePlateProps() {
        return BlockBehaviour.Properties.of()
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .noCollission()
                .strength(0.5F)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties signProps() {
        return BlockBehaviour.Properties.of()
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .noCollission()
                .strength(1.0F)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties wallSignProps(Block block) {
        return signProps().dropsLike(block);
    }

    private static BlockBehaviour.Properties doorProps() {
        return BlockBehaviour.Properties.of()
                .instrument(NoteBlockInstrument.BASS)
                .strength(3.0F)
                .noOcclusion()
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties trapdoorProps() {
        return BlockBehaviour.Properties.of()
                .instrument(NoteBlockInstrument.BASS)
                .strength(3.0F)
                .noOcclusion()
                .isValidSpawn(SDBlocks::never)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties chestProps() {
        return BlockBehaviour.Properties.of()
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.5F)
                .sound(SoundType.WOOD)
                .ignitedByLava();
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }
}
