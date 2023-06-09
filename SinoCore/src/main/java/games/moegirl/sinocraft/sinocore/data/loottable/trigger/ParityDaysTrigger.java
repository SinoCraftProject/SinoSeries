package games.moegirl.sinocraft.sinocore.data.loottable.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;

import java.util.Calendar;

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

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate contextAwarePredicate,
                                             DeserializationContext deserializationContext) {
        if (even == 0) {
            return new TriggerInstance(getId(), contextAwarePredicate, false);
        } else if (even == 1) {
            return new TriggerInstance(getId(), contextAwarePredicate, true);
        }

        return new TriggerInstance(getId(), contextAwarePredicate, json.get("isEven").getAsBoolean());
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final boolean even;

        public TriggerInstance(ResourceLocation id, ContextAwarePredicate contextAwarePredicate, boolean isEven) {
            super(id, contextAwarePredicate);
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
        public JsonObject serializeToJson(SerializationContext context) {
            var json = super.serializeToJson(context);
            json.addProperty("isEven", even);
            return json;
        }
    }
}
