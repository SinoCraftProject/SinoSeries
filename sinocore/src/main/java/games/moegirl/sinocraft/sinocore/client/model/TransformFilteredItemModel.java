package games.moegirl.sinocraft.sinocore.client.model;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class TransformFilteredItemModel implements BakedModel {
    protected final BakedModel baseModel;
    protected final List<ItemDisplayContext> except;

    protected TransformFilteredItemModel(BakedModel baseModel, List<ItemDisplayContext> except) {
        this.baseModel = baseModel;
        this.except = except;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return baseModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return baseModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return baseModel.getParticleIcon();
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return baseModel.getOverrides();
    }

    @Override
    public String toString() {
        return "TransformedItemModel[" +
                "baseModel=" + baseModel + ", " +
                "transforms=" + except + ']';
    }

    public BakedModel filterTransforms(ItemDisplayContext transformType) {
        if (except.contains(transformType)) {
            return this;
        }

        return baseModel;
    }

    @ExpectPlatform
    public static TransformFilteredItemModel create(BakedModel baseModel, List<ItemDisplayContext> except) {
        throw new AssertionError();
    }
}
