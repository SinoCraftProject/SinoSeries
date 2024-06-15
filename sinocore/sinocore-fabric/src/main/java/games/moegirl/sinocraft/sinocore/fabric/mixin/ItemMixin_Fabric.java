package games.moegirl.sinocraft.sinocore.fabric.mixin;

import games.moegirl.sinocraft.sinocore.mixin_interfaces.injectable.ISinoItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin_Fabric implements ISinoItem {

    @Inject(at = @At(value = "TAIL"), method = "<init>")
    private void afterConstructor(CallbackInfo ci) {
        var renderer = sino$getCustomRender();
        if (renderer != null && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            BuiltinItemRendererRegistry.INSTANCE.register((Item)(Object)this, renderer::renderByItem);
        }
    }
}
