package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinodivination.blockentity.SophoraDoorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SophoraDoor extends DoorBlock implements SophoraBlock {

    private final Tree tree;

    public SophoraDoor(Tree tree, Properties properties) {
        super(properties, tree.getBlockSetType());
        this.tree = tree;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState blockState, LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(level, pos, blockState, placer, itemStack);
        SophoraDoorEntity blockEntity = (SophoraDoorEntity) level.getBlockEntity(pos);
        assert blockEntity != null;
        blockEntity.setEntity(placer);
        SophoraDoorEntity blockEntity2 = (SophoraDoorEntity) level.getBlockEntity(pos.above());
        assert blockEntity2 != null;
        blockEntity2.above();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return tree.getBlockEntityType(TreeBlockType.DOOR).create(pos, blockState);
    }
}
