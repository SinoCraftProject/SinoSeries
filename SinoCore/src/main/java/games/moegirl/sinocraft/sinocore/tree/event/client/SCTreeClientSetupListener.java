package games.moegirl.sinocraft.sinocore.tree.event.client;

import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class SCTreeClientSetupListener {
    private String modid;

    public SCTreeClientSetupListener(String modid) {
        this.modid = modid;
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        var trees = TreeRegistry.getRegistry().get(modid);

        for (var tree : trees) {
            // Deprecated! It was moved to model json file.
//            ItemBlockRenderTypes.setRenderLayer(tree.getBlock(TreeBlockType.LEAVES), RenderType.cutoutMipped());
//            ItemBlockRenderTypes.setRenderLayer(tree.getBlock(TreeBlockType.SAPLING), RenderType.cutout());
//            ItemBlockRenderTypes.setRenderLayer(tree.getBlock(TreeBlockType.POTTED_SAPLING), RenderType.cutout());
        }
    }
}
