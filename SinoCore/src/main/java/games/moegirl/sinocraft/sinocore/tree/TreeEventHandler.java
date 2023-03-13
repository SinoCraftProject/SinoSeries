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

/**
 * 树相关事件注册
 */
public class TreeEventHandler {

    private static final Map<String, TreeEventHandler> PROVIDERS = new HashMap<>();

    public static TreeEventHandler obtain(String modid) {
        return PROVIDERS.computeIfAbsent(modid, __ -> new TreeEventHandler());
    }

    final List<Tree> tabs = new ArrayList<>();
    final List<Tree> render = new ArrayList<>();

    void register(IEventBus bus) {
        if (tabs.isEmpty() && render.isEmpty()) return;
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            if (!tabs.isEmpty()) {
                TCreativeTabContentHandler handler = new TCreativeTabContentHandler();
                for (Tree tree : tabs) {
                    Tree.BuilderProperties properties = tree.properties();
                    handler.loadTabs(tree.sapling, properties.saplingTabs());
                    handler.loadTabs(tree.leaves, properties.leavesTabs());
                    handler.loadTabs(tree.log, properties.logTabs());
                    handler.loadTabs(tree.strippedLog, properties.strippedLogTabs());
                    handler.loadTabs(tree.wood, properties.woodTabs());
                    handler.loadTabs(tree.strippedWood, properties.strippedWoodTabs());
                }
                bus.register(handler);
            }
            if (!render.isEmpty()) {
                bus.addListener((Consumer<FMLClientSetupEvent>) event1 -> {
                    RenderType cutoutMipped = RenderType.cutoutMipped();
                    RenderType cutout = RenderType.cutout();
                    for (Tree tree : render) {
                        ItemBlockRenderTypes.setRenderLayer(tree.leaves(), cutoutMipped);
                        ItemBlockRenderTypes.setRenderLayer(tree.sapling(), cutout);
                        ItemBlockRenderTypes.setRenderLayer(tree.pottedSapling(), cutout);
                    }
                });
            }
        });
    }

    public static class TCreativeTabContentHandler {
        Map<CreativeModeTab, List<Supplier<? extends Block>>> allTabs = new HashMap<>();

        @SubscribeEvent
        public void onTabBuild(CreativeModeTabEvent.BuildContents event) {
            if (allTabs.containsKey(event.getTab())) {
                allTabs.get(event.getTab()).forEach(event::accept);
            }
        }

        void loadTabs(Supplier<? extends Block> block, List<CreativeModeTab> tabs) {
            tabs.forEach(tab -> allTabs.computeIfAbsent(tab, t -> new ArrayList<>()).add(block));
        }
    }
}
