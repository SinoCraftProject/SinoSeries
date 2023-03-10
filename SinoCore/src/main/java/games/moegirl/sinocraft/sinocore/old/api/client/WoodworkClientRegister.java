package games.moegirl.sinocraft.sinocore.old.api.client;

import games.moegirl.sinocraft.sinocore.api.client.render.ModSignRenderer;
import games.moegirl.sinocraft.sinocore.api.woodwork.Woodwork;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("unused")
public record WoodworkClientRegister(Woodwork woodwork) {

    /**
     * Call when {@link FMLClientSetupEvent} event fired
     */
    public void registerRender() {
        RenderType cutout = RenderType.cutout();
        ItemBlockRenderTypes.setRenderLayer(woodwork.trapdoor(), cutout);
        ItemBlockRenderTypes.setRenderLayer(woodwork.door(), cutout);
        Sheets.addWoodType(woodwork.type);
    }

    /**
     * Call when {@link EntityRenderersEvent.RegisterRenderers} event fired
     */
    public void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
        if (woodwork.useDefaultSignEntity()) {
            event.registerBlockEntityRenderer(woodwork.signEntity(), ModSignRenderer::new);
        }
        if (woodwork.useDefaultWallSignEntity()) {
            event.registerBlockEntityRenderer(woodwork.wallSignEntity(), ModSignRenderer::new);
        }
    }
}
