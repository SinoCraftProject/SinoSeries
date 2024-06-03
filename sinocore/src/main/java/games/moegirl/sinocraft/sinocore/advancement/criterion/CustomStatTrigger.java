package games.moegirl.sinocraft.sinocore.advancement.criterion;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomStatTrigger extends SimpleCriterionTrigger<CustomStatTrigger.Instance> {

    public static final ResourceLocation ID = new ResourceLocation(SinoCore.MODID, "custom_stat");

    @Override
    protected @NotNull Instance createInstance(JsonObject json, ContextAwarePredicate predicate,
                                               DeserializationContext deserializationContext) {
        var stat = new ResourceLocation(json.get("custom_stat").getAsString());
        var higherThan = json.get("value_greater_than").getAsInt();
        return new Instance(stat, higherThan, predicate);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> instance.matches(player));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        @NotNull
        private final ResourceLocation customStat;
        private final int valueGreaterThan;

        public Instance(@NotNull ResourceLocation customStat, int valueGreaterThan, ContextAwarePredicate predicate) {
            super(ID, predicate);
            this.customStat = customStat;
            this.valueGreaterThan = valueGreaterThan;
        }

        public static Instance create(ResourceLocation stat, int greaterThan) {
            return new Instance(stat, greaterThan, ContextAwarePredicate.ANY);
        }

        @Override
        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            var json = super.serializeToJson(context);
            json.addProperty("custom_stat", customStat.toString());
            json.addProperty("value_greater_than", valueGreaterThan);
            return json;
        }

        public boolean matches(ServerPlayer player) {
            if (!Stats.CUSTOM.contains(customStat)) {
                // Fixme: qyl27: Custom stats was not registered!
                return false;
            }

            return player.getStats().getValue(Stats.CUSTOM.get(customStat)) > valueGreaterThan;
        }
    }
}
