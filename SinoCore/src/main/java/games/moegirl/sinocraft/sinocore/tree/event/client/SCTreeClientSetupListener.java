package games.moegirl.sinocraft.sinocore.tree.event.client;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

public class SCTreeClientSetupListener {
    private String modid;

    public SCTreeClientSetupListener(String modid) {
        this.modid = modid;
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        var trees = Tree.getRegistry().entrySet()
                .stream()
                .filter(e -> e.getKey().getNamespace().equals(modid))
                .map(Map.Entry::getValue)
                .toList();

        for (var tree : trees) {
            // Deprecated! It was moved to model json file.
//            ItemBlockRenderTypes.setRenderLayer(tree.getBlock(TreeBlockType.LEAVES), RenderType.cutoutMipped());
//            ItemBlockRenderTypes.setRenderLayer(tree.getBlock(TreeBlockType.SAPLING), RenderType.cutout());
//            ItemBlockRenderTypes.setRenderLayer(tree.getBlock(TreeBlockType.POTTED_SAPLING), RenderType.cutout());
        }
    }
}
