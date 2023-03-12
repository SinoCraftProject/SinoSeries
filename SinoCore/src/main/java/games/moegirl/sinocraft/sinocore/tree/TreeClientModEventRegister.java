package games.moegirl.sinocraft.sinocore.tree;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TreeClientModEventRegister {

    private final Map<CreativeModeTab, List<Supplier<? extends Block>>> allTabs = new HashMap<>();

    TreeClientModEventRegister(Tree tree, IEventBus event) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            Tree.BuilderProperties properties = tree.properties();
            loadTabs(tree.sapling, properties.saplingTabs());
            loadTabs(tree.leaves, properties.leavesTabs());
            loadTabs(tree.log, properties.logTabs());
            loadTabs(tree.strippedLog, properties.strippedLogTabs());
            loadTabs(tree.wood, properties.woodTabs());
            loadTabs(tree.strippedWood, properties.strippedWoodTabs());

            event.register(this);
            event.addListener((Consumer<FMLClientSetupEvent>) event1 -> {
                RenderType cutoutMipped = RenderType.cutoutMipped();
                RenderType cutout = RenderType.cutout();

                ItemBlockRenderTypes.setRenderLayer(tree.leaves(), cutoutMipped);
                ItemBlockRenderTypes.setRenderLayer(tree.sapling(), cutout);
                ItemBlockRenderTypes.setRenderLayer(tree.pottedSapling(), cutout);
            });
        });
    }

    private void loadTabs(Supplier<? extends Block> block, List<CreativeModeTab> tabs) {
        tabs.forEach(tab -> allTabs.computeIfAbsent(tab, t -> new ArrayList<>()).add(block));
    }

    @SubscribeEvent
    public void onTabBuild(CreativeModeTabEvent.BuildContents event) {
        if (allTabs.containsKey(event.getTab())) {
            allTabs.get(event.getTab()).forEach(event::accept);
        }
    }
}
