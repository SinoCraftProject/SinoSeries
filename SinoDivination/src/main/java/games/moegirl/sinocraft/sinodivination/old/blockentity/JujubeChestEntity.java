package games.moegirl.sinocraft.sinodivination.old.blockentity;

import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class JujubeChestEntity extends WoodenChestEntity {

    public JujubeChestEntity(BlockPos blockPos, BlockState state) {
        super(SDBlocks.JUJUBE_CHEST.get(), blockPos, state);
    }
}
