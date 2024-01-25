package games.moegirl.sinocraft.sinocore.datagen.copy.loottable;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Calendar;

public class ParityDaysCondition implements LootItemCondition {
    public static final Codec<ParityDaysCondition> CODEC = null; // todo NULL
    public static final LootItemConditionType PARITY_DAYS_CONDITION_TYPE = new LootItemConditionType(CODEC);

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
}
