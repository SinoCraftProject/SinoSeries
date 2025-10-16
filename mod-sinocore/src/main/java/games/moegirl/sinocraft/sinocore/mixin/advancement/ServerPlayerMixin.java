package games.moegirl.sinocraft.sinocore.mixin.advancement;

import games.moegirl.sinocraft.sinocore.advancement.criterion.SCCriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Inject(at = @At("RETURN"), method = "awardStat")
    private void onAwardStat(Stat<?> stat, int increment, CallbackInfo ci) {
        SCCriteriaTriggers.CUSTOM_STAT_TRIGGER.get().trigger((ServerPlayer) (Object) this);
    }

    @Inject(at = @At("RETURN"), method = "resetStat")
    private void onResetStat(Stat<?> stat, CallbackInfo ci) {
        SCCriteriaTriggers.CUSTOM_STAT_TRIGGER.get().trigger((ServerPlayer) (Object) this);
    }
}
