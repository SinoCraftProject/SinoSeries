package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SophoraTrapdoor extends TrapDoorBlock implements SophoraBlock {

    private final Tree tree;

    public SophoraTrapdoor(Tree tree, Properties properties) {
        super(properties, tree.getBlockSetType());
        this.tree = tree;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState blockState, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(level, pos, blockState, placer, itemStack);
        placedBy(level, pos, placer);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return tree.getBlockEntityType(TreeBlockType.TRAPDOOR).create(pos, blockState);
    }
}
