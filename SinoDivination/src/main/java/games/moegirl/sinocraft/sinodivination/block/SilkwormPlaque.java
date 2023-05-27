package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.block.AbstractEntityBlock;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.blockentity.SilkwormPlaqueEntity;
import net.minecraft.world.level.block.Blocks;

public class SilkwormPlaque extends AbstractEntityBlock<SilkwormPlaqueEntity> {

    public SilkwormPlaque() {
        super(Properties.copy(Blocks.STONE), SDBlockEntities.SILKWORM_PLAQUE);
    }
}
