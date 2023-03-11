package games.moegirl.sinocraft.sinocore.old.woodwork;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.WoodType;

public interface IWoodworkEntity {

    default WoodType getWoodType() {
        if (this instanceof BlockEntity be) {
            Block block = be.getBlockState().getBlock();
            if (block instanceof IWoodwork woodwork) {
                return woodwork.getWoodType();
            }
        }
        return WoodType.OAK;
    }
}
