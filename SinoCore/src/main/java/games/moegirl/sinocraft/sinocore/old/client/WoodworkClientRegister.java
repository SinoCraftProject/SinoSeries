package games.moegirl.sinocraft.sinocore.old.client;

import games.moegirl.sinocraft.sinocore.old.client.render.ModSignRenderer;
import games.moegirl.sinocraft.sinocore.old.woodwork.Woodwork;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public record WoodworkClientRegister(Woodwork woodwork) {

    /**
     * Call when {@link FMLClientSetupEvent} event fired
     * @deprecated todo ItemBlockRenderTypes.setRenderLayer will be removed?
     */
    @Deprecated(forRemoval = true)
    public void registerRender() {
        ChunkRenderTypeSet cutout = ChunkRenderTypeSet.of(RenderType.cutout());
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
