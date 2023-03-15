package games.moegirl.sinocraft.sinocore.woodwork;

import games.moegirl.sinocraft.sinocore.utility.Self;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Mod 木制品方块中的 BlockEntity 接口
 */
public interface IWoodworkEntity<T extends BlockEntity> extends Self<T> {

    default Woodwork getWoodwork() {
        Block block = self().getBlockState().getBlock();
        if (block instanceof IWoodworkBlock woodwork) {
            return woodwork.getWoodwork();
        }
        throw new RuntimeException("Not found woodwork in " + self().getClass());
    }
}
