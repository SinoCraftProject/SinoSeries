package games.moegirl.sinocraft.sinocore.old.api.client;

import games.moegirl.sinocraft.sinocore.api.tree.Tree;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("unused")
public record TreeClientRegister(Tree tree) {

    /**
     * Register all block's render types, call in {@link FMLClientSetupEvent}
     */
    public void registerRenderType() {
        RenderType cutoutMipped = RenderType.cutoutMipped();
        RenderType cutout = RenderType.cutout();

        ItemBlockRenderTypes.setRenderLayer(tree.leaves(), cutoutMipped);
        ItemBlockRenderTypes.setRenderLayer(tree.sapling(), cutout);
        ItemBlockRenderTypes.setRenderLayer(tree.pottedSapling(), cutout);
    }
}
