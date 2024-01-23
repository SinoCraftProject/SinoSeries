package games.moegirl.sinocraft.sinocore.datagen.copy.loottable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Calendar;

public class ParityDaysCondition implements LootItemCondition {
    public static final LootItemConditionType PARITY_DAYS_CONDITION_TYPE =
            new LootItemConditionType(new ConditionSerializer());

    private final boolean even;

    protected ParityDaysCondition(boolean isEven) {
        even = isEven;
    }

    @Override
    public LootItemConditionType getType() {
        return PARITY_DAYS_CONDITION_TYPE;
    }

    @Override
    public boolean test(LootContext lootContext) {
        var date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        if (even) {
            return date % 2 == 0;
        } else {
            return date % 2 != 0;
        }
    }

    public static Builder builder(boolean isEven) {
        return new Builder(isEven);
    }

    public static class Builder implements LootItemCondition.Builder {
        private final boolean even;

        public Builder(boolean isEven) {
            even = isEven;
        }

        @Override
        public LootItemCondition build() {
            return new ParityDaysCondition(even);
        }
    }

    public static class ConditionSerializer implements Serializer<ParityDaysCondition> {
        @Override
        public void serialize(JsonObject json, ParityDaysCondition value, JsonSerializationContext serializationContext) {
            json.addProperty("isEven", value.even);
        }

        @Override
        public ParityDaysCondition deserialize(JsonObject json, JsonDeserializationContext serializationContext) {
            return new ParityDaysCondition(json.get("isEven").getAsBoolean());
        }
    }

}
