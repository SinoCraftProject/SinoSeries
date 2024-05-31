package games.moegirl.sinocraft.sinocore.advancement.criterion;

import net.minecraft.advancements.CriteriaTriggers;

public class SinoCriteriaTriggers {
    public static final CustomStatTrigger CUSTOM_STAT_TRIGGER = new CustomStatTrigger();

    public static void register() {
        CriteriaTriggers.register(CUSTOM_STAT_TRIGGER);
    }
}
