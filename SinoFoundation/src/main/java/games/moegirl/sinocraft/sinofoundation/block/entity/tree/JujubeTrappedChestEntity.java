package games.moegirl.sinocraft.sinofoundation.block.entity.tree;

import games.moegirl.sinocraft.sinocore.blockentity.BaseTrappedChestBlockEntity;
import games.moegirl.sinocraft.sinofoundation.block.entity.SFDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author luqin2007
 */
public class JujubeTrappedChestEntity extends BaseTrappedChestBlockEntity {

    public JujubeTrappedChestEntity(BlockPos pos, BlockState state) {
        super(SFDBlockEntities.JUJUBE_TRAPPED_CHEST.get(), pos, state);
    }
}
