package games.moegirl.sinocraft.sinodeco.block;

import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.function.Supplier;

public class SDBlocks {
    public static final IRegistry<Block> BLOCKS = RegistryManager.obtain(SinoDeco.MODID, Registries.BLOCK);

    public static void register() {
        BLOCKS.register();
    }

    public static final Supplier<Block> WOODEN_TABLE = BLOCKS.register("wooden_table", WoodDeskBlock::new);

    public static final Supplier<Block> MARBLE_BLOCK = BLOCKS.register("marble_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).instrument(NoteBlockInstrument.HARP).requiresCorrectToolForDrops().strength(0.8F)));
    public static final Supplier<Block> CHISELED_MARBLE = BLOCKS.register("chiseled_marble", () -> new Block(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final Supplier<RotatedPillarBlock> MARBLE_PILLAR = BLOCKS.register("marble_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final Supplier<ModStairBlock> MARBLE_STAIRS = BLOCKS.register("marble_stairs", () -> new ModStairBlock(MARBLE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final Supplier<SlabBlock> MARBLE_SLAB = BLOCKS.register("marble_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> SMOOTH_MARBLE = BLOCKS.register("smooth_marble", () -> new Block(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get()).strength(2.0F, 6.0F)));
    public static final Supplier<ModStairBlock> SMOOTH_MARBLE_STAIRS = BLOCKS.register("smooth_marble_stairs", () -> new ModStairBlock(SMOOTH_MARBLE.get().defaultBlockState(), BlockBehaviour.Properties.copy(SMOOTH_MARBLE.get())));
    public static final Supplier<SlabBlock> SMOOTH_MARBLE_SLAB = BLOCKS.register("smooth_marble_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(SMOOTH_MARBLE.get())));
    public static final Supplier<Block> MARBLE_BRICKS = BLOCKS.register("marble_bricks", () -> new Block(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final Supplier<Block> MARBLE_WALL = BLOCKS.register("marble_wall", () -> new MarbleWallBlock(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get()).forceSolidOn()));
}
