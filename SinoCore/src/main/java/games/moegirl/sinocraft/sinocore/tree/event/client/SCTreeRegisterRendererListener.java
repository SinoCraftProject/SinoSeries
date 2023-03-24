package games.moegirl.sinocraft.sinocore.tree.event.client;

import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SCTreeRegisterRendererListener {
    private final String modid;

    public SCTreeRegisterRendererListener(String modid) {
        this.modid = modid;
    }

    @SubscribeEvent
    public void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
        var trees = TreeRegistry.getRegistry().get(modid);

        for (var tree : trees) {
            // Todo: do we need it anymore?
//            event.registerBlockEntityRenderer();
        }
    }
}
