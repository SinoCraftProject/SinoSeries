package games.moegirl.sinocraft.sinodivination.old.world;

import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.material.Material;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SDPatterns {

    public static final BlockPattern ALTAR = BlockPatternBuilder.start()
            .aisle("    ∀    ", "    @    ", "    @    ", "    @    ", "    @    ", "    @    ", "    @    ", "    @    ", "    @    ", "    @    ")
            .aisle("         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ")
            .aisle("         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ")
            .aisle("         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ")
            .aisle("∀   ■   ∀", "@   @   @", "@   @   @", "@   @   @", "@   @   @", "@   @   @", "@   @   @", "@   @   @", "@   @   @", "@   @   @")
            .aisle("         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ")
            .aisle("         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ")
            .aisle("         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ")
            .aisle("    ∀    ", "    @    ", "    @    ", "    @    ", "    @    ", "    @    ", "    @    ", "    @    ", "    @    ", "    @    ")
            .where(' ', BlockInWorld.hasState(BlockStatePredicate.ANY))
            .where('∀', BlockInWorld.hasState(forBlock(SDBlocks.ALTAR)))
            .where('@', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR)))
            .where('■', BlockInWorld.hasState(forBlock(SDBlocks.TRIPOD)))
            .build();

    private static Predicate<BlockState> forBlock(Supplier<? extends Block> block) {
        return bs -> bs.is(block.get());
    }
}
