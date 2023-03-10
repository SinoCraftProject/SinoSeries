package games.moegirl.sinocraft.sinocore.old.api.block;

import games.moegirl.sinocraft.sinocore.api.woodwork.IWoodwork;
import games.moegirl.sinocraft.sinocore.api.woodwork.Woodwork;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

/**
 * A class for plank with tree
 */
public class BlockTreePlanks extends Block implements IWoodwork {

    private final Woodwork woodwork;

    public BlockTreePlanks(Woodwork woodwork, Properties properties) {
        super(properties);
        this.woodwork = woodwork;
    }

    public BlockTreePlanks(Woodwork woodwork) {
        this(woodwork, Properties.of(Material.WOOD, woodwork.plankColor)
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD));
    }

    @Override
    public Woodwork getWoodwork() {
        return woodwork;
    }
}
