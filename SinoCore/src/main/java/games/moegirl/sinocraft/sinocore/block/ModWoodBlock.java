package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.tree.depr.ITreeBlock;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

/**
 * 木 与 去皮木（？）
 */
public class ModWoodBlock extends RotatedPillarBlock implements ITreeBlock {

    private final Tree tree;
    private final boolean isStripped;

    public ModWoodBlock(Tree tree, boolean isStripped, Properties properties) {
        super(properties);
        this.tree = tree;
        this.isStripped = isStripped;
    }

    public ModWoodBlock(Tree tree, boolean isStripped) {
        this(tree, isStripped, Properties.of(Material.WOOD,
                        isStripped ? tree.properties().strippedWoodColor() : tree.properties().woodColor())
                .strength(tree.properties().strengthModifier().apply(2), 2.0f)
                .sound(SoundType.WOOD));
    }

    @Override
    public Tree getTree() {
        return tree;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (!isStripped && ToolActions.AXE_STRIP.equals(toolAction)) {
            return tree.strippedWood().defaultBlockState();
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
