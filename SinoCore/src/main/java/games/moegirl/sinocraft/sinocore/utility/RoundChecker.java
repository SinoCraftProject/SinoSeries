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
import java.util.function.Supplier;

@SuppressWarnings("CommentedOutCode")
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
//    public BlockPos relative(int x, int y, int z) {
//        return pos.relative(Direction.Axis.X, x).relative(Direction.Axis.Y, y).relative(Direction.Axis.Z, z);
//    }
//    public BlockPos relative(int x, int z) {
//        return pos.relative(Direction.Axis.X, x).relative(Direction.Axis.Z, z);
//    }
    public BlockPos relative(int y) {
        return pos.relative(Direction.Axis.Y, y);
    }
//    public BlockPos at(int x, int z) {
//        return new BlockPos(x, pos.getY(), z);
//    }
//    public BlockPos at(int y) {
//        return pos.atY(y);
//    }
    public BlockPos here() {
        return pos;
    }

    public void move(Direction direction) {
        set(relative(direction));
    }
//    public void move(Direction direction, int distance) {
//        set(relative(direction, distance));
//    }
//    public void move(int x, int y, int z) {
//        set(relative(x, y, z));
//    }
//    public void move(int x, int z) {
//        set(relative(x, z));
//    }
//    public void move(int y) {
//        set(relative(y));
//    }
//    public void set(int x, int z) {
//        set(at(x, z));
//    }
//    public void set(int y) {
//        set(at(y));
//    }
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
        return isAt(relative(direction), block);
    }
//    public boolean is(Direction direction, int distance, Block block) {
//        return isAt(relative(direction, distance), block);
//    }
//    public boolean is(int x, int y, int z, Block block) {
//        return isAt(relative(x, y, z), block);
//    }
//    public boolean is(int x, int z, Block block) {
//        return isAt(relative(x, z), block);
//    }
//    public boolean is(int y, Block block) {
//        return isAt(relative(y), block);
//    }
//    public boolean isAt(int x, int z, Block block) {
//        return isAt(at(x, z), block);
//    }
//    public boolean isAt(int y, Block block) {
//        return isAt(at(y), block);
//    }
    public boolean isAt(BlockPos p, Block block) {
        return block(p).is(block);
    }

    public boolean is(Direction direction, TagKey<Block> block) {
        return isAt(relative(direction), block);
    }
//    public boolean is(Direction direction, int distance, TagKey<Block> block) {
//        return isAt(relative(direction, distance), block);
//    }
//    public boolean is(int x, int y, int z, TagKey<Block> block) {
//        return isAt(relative(x, y, z), block);
//    }
//    public boolean is(int x, int z, TagKey<Block> block) {
//        return isAt(relative(x, z), block);
//    }
//    public boolean is(int y, TagKey<Block> block) {
//        return isAt(relative(y), block);
//    }
//    public boolean isAt(int x, int z, TagKey<Block> block) {
//        return isAt(at(x, z), block);
//    }
//    public boolean isAt(int y, TagKey<Block> block) {
//        return isAt(at(y), block);
//    }
    public boolean isAt(BlockPos p, TagKey<Block> block) {
        return block(p).is(block);
    }

//    public boolean is(Direction direction, Supplier<? extends Block> block) {
//        return isAt(relative(direction), block);
//    }
//    public boolean is(Direction direction, int distance, Supplier<? extends Block> block) {
//        return isAt(relative(direction, distance), block);
//    }
//    public boolean is(int x, int y, int z, Supplier<? extends Block> block) {
//        return isAt(relative(x, y, z), block);
//    }
//    public boolean is(int x, int z, Supplier<? extends Block> block) {
//        return isAt(relative(x, z), block);
//    }
//    public boolean is(int y, Supplier<? extends Block> block) {
//        return isAt(relative(y), block);
//    }
//    public boolean isAt(int x, int z, Supplier<? extends Block> block) {
//        return isAt(at(x, z), block);
//    }
//    public boolean isAt(int y, Supplier<? extends Block> block) {
//        return isAt(at(y), block);
//    }
//    public boolean isAt(BlockPos p, Supplier<? extends Block> block) {
//        return block(p).is(block.get());
//    }

