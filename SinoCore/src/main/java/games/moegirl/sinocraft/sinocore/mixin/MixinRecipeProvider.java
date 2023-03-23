package games.moegirl.sinocraft.sinocore.mixin;

import games.moegirl.sinocraft.sinocore.mixin_inter.INamedProvider;
import net.minecraft.data.recipes.RecipeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author luqin2007
 */
@Mixin(RecipeProvider.class)
public abstract class MixinRecipeProvider {

    /**
     * 对于实现了 {@link INamedProvider} 的 RecipeProvider，允许 getName() 方法返回其他值以防冲突
     */
    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    private void injectGetName(CallbackInfoReturnable<String> cancel) {
        if (((RecipeProvider) (Object) this) instanceof INamedProvider provider) {
            cancel.setReturnValue(provider.sinocoreGetName());
            cancel.cancel();
        }
    }
}
