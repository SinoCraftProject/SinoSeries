package games.moegirl.sinocraft.sinodivination.old.mixin;

import games.moegirl.sinocraft.sinodivination.old.client.WoodenChestRenderer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(Sheets.class)
public abstract class MixinSheets {

    @Inject(method = "getAllMaterials", at = @At("RETURN"))
    private static void injectGetAllMaterials(Consumer<Material> pMaterialConsumer, CallbackInfo ci) {
        WoodenChestRenderer.getAllMaterials(pMaterialConsumer);
    }
}
