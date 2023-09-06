package games.moegirl.sinocraft.sinocore.tree.event;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * @author luqin2007
 */
@Mod.EventBusSubscriber
public class TreeToolEventHandler {

    @SubscribeEvent
    public static void onToolUseEvent(BlockEvent.BlockToolModificationEvent event) {
        Block block = event.getState().getBlock();
        if (event.getToolAction() == ToolActions.AXE_STRIP) {
            handleAxeStrip(event, block);
        }
    }

    /*
    斧
     */
    private static void handleAxeStrip(BlockEvent.BlockToolModificationEvent event, Block block) {
        boolean found = false;
        for (List<Tree> trees : TreeRegistry.getRegistry().values()) {
            for (Tree tree : trees) {
                if (block == tree.getBlockOrNull(TreeBlockType.LOG)
                        && tree.hasBlock(TreeBlockType.STRIPPED_LOG)) {
                    event.setFinalState(tree.getBlock(TreeBlockType.STRIPPED_LOG).defaultBlockState());
                    found = true;
                    break;
                }
                if (block == tree.getBlockOrNull(TreeBlockType.LOG_WOOD)
                        && tree.hasBlock(TreeBlockType.STRIPPED_LOG_WOOD)) {
                    event.setFinalState(tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD).defaultBlockState());
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
    }
}
