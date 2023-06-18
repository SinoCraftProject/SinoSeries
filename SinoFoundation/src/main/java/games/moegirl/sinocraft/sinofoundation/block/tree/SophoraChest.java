package games.moegirl.sinocraft.sinofoundation.block.tree;

import games.moegirl.sinocraft.sinocore.block.BaseChestBlock;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import games.moegirl.sinocraft.sinofoundation.block.entity.SFDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SophoraChest extends BaseChestBlock implements SophoraBlock {

    public SophoraChest() {
        super(SFDBlockEntities.SOPHORA_CHEST, SFDTrees.SOPHORA.name);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState blockState, LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(level, pos, blockState, placer, itemStack);
        placedBy(level, pos, placer);
    }
}
