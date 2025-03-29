package games.moegirl.sinocraft.sinocore.fabric.mixin.client;

import games.moegirl.sinocraft.sinocore.interfaces.injectable.ISinoItem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin_Fabric_Client implements ISinoItem {

    @Inject(at = @At(value = "TAIL"), method = "<init>")
    private void afterConstructor(CallbackInfo ci) {
        var client = sino$getClientItem();
        if (client != null) {
            var renderer = client.sino$getCustomRender();
            if (renderer != null) {
                BuiltinItemRendererRegistry.INSTANCE.register((Item)(Object)this, renderer::renderByItem);
            }
        }
    }
}
