package games.moegirl.sinocraft.sinocore.mixins.mixin;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    /**
     * Add ": SinoSeries Dev" when debugging, ensure mixin is loading
     */
    @Inject(method = "createTitle", at = @At("RETURN"), cancellable = true)
    private void sinocore$injectCreateTitle(CallbackInfoReturnable<String> cir) {
        if (SinoCore.DEBUG) {
            cir.setReturnValue(cir.getReturnValue() + ": SinoSeries Dev");
        }
    }
}
