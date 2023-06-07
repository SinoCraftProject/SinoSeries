package games.moegirl.sinocraft.sinocore.mixin.mixins;

import games.moegirl.sinocraft.sinocore.data.LootTableProviderBase;
import net.minecraft.data.loot.LootTableProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author luqin2007
 */
@Mixin(LootTableProvider.class)
public abstract class LootTableProviderMixin {

    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    protected void injectGetName(CallbackInfoReturnable<String> callback) {
        LootTableProvider $this = (LootTableProvider) (Object) this;
        if ($this instanceof LootTableProviderBase provider) {
            callback.setReturnValue(provider.getProviderName());
            callback.cancel();
        }
    }
}
