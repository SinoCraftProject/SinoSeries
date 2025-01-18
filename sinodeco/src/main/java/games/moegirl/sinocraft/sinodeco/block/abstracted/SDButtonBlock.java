package games.moegirl.sinocraft.sinodeco.block.abstracted;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class SDButtonBlock extends ButtonBlock {
    public SDButtonBlock(BlockSetType type, int ticksToStayPressed, Properties properties) {
        super(type, ticksToStayPressed, properties);
    }
}
