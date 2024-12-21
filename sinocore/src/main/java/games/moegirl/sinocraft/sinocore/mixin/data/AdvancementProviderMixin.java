package games.moegirl.sinocraft.sinocore.mixin.data;

import games.moegirl.sinocraft.sinocore.interfaces.bridge.ISinoRenamedProviderBridge;
import net.minecraft.data.advancements.AdvancementProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementProvider.class)
public class AdvancementProviderMixin {

    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    public void injectGetName(CallbackInfoReturnable<String> cir) {
        AdvancementProvider provider = (AdvancementProvider) (Object) this;
        if (provider instanceof ISinoRenamedProviderBridge mp) {
            cir.setReturnValue(mp.sino$getNewName());
        }
    }
}
