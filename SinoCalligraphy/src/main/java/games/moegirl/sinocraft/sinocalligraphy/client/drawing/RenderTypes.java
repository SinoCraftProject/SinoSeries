package games.moegirl.sinocraft.sinocalligraphy.client.drawing;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class RenderTypes {

    public final static RenderType COLOR_256 = RenderType.create(
            "draw_256", DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS, 256, false, true,
            RenderType.CompositeState.builder().
                    setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorShader))
                    .createCompositeState(false));

    public final static RenderType COLOR_LIGHT_256 = RenderType.create(
            "draw_256_with_light", DefaultVertexFormat.POSITION_COLOR_LIGHTMAP,
            VertexFormat.Mode.QUADS, 256, false, true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorLightmapShader))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(true))
                    .createCompositeState(false));
}
