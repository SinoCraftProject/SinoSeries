package games.moegirl.sinocraft.sinocore.datagen.copy.loottable.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;

import java.util.Calendar;
import java.util.Optional;

public class ParityDaysTrigger extends SimpleCriterionTrigger<ParityDaysTrigger.TriggerInstance> {
    private final ResourceLocation id;

    private int even = -1;

    public ParityDaysTrigger(ResourceLocation idIn) {
        id = idIn;
    }

    public ParityDaysTrigger(ResourceLocation idIn, boolean isEven) {
        id = idIn;
        even = isEven ? 1 : 0;
    }

    public ResourceLocation getId() {
        return id;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext deserializationContext) {
        if (even == 0) {
            return new TriggerInstance(getId(), player.get(), false);
        } else if (even == 1) {
            return new TriggerInstance(getId(), player.get(), true);
        }

        return new TriggerInstance(getId(), player.get(), json.get("isEven").getAsBoolean());
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final boolean even;

        public TriggerInstance(ResourceLocation id, ContextAwarePredicate contextAwarePredicate, boolean isEven) {
            super(Optional.ofNullable(contextAwarePredicate));
            even = isEven;
        }

        public boolean test() {
            var date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

            if (even) {
                return date % 2 == 0;
            } else {
                return date % 2 != 0;
            }
        }

        @Override
        public JsonObject serializeToJson() {
            JsonObject json = super.serializeToJson();
            json.addProperty("isEven", even);
            return json;
        }
    }
}
