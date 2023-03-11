package games.moegirl.sinocraft.sinocore.old.utility.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;

public class RenderTypes {
    protected static final RenderStateShard.ShaderStateShard POSITION_TEX_SHADER = new RenderStateShard.ShaderStateShard(GameRenderer::getPositionTexShader);
    protected static final RenderStateShard.ShaderStateShard POSITION_COLOR_TEX_SHADER = new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorTexShader);
    protected static final RenderStateShard.ShaderStateShard POSITION_COLOR_TEX_LIGHTMAP_SHADER = new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorTexLightmapShader);
    protected static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public final Lazy<RenderType> renderType,
            renderTypeWithColor,
            renderTypeWithTransparency,
            renderTypeWithColorTransparency,
            renderTypeWithColorLightmap,
            renderTypeWithColorLightmapTransparency;

    public RenderTypes(ResourceLocation texture) {
        renderType = Lazy.of(() -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(POSITION_TEX_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                    .createCompositeState(false);
            return RenderType.create(texture.toString(), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS,
                    256, false, false, state);
        });
        renderTypeWithColor = Lazy.of(() -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(POSITION_COLOR_TEX_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                    .createCompositeState(false);
            return RenderType.create(texture.toString(), DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS,
                    256, false, false, state);
        });
        renderTypeWithTransparency = Lazy.of(() -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(POSITION_TEX_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(texture, true, false))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .createCompositeState(false);
            return RenderType.create(texture.toString(), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS,
                    256, false, false, state);
        });
        renderTypeWithColorTransparency = Lazy.of(() -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(POSITION_COLOR_TEX_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(texture, true, false))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .createCompositeState(false);
            return RenderType.create(texture.toString(), DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS,
                    256, false, false, state);
        });
        renderTypeWithColorLightmap = Lazy.of(() -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                    .createCompositeState(false);
            return RenderType.create(texture.toString(), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS,
                    256, false, false, state);
        });
        renderTypeWithColorLightmapTransparency = Lazy.of(() -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(texture, true, false))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .createCompositeState(false);
            return RenderType.create(texture.toString(), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS,
                    256, false, false, state);
        });
    }
}
