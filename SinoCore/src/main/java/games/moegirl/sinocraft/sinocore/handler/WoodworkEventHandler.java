package games.moegirl.sinocraft.sinocore.handler;

import games.moegirl.sinocraft.sinocore.client.render.ModSignRenderer;
import games.moegirl.sinocraft.sinocore.woodwork.Woodwork;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 树相关事件注册
 */
public class WoodworkEventHandler {

    private static final Map<String, WoodworkEventHandler> PROVIDERS = new HashMap<>();

    public static WoodworkEventHandler obtain(String modid) {
        return PROVIDERS.computeIfAbsent(modid, __ -> new WoodworkEventHandler());
    }

    public final List<Pair<Supplier<? extends ItemLike>, List<CreativeModeTab>>> tabs = new ArrayList<>();
    public final List<Woodwork> render = new ArrayList<>();
    public final List<Woodwork> renderer = new ArrayList<>();
    public final List<Woodwork> definitions = new ArrayList<>();

    public void register(IEventBus bus) {
        if (tabs.isEmpty() && render.isEmpty()) return;
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            if (!tabs.isEmpty()) {
                WCreativeTabContentHandler handler = new WCreativeTabContentHandler();
                for (Pair<Supplier<? extends ItemLike>, List<CreativeModeTab>> pair : tabs) {
                    handler.loadTabs(pair.getKey(), pair.getValue());
                }
                bus.register(handler);
            }
            if (!render.isEmpty()) {
                bus.addListener((Consumer<FMLClientSetupEvent>) event1 -> {
                    ChunkRenderTypeSet cutout = ChunkRenderTypeSet.of(RenderType.cutout());
                    for (Woodwork woodwork : render) {
                        ItemBlockRenderTypes.setRenderLayer(woodwork.trapdoor(), cutout);
                        ItemBlockRenderTypes.setRenderLayer(woodwork.door(), cutout);
                        Sheets.addWoodType(woodwork.type);
                    }
                });
            }
            if (!renderer.isEmpty()) bus.register(new WRegisterRendererHandler());
            if (!definitions.isEmpty()) bus.register(new WLayerDefinitionsRegister());
        });
    }

    public static class WCreativeTabContentHandler {
        final Map<CreativeModeTab, List<Supplier<? extends ItemLike>>> allTabs = new HashMap<>();

        @SubscribeEvent
        public void onTabBuild(CreativeModeTabEvent.BuildContents event) {
            if (allTabs.containsKey(event.getTab())) {
                allTabs.get(event.getTab()).forEach(event::accept);
            }
        }

        void loadTabs(Supplier<? extends ItemLike> item, List<CreativeModeTab> tabs) {
            tabs.forEach(tab -> allTabs.computeIfAbsent(tab, t -> new ArrayList<>()).add(item));
        }
    }

    public class WRegisterRendererHandler {

        @SubscribeEvent
        public void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
            for (Woodwork woodwork : renderer) {
                if (woodwork.useDefaultSignEntity())
                    event.registerBlockEntityRenderer(woodwork.signEntity(), ModSignRenderer::new);
                if (woodwork.useDefaultWallSignEntity())
                    event.registerBlockEntityRenderer(woodwork.wallSignEntity(), ModSignRenderer::new);
            }
        }
    }

    public class WLayerDefinitionsRegister {

        @SubscribeEvent
        public void onDefinitionRegister(EntityRenderersEvent.RegisterLayerDefinitions event) {
            for (Woodwork woodwork : definitions) {
                MeshDefinition meshdefinition = new MeshDefinition();
                PartDefinition partdefinition = meshdefinition.getRoot();
                partdefinition.addOrReplaceChild("sign", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F), PartPose.ZERO);
                partdefinition.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F), PartPose.ZERO);
                LayerDefinition layer = LayerDefinition.create(meshdefinition, 64, 32);
                event.registerLayerDefinition(ModelLayers.createSignModelName(woodwork.type), () -> layer);
            }
        }
    }
}