//    public boolean near(Direction direction, Block block) {
//        return nearAt(relative(direction), block);
//    }
//    public boolean near(Direction direction, int distance, Block block) {
//        return nearAt(relative(direction, distance), block);
//    }
//    public boolean near(int x, int y, int z, Block block) {
//        return nearAt(relative(x, y, z), block);
//    }
//    public boolean near(int x, int z, Block block) {
//        return nearAt(relative(x, z), block);
//    }
//    public boolean near(int y, Block block) {
//        return nearAt(relative(y), block);
//    }
//    public boolean nearAt(int x, int z, Block block) {
//        return nearAt(at(x, z), block);
//    }
//    public boolean nearAt(int y, Block block) {
//        return nearAt(at(y), block);
//    }
//    public boolean nearAt(BlockPos p, Block block) {
//        for (Direction value : Direction.values()) {
//            if (block(p.relative(value)).is(block)) return true;
//        }
//        return false;
//    }

    public boolean near(Direction direction, TagKey<Block> block) {
        return nearAt(relative(direction), block);
    }
//    public boolean near(Direction direction, int distance, TagKey<Block> block) {
//        return nearAt(relative(direction, distance), block);
//    }
//    public boolean near(int x, int y, int z, TagKey<Block> block) {
//        return nearAt(relative(x, y, z), block);
//    }
//    public boolean near(int x, int z, TagKey<Block> block) {
//        return nearAt(relative(x, z), block);
//    }
//    public boolean near(int y, TagKey<Block> block) {
//        return nearAt(relative(y), block);
//    }
//    public boolean nearAt(int x, int z, TagKey<Block> block) {
//        return nearAt(at(x, z), block);
//    }
//    public boolean nearAt(int y, TagKey<Block> block) {
//        return nearAt(at(y), block);
//    }
    public boolean nearAt(BlockPos p, TagKey<Block> block) {
        for (Direction value : Direction.values()) {
            if (block(p.relative(value)).is(block)) return true;
        }
        return false;
    }

//    public boolean near(Direction direction, Supplier<? extends Block> block) {
//        return nearAt(relative(direction), block);
//    }
//    public boolean near(Direction direction, int distance, Supplier<? extends Block> block) {
//        return nearAt(relative(direction, distance), block);
//    }
//    public boolean near(int x, int y, int z, Supplier<? extends Block> block) {
//        return nearAt(relative(x, y, z), block);
//    }
//    public boolean near(int x, int z, Supplier<? extends Block> block) {
//        return nearAt(relative(x, z), block);
//    }
    public boolean near(int y, Supplier<? extends Block> block) {
        return nearAt(relative(y), block);
    }
//    public boolean nearAt(int x, int z, Supplier<? extends Block> block) {
//        return nearAt(at(x, z), block);
//    }
//    public boolean nearAt(int y, Supplier<? extends Block> block) {
//        return nearAt(at(y), block);
//    }
    public boolean nearAt(BlockPos p, Supplier<? extends Block> block) {
        for (Direction value : Direction.values()) {
            if (block(p.relative(value)).is(block.get())) return true;
        }
        return false;
    }

//    public boolean isAir(Direction direction) {
//        return isAirAt(relative(direction));
//    }
    public boolean isAir(Direction direction, int distance) {
        return isAirAt(relative(direction, distance));
    }
//    public boolean isAir(int x, int y, int z) {
//        return isAirAt(relative(x, y, z));
//    }
//    public boolean isAir(int x, int z) {
//        return isAirAt(relative(x, z));
//    }
//    public boolean isAir(int y) {
//        return isAirAt(relative(y));
//    }
//    public boolean isAirAt(int x, int z) {
//        return isAirAt(at(x, z));
//    }
//    public boolean isAirAt(int y) {
//        return isAirAt(at(y));
//    }
    public boolean isAirAt(BlockPos p) {
        return block(p).isAir();
    }

    public boolean isReplaceable(Direction direction) {
        return isReplaceableAt(relative(direction));
    }
