package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.tree.ITreeBlock;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.Material;

/**
 * 树叶
 * @author luqin2007
 */
public class ModLeavesBlock extends LeavesBlock implements ITreeBlock {

    private final Tree tree;

    public ModLeavesBlock(Tree tree, Properties properties) {
        super(properties);
        this.tree = tree;
    }

    public ModLeavesBlock(Tree tree) {
        this(tree, Properties.of(Material.LEAVES)
                .randomTicks()
                .noOcclusion()
                .sound(tree.properties().leavesSound())
                .strength(tree.properties().strengthModifier().apply(.2f), .2f)
                .isValidSpawn((_1, _2, _3, entity) -> entity == EntityType.OCELOT || entity == EntityType.PARROT)
                .isSuffocating((_1, _2, _3) -> false)
                .isViewBlocking((_1, _2, _3) -> false));
    }

    @Override
    public Tree getTree() {
        return tree;
    }
}
