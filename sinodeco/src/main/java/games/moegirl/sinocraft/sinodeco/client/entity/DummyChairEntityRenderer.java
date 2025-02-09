package games.moegirl.sinocraft.sinodeco.client.entity;

import games.moegirl.sinocraft.sinodeco.entity.DummyChairEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DummyChairEntityRenderer extends EntityRenderer<DummyChairEntity> {
    public DummyChairEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRender(DummyChairEntity livingEntity, Frustum camera,
                                double camX, double camY, double camZ) {
        return false;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DummyChairEntity entity) {
        return ResourceLocation.withDefaultNamespace("missingno");
    }
}
