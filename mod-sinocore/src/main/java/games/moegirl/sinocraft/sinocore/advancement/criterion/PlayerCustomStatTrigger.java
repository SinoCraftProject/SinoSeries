package games.moegirl.sinocraft.sinocore.advancement.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerCustomStatTrigger extends SimpleCriterionTrigger<PlayerCustomStatTrigger.Instance> {

    @Override
    public @NotNull Codec<PlayerCustomStatTrigger.Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> instance.matches(player));
    }

    public record Instance(Optional<ContextAwarePredicate> player, ResourceLocation customStatId, int value) implements SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
                        ResourceLocation.CODEC.fieldOf("customStatId").forGetter(Instance::customStatId),
                        Codec.INT.fieldOf("value").forGetter(Instance::value)
                ).apply(instance, Instance::new));

        public boolean matches(ServerPlayer player) {
            if (!Stats.CUSTOM.contains(customStatId)) {
                return false;
            }

            return player.getStats().getValue(Stats.CUSTOM.get(customStatId)) > value;
        }

        @Override
        public @NotNull Optional<ContextAwarePredicate> player() {
            return player;
        }
    }

    public static Criterion<Instance> awardCustomStat(ResourceLocation id, int value) {
        return SCCriteriaTriggers.CUSTOM_STAT_TRIGGER.get().createCriterion(new Instance(Optional.empty(), id, value));
    }
}
