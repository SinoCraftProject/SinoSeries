package games.moegirl.sinocraft.sinocore.old.api.block;

import games.moegirl.sinocraft.sinocore.api.tree.Tree;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

/**
 * A class for log and stripped log with tree
 */
public class BlockTreeLog extends RotatedPillarBlock implements ITreeBlock {

    private final Tree tree;
    private final boolean isStripped;

    public BlockTreeLog(Tree tree, boolean isStripped, Properties properties) {
        super(properties);
        this.tree = tree;
        this.isStripped = isStripped;
    }

    public BlockTreeLog(Tree tree, boolean isStripped) {
        this(tree, isStripped, Properties.of(Material.WOOD, state -> color(tree, state, isStripped))
                .strength(tree.properties().strengthModifier().apply(2), 2.0f)
                .sound(SoundType.WOOD));
    }

    private static MaterialColor color(Tree tree, BlockState state, boolean isStripped) {
        Tree.BuilderProperties prop = tree.properties();
        if (isStripped) {
            return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? prop.topStrippedLogColor() : prop.barkStrippedLogColor();
        } else {
            return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? prop.topLogColor() : prop.barkLogColor();
        }
    }

    @Override
    public Tree getTree() {
        return tree;
    }

    @Nullable
    @Override
    @SuppressWarnings("removal")
    public BlockState getToolModifiedState(BlockState state, Level level, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
        if (!isStripped && ToolActions.AXE_STRIP.equals(toolAction)) {
            return tree.strippedLog().defaultBlockState();
        }
        return super.getToolModifiedState(state, level, pos, player, stack, toolAction);
    }
}
