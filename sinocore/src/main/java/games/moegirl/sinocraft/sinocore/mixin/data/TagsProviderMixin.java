package games.moegirl.sinocraft.sinocore.mixin.data;

import games.moegirl.sinocraft.sinocore.interfaces.bridge.ISinoRenamedProviderBridge;
import net.minecraft.data.tags.TagsProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TagsProvider.class)
public abstract class TagsProviderMixin {

    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    public void injectGetName(CallbackInfoReturnable<String> cir) {
        TagsProvider<?> provider = (TagsProvider<?>) (Object) this;
        if (provider instanceof ISinoRenamedProviderBridge mp) {
            cir.setReturnValue(mp.sino$getNewName());
        }
    }
}
