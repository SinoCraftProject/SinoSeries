package games.moegirl.sinocraft.sinocore.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.HashMap;
import java.util.Map;

public class RoundChecker {

    private final BlockGetter level;
    private BlockPos pos;
    private final Map<BlockPos, BlockState> bs = new HashMap<>();

    public RoundChecker(BlockGetter level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    public BlockPos relative(Direction direction) {
        return pos.relative(direction);
    }
    public BlockPos relative(Direction direction, int distance) {
        return pos.relative(direction, distance);
    }
    public BlockPos here() {
        return pos;
    }

    public void move(Direction direction) {
        set(relative(direction));
    }
    public void set(BlockPos p) {
        pos = p;
    }

    public BlockState block(BlockPos pos) {
        return bs.computeIfAbsent(pos, level::getBlockState);
    }
    public FluidState fluid(BlockPos pos) {
        return block(pos).getFluidState();
    }
    public int light(BlockPos pos) {
        return level instanceof BlockAndTintGetter t ? t.getRawBrightness(pos, 0) : -1;
    }

    public boolean is(Direction direction, Block block) {
        return block(relative(direction)).is(block);
    }
    public boolean is(Direction direction, TagKey<Block> block) {
        return block(relative(direction)).is(block);
    }

    public boolean near(Direction direction, TagKey<Block> block) {
        BlockPos p = relative(direction);
        for (Direction value : Direction.values()) {
            if (block(p.relative(value)).is(block)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAir(Direction direction, int distance) {
        return block(relative(direction, distance)).isAir();
    }
    public boolean isReplaceable(Direction direction) {
        return block(relative(direction)).is(BlockTags.REPLACEABLE_PLANTS);
    }

    public boolean isSource(Direction direction) {
        return fluid(relative(direction)).isSource();
    }

    public boolean dark(Direction direction, int max) {
        return light(relative(direction)) <= max;
    }
}
