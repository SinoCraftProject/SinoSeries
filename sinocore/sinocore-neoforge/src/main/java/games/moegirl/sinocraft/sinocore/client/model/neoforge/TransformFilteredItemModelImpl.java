package games.moegirl.sinocraft.sinocore.client.model.neoforge;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.client.model.TransformFilteredItemModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("all")
public class TransformFilteredItemModelImpl extends TransformFilteredItemModel implements BakedModel {
    public TransformFilteredItemModelImpl(BakedModel baseModel, List<ItemDisplayContext> except) {
        super(baseModel, except);
    }

    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext transformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform) {
        return filterTransforms(transformType);
    }

    public static TransformFilteredItemModel create(BakedModel baseModel, List<ItemDisplayContext> except) {
        return new TransformFilteredItemModelImpl(baseModel, except);
    }
}
