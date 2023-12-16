package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.block.SimpleCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * 不管有几个阶段，只有四种形状的作物
 * <p><s>为什么不直接设置 4 阶段</s></p>
 *
 * @param <T> 作物物品
 */
public class CropBlockWith4Shape<T extends Item> extends SimpleCropBlock<T> {

    protected static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0, 0, 0, 16, 5, 16),
            Block.box(0, 0, 0, 16, 7, 16),
            Block.box(0, 0, 0, 16, 9, 16),
    };

    public CropBlockWith4Shape(Supplier<RegistryObject<T>> crop, int age, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        super(crop, age, minSeedCount, maxSeedCount, minCropCount, maxCropCount);
    }

    public CropBlockWith4Shape(int age, int minCrop, int maxCrop) {
        super(age, minCrop, maxCrop);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int age = state.getValue(getAgeProperty());
        int stage = age / ((getMaxAge() + 1) / 4);
        return SHAPES[stage];
    }
}
