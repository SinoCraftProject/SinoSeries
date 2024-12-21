package games.moegirl.sinocraft.sinocore.mixin.data;

import games.moegirl.sinocraft.sinocore.interfaces.bridge.ISinoRenamedProviderBridge;
import net.minecraft.data.loot.LootTableProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootTableProvider.class)
public class LootTableProviderMixin {

    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    public void injectGetName(CallbackInfoReturnable<String> cir) {
        LootTableProvider provider = (LootTableProvider) (Object) this;
        if (provider instanceof ISinoRenamedProviderBridge mp) {
            cir.setReturnValue(mp.sino$getNewName());
        }
    }
}
