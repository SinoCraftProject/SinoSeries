package games.moegirl.sinocraft.sinocore.mixin;

import games.moegirl.sinocraft.sinocore.mixin_interfaces.injectable.IRenamedProvider;
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
        if (provider instanceof IRenamedProvider mp) {
            cir.setReturnValue(mp.getNewName());
        }
    }
}
