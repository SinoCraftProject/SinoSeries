package games.moegirl.sinocraft.sinocore.mixin.data;

import games.moegirl.sinocraft.sinocore.interfaces.bridge.ISinoRenamedProviderBridge;
import net.minecraft.data.recipes.RecipeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeProvider.class)
public abstract class RecipeProviderMixin {

    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    public void injectGetName(CallbackInfoReturnable<String> cir) {
        RecipeProvider provider = (RecipeProvider) (Object) this;
        if (provider instanceof ISinoRenamedProviderBridge mp) {
            cir.setReturnValue(mp.sino$getNewName());
        }
    }
}