//    public boolean isReplaceable(Direction direction, int distance) {
//        return isReplaceableAt(relative(direction, distance));
//    }
//    public boolean isReplaceable(int x, int y, int z) {
//        return isReplaceableAt(relative(x, y, z));
//    }
//    public boolean isReplaceable(int x, int z) {
//        return isReplaceableAt(relative(x, z));
//    }
//    public boolean isReplaceable(int y) {
//        return isReplaceableAt(relative(y));
//    }
//    public boolean isReplaceableAt(int x, int z) {
//        return isReplaceableAt(at(x, z));
//    }
//    public boolean isReplaceableAt(int y) {
//        return isReplaceableAt(at(y));
//    }
    public boolean isReplaceableAt(BlockPos p) {
        return block(p).is(BlockTags.REPLACEABLE_PLANTS);
    }

//    public boolean isBurning(Direction direction) {
//        return isBurningAt(relative(direction));
//    }
//    public boolean isBurning(Direction direction, int distance) {
//        return isBurningAt(relative(direction, distance));
//    }
//    public boolean isBurning(int x, int y, int z) {
//        return isBurningAt(relative(x, y, z));
//    }
//    public boolean isBurning(int x, int z) {
//        return isBurningAt(relative(x, z));
//    }
//    public boolean isBurning(int y) {
//        return isBurningAt(relative(y));
//    }
//    public boolean isBurningAt(int x, int z) {
//        return isBurningAt(at(x, z));
//    }
//    public boolean isBurningAt(int y) {
//        return isBurningAt(at(y));
//    }
//    public boolean isBurningAt(BlockPos p) {
//        return block(p).isBurning(level, p);
//    }

//    public <T extends Comparable<T>> boolean has(Direction direction, Property<T> property, T value) {
//        return hasAt(relative(direction), property, value);
//    }
//    public <T extends Comparable<T>> boolean has(Direction direction, int distance, Property<T> property, T value) {
//        return hasAt(relative(direction, distance), property, value);
//    }
//    public <T extends Comparable<T>> boolean has(int x, int y, int z, Property<T> property, T value) {
//        return hasAt(relative(x, y, z), property, value);
//    }
//    public <T extends Comparable<T>> boolean has(int x, int z, Property<T> property, T value) {
//        return hasAt(relative(x, z), property, value);
//    }
//    public <T extends Comparable<T>> boolean has(int y, Property<T> property, T value) {
//        return hasAt(relative(y), property, value);
//    }
//    public <T extends Comparable<T>> boolean hasAt(int x, int z, Property<T> property, T value) {
//        return hasAt(at(x, z), property, value);
//    }
//    public <T extends Comparable<T>> boolean hasAt(int y, Property<T> property, T value) {
//        return hasAt(at(y), property, value);
//    }
//    public <T extends Comparable<T>> boolean hasAt(BlockPos p, Property<T> property, T value) {
//        BlockState state = block(p);
//        return state.hasProperty(property) && state.getValue(property).equals(value);
//    }

//    public <T extends Comparable<T>> boolean larger(Direction direction, Property<T> property, T value) {
//        return largerAt(relative(direction), property, value);
//    }
//    public <T extends Comparable<T>> boolean larger(Direction direction, int distance, Property<T> property, T value) {
//        return largerAt(relative(direction, distance), property, value);
//    }
//    public <T extends Comparable<T>> boolean larger(int x, int y, int z, Property<T> property, T value) {
//        return largerAt(relative(x, y, z), property, value);
//    }
//    public <T extends Comparable<T>> boolean larger(int x, int z, Property<T> property, T value) {
//        return largerAt(relative(x, z), property, value);
//    }
//    public <T extends Comparable<T>> boolean larger(int y, Property<T> property, T value) {
//        return largerAt(relative(y), property, value);
//    }
//    public <T extends Comparable<T>> boolean largerAt(int x, int z, Property<T> property, T value) {
//        return largerAt(at(x, z), property, value);
//    }
//    public <T extends Comparable<T>> boolean largerAt(int y, Property<T> property, T value) {
//        return largerAt(at(y), property, value);
//    }
//    public <T extends Comparable<T>> boolean largerAt(BlockPos p, Property<T> property, T value) {
//        BlockState state = block(p);
//        return state.hasProperty(property) && state.getValue(property).compareTo(value) > 0;
//    }

