package games.moegirl.sinocraft.sinodivination.old.block.base;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class OreBlock extends DropExperienceBlock {

    public OreBlock(Material material) {
        super(BlockBehaviour.Properties.of(material)
                .requiresCorrectToolForDrops()
                .explosionResistance(3.0F));
    }

    public OreBlock(Material material, int minExp, int maxExp) {
        super(BlockBehaviour.Properties.of(material)
                .requiresCorrectToolForDrops()
                .explosionResistance(3.0F), UniformInt.of(minExp, maxExp));
    }
}
