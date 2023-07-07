package games.moegirl.sinocraft.sinocore.mixins.mixin;

import games.moegirl.sinocraft.sinocore.data.loottable.LootTableProviderBase;
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

    @Inject(method = "getName", at = @At("RETURN"), cancellable = true)
    protected void sino$injectGetName(CallbackInfoReturnable<String> cir) {
        LootTableProvider $this = (LootTableProvider) (Object) this;
        if ($this instanceof LootTableProviderBase provider) {
            cir.setReturnValue(provider.getProviderName());
        }
    }
}
