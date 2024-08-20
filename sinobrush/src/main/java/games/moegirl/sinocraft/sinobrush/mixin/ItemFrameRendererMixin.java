package games.moegirl.sinocraft.sinobrush.mixin;

import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrameRenderer.class)
public abstract class ItemFrameRendererMixin {
    @Shadow @Final private static ModelResourceLocation MAP_FRAME_LOCATION;

    @Shadow @Final private static ModelResourceLocation GLOW_MAP_FRAME_LOCATION;

    @Inject(method = "getFrameModelResourceLoc", at = @At(value = "HEAD"), cancellable = true)
    private void beforeGetFrameModelResourceLoc(ItemFrame entity, ItemStack item,
                                                CallbackInfoReturnable<ModelResourceLocation> cir) {
        if (item.is(SBRItems.FILLED_XUAN_PAPER.get())) {
            cir.setReturnValue(entity.getType() == EntityType.GLOW_ITEM_FRAME ? GLOW_MAP_FRAME_LOCATION : MAP_FRAME_LOCATION);
        }
    }
}
