package games.moegirl.sinocraft.sinocore.tree.event.client;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.List;
import java.util.Map;

public class SCTreeRegisterRendererListener {
    private final String modid;

    public SCTreeRegisterRendererListener(String modid) {
        this.modid = modid;
    }

    @SubscribeEvent
    public void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
        var trees = Tree.getRegistry().entrySet()
                .stream()
                .filter(e -> e.getKey().getNamespace().equals(modid))
                .map(Map.Entry::getValue)
                .toList();

        for (var tree : trees) {
            // Todo: do we need it anymore?
//            event.registerBlockEntityRenderer();
        }
    }
}
