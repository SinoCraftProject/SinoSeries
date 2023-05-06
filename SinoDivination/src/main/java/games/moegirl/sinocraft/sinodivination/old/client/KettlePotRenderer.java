package games.moegirl.sinocraft.sinodivination.old.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import games.moegirl.sinocraft.sinocore.utility.texture.RenderTypes;
import games.moegirl.sinocraft.sinodivination.old.blockentity.KettlePotEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class KettlePotRenderer implements BlockEntityRenderer<KettlePotEntity> {

    public final BlockEntityRendererProvider.Context context;

    public KettlePotRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(KettlePotEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // shader: rendertype_cutout
        ShaderInstance shader = RenderSystem.getShader();
        RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);
        FluidStack fluid = blockEntity.getTank().getFluid();
        if (!fluid.isEmpty()) {
            // todo render fluid
//            ResourceLocation texture = fluid.getFluid().getAttributes().getStillTexture();
//            texture = new ResourceLocation(texture.getNamespace(), "textures/" + texture.getPath() + ".png");
//            Tesselator instance = Tesselator.getInstance();
//            RenderSystem.setShaderTexture(0, texture);
//            BufferBuilder buffer = instance.getBuilder();
//            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
//            buffer.vertex(0, 0, 1).color(1, 1, 1, 1).uv(0, 0).uv2(packedLight).endVertex();
//            buffer.vertex(0, 1, 1).color(1, 1, 1, 1).uv(0, 1).uv2(packedLight).endVertex();
//            buffer.vertex(1, 1, 1).color(1, 1, 1, 1).uv(1, 1).uv2(packedLight).endVertex();
//            buffer.vertex(1, 0, 1).color(1, 1, 1, 1).uv(1, 0).uv2(packedLight).endVertex();
//            buffer.end();
        }
    }
}