//    public <T extends Comparable<T>> boolean smaller(Direction direction, Property<T> property, T value) {
//        return smallerAt(relative(direction), property, value);
//    }
//    public <T extends Comparable<T>> boolean smaller(Direction direction, int distance, Property<T> property, T value) {
//        return smallerAt(relative(direction, distance), property, value);
//    }
//    public <T extends Comparable<T>> boolean smaller(int x, int y, int z, Property<T> property, T value) {
//        return smallerAt(relative(x, y, z), property, value);
//    }
//    public <T extends Comparable<T>> boolean smaller(int x, int z, Property<T> property, T value) {
//        return smallerAt(relative(x, z), property, value);
//    }
//    public <T extends Comparable<T>> boolean smaller(int y, Property<T> property, T value) {
//        return smallerAt(relative(y), property, value);
//    }
//    public <T extends Comparable<T>> boolean smallerAt(int x, int z, Property<T> property, T value) {
//        return smallerAt(at(x, z), property, value);
//    }
//    public <T extends Comparable<T>> boolean smallerAt(int y, Property<T> property, T value) {
//        return smallerAt(at(y), property, value);
//    }
//    public <T extends Comparable<T>> boolean smallerAt(BlockPos p, Property<T> property, T value) {
//        BlockState state = block(p);
//        return state.hasProperty(property) && state.getValue(property).compareTo(value) < 0;
//    }

//    public boolean has(Direction direction, Fluid fluid) {
//        return hasAt(relative(direction), fluid);
//    }
//    public boolean has(Direction direction, int distance, Fluid fluid) {
//        return hasAt(relative(direction, distance), fluid);
//    }
//    public boolean has(int x, int y, int z, Fluid fluid) {
//        return hasAt(relative(x, y, z), fluid);
//    }
//    public boolean has(int x, int z, Fluid fluid) {
//        return hasAt(relative(x, z), fluid);
//    }
//    public boolean has(int y, Fluid fluid) {
//        return hasAt(relative(y), fluid);
//    }
//    public boolean hasAt(int x, int z, Fluid fluid) {
//        return hasAt(at(x, z), fluid);
//    }
//    public boolean hasAt(int y, Fluid fluid) {
//        return hasAt(at(y), fluid);
//    }
//    public boolean hasAt(BlockPos p, Fluid fluid) {
//        return fluid(p).is(fluid);
//    }

//    public boolean has(Direction direction, TagKey<Fluid> fluid) {
//        return hasAt(relative(direction), fluid);
//    }
//    public boolean has(Direction direction, int distance, TagKey<Fluid> fluid) {
//        return hasAt(relative(direction, distance), fluid);
//    }
//    public boolean has(int x, int y, int z, TagKey<Fluid> fluid) {
//        return hasAt(relative(x, y, z), fluid);
//    }
//    public boolean has(int x, int z, TagKey<Fluid> fluid) {
//        return hasAt(relative(x, z), fluid);
//    }
//    public boolean has(int y, TagKey<Fluid> fluid) {
//        return hasAt(relative(y), fluid);
//    }
//    public boolean hasAt(int x, int z, TagKey<Fluid> fluid) {
//        return hasAt(at(x, z), fluid);
//    }
//    public boolean hasAt(int y, TagKey<Fluid> fluid) {
//        return hasAt(at(y), fluid);
//    }
//    public boolean hasAt(BlockPos p, TagKey<Fluid> fluid) {
//        return fluid(p).is(fluid);
//    }

    public boolean isSource(Direction direction) {
        return isSourceAt(relative(direction));
    }
//    public boolean isSource(Direction direction, int distance) {
//        return isSourceAt(relative(direction, distance));
//    }
//    public boolean isSource(int x, int y, int z) {
//        return isSourceAt(relative(x, y, z));
//    }
//    public boolean isSource(int x, int z) {
//        return isSourceAt(relative(x, z));
//    }
//    public boolean isSource(int y) {
//        return isSourceAt(relative(y));
//    }
//    public boolean isSourceAt(int x, int z) {
//        return isSourceAt(at(x, z));
//    }
//    public boolean isSourceAt(int y) {
//        return isSourceAt(at(y));
//    }
    public boolean isSourceAt(BlockPos p) {
        return fluid(p).isSource();
    }

