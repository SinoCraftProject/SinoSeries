package games.moegirl.sinocraft.sinocore.advancement.criterion;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.ResourceLocation;

public class SinoCriteriaTriggers {
    public static final CustomStatTrigger CUSTOM_STAT_TRIGGER = register(ResourceLocation.fromNamespaceAndPath(SinoCore.MODID, "custom_stat"), new CustomStatTrigger());

    public static void register() {
    }

    private static <T extends CriterionTrigger<?>> T register(ResourceLocation id, T trigger) {
        CriteriaTriggers.register(id.toString(), trigger);
        return trigger;
    }
}
