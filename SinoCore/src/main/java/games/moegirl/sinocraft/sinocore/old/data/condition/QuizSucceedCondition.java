package games.moegirl.sinocraft.sinocore.old.data.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class QuizSucceedCondition implements LootItemCondition {
    public static final LootItemConditionType QUIZ_SUCCEED_TYPE =
            new LootItemConditionType(new ConditionSerializer());

    private final boolean isSucceed;
    private final boolean lastCorrect;

    protected QuizSucceedCondition(boolean succeed, boolean last) {
        isSucceed = succeed;
        lastCorrect = last;
    }

    @Override
    public LootItemConditionType getType() {
        return QUIZ_SUCCEED_TYPE;
    }

    @Override
    public boolean test(LootContext context) {
        var entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);

        if (!(entity instanceof Player player)) {
            return false;
        }

        return PlayerQuizzingHelper.isCompleteSuccessfully(player, isSucceed, lastCorrect);
    }

    public static Builder builder(boolean succeed, boolean last) {
        return new Builder(succeed, last);
    }

    public static class Builder implements LootItemCondition.Builder {
        private final boolean isSucceed;
        private final boolean lastCorrect;

        protected Builder(boolean succeed, boolean last) {
            isSucceed = succeed;
            lastCorrect = last;
        }

        @Override
        public LootItemCondition build() {
            return new QuizSucceedCondition(isSucceed, lastCorrect);
        }
    }

    public static class ConditionSerializer implements Serializer<QuizSucceedCondition> {
        @Override
        public void serialize(JsonObject json, QuizSucceedCondition value, JsonSerializationContext serializationContext) {
            json.addProperty("isSucceed", value.isSucceed);
            json.addProperty("lastCorrect", value.lastCorrect);
        }

        @Override
        public QuizSucceedCondition deserialize(JsonObject json, JsonDeserializationContext serializationContext) {
            var isSucceed = json.get("isSucceed").getAsBoolean();
            var lastCorrect = json.get("lastCorrect").getAsBoolean();

            return new QuizSucceedCondition(isSucceed, lastCorrect);
        }
    }
}
