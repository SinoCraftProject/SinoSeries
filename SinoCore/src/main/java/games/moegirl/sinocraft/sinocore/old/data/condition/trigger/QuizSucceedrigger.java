package games.moegirl.sinocraft.sinocore.old.data.condition.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;

public class QuizSucceedrigger extends SimpleCriterionTrigger<QuizSucceedrigger.TriggerInstance> {
    private final ResourceLocation id;

    public QuizSucceedrigger(ResourceLocation idIn) {
        id = idIn;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext context) {
        return new TriggerInstance(getId(), player, json.get("answer").getAsString());
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final String ans;

        public TriggerInstance(ResourceLocation id, EntityPredicate.Composite player, String answer) {
            super(id, player);
            ans = answer;
        }

        // Todo: qyl27: need implementation.
        public boolean test() {
            return false;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            var json = super.serializeToJson(context);
            json.addProperty("answer", ans);
            return json;
        }
    }
}