//    public boolean isDry(Direction direction) {
//        return isDryAt(relative(direction));
//    }
//    public boolean isDry(Direction direction, int distance) {
//        return isDryAt(relative(direction, distance));
//    }
//    public boolean isDry(int x, int y, int z) {
//        return isDryAt(relative(x, y, z));
//    }
//    public boolean isDry(int x, int z) {
//        return isDryAt(relative(x, z));
//    }
//    public boolean isDry(int y) {
//        return isDryAt(relative(y));
//    }
//    public boolean isDryAt(int x, int z) {
//        return isDryAt(at(x, z));
//    }
//    public boolean isDryAt(int y) {
//        return isDryAt(at(y));
//    }
//    public boolean isDryAt(BlockPos p) {
//        return fluid(p).isEmpty();
//    }

//    public boolean has(Direction direction, Supplier<? extends Fluid> fluid) {
//        return hasAt(relative(direction), fluid);
//    }
//    public boolean has(Direction direction, int distance, Supplier<? extends Fluid> fluid) {
//        return hasAt(relative(direction, distance), fluid);
//    }
//    public boolean has(int x, int y, int z, Supplier<? extends Fluid> fluid) {
//        return hasAt(relative(x, y, z), fluid);
//    }
//    public boolean has(int x, int z, Supplier<? extends Fluid> fluid) {
//        return hasAt(relative(x, z), fluid);
//    }
//    public boolean has(int y, Supplier<? extends Fluid> fluid) {
//        return hasAt(relative(y), fluid);
//    }
//    public boolean hasAt(int x, int z, Supplier<? extends Fluid> fluid) {
//        return hasAt(at(x, z), fluid);
//    }
//    public boolean hasAt(int y, Supplier<? extends Fluid> fluid) {
//        return hasAt(at(y), fluid);
//    }
//    public boolean hasAt(BlockPos p, Supplier<? extends Fluid> fluid) {
//        return fluid(p).is(fluid.get());
//    }

//    public boolean is(Direction direction, int light) {
//        return isAt(relative(direction), light);
//    }
//    public boolean is(Direction direction, int distance, int light) {
//        return isAt(relative(direction, distance), light);
//    }
//    public boolean is(int x, int y, int z, int light) {
//        return isAt(relative(x, y, z), light);
//    }
//    public boolean is(int x, int z, int light) {
//        return isAt(relative(x, z), light);
//    }
//    public boolean is(int y, int light) {
//        return isAt(relative(y), light);
//    }
//    public boolean isAt(int x, int z, int light) {
//        return isAt(at(x, z), light);
//    }
//    public boolean isAt(int y, int light) {
//        return isAt(at(y), light);
//    }
//    public boolean isAt(BlockPos p, int light) {
//        return light(p) == light;
//    }

//    public boolean light(Direction direction, int min) {
//        return lightAt(relative(direction), min);
//    }
//    public boolean light(Direction direction, int distance, int min) {
//        return lightAt(relative(direction, distance), min);
//    }
//    public boolean light(int x, int y, int z, int min) {
//        return lightAt(relative(x, y, z), min);
//    }
//    public boolean light(int x, int z, int min) {
//        return lightAt(relative(x, z), min);
//    }
//    public boolean light(int y, int min) {
//        return lightAt(relative(y), min);
//    }
//    public boolean lightAt(int x, int z, int min) {
//        return lightAt(at(x, z), min);
//    }
//    public boolean lightAt(int y, int min) {
//        return lightAt(at(y), min);
//    }
//    public boolean lightAt(BlockPos p, int min) {
//        return light(p) >= min;
//    }

    public boolean dark(Direction direction, int max) {
        return darkAt(relative(direction), max);
    }
//    public boolean dark(Direction direction, int distance, int max) {
//        return darkAt(relative(direction, distance), max);
//    }
//    public boolean dark(int x, int y, int z, int max) {
//        return darkAt(relative(x, y, z), max);
//    }
//    public boolean dark(int x, int z, int max) {
//        return darkAt(relative(x, z), max);
//    }
//    public boolean dark(int y, int max) {
//        return darkAt(relative(y), max);
//    }
//    public boolean darkAt(int x, int z, int max) {
//        return darkAt(at(x, z), max);
//    }
//    public boolean darkAt(int y, int max) {
//        return darkAt(at(y), max);
//    }
    public boolean darkAt(BlockPos p, int max) {
        return light(p) <= max;
    }

    public void clear() {
        bs.clear();
    }
}
