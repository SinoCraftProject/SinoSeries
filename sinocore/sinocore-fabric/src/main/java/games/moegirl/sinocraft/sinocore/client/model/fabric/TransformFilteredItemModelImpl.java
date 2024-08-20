package games.moegirl.sinocraft.sinocore.client.model.fabric;

import games.moegirl.sinocraft.sinocore.client.model.TransformFilteredItemModel;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.impl.renderer.VanillaModelEncoder;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class TransformFilteredItemModelImpl extends TransformFilteredItemModel implements FabricBakedModel {

    public TransformFilteredItemModelImpl(BakedModel baseModel, List<ItemDisplayContext> except) {
        super(baseModel, except);
    }

    @Override
    public @NotNull ItemTransforms getTransforms() {
        return ItemTransforms.NO_TRANSFORMS;
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
        var actual = filterTransforms(context.itemTransformationMode());
        VanillaModelEncoder.emitItemQuads(actual, null, randomSupplier, context);
    }

    public static TransformFilteredItemModel create(BakedModel baseModel, List<ItemDisplayContext> except) {
        return new TransformFilteredItemModelImpl(baseModel, except);
    }
}
