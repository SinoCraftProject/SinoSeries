package games.moegirl.sinocraft.sinocore.tree.event;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.client.TreeChestRenderer;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author luqin2007
 */
public class TreeClientEventHandler {

    @SubscribeEvent
    public void onBlockEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        TreeRegistry.getRegistry().forEach((modid, trees) -> {
            for (Tree tree : trees) {
                var chestRO = tree.getBlockEntityTypeObj(TreeBlockType.CHEST);
                if (chestRO != null && chestRO.isPresent()) {
                    event.registerBlockEntityRenderer(tree.getBlockEntityType(TreeBlockType.CHEST), context -> new TreeChestRenderer(context, tree, false));
                }

                var trappedChestRO = tree.getBlockEntityTypeObj(TreeBlockType.CHEST);
                if (trappedChestRO != null && trappedChestRO.isPresent()) {
                    event.registerBlockEntityRenderer(tree.getBlockEntityType(TreeBlockType.TRAPPED_CHEST), context -> new TreeChestRenderer(context, tree, true));
                }
            }
        });
    }
}
