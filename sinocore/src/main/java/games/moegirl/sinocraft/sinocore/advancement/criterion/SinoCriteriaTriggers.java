package games.moegirl.sinocraft.sinocore.advancement.criterion;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class SinoCriteriaTriggers {
    public static final IRegistry<CriterionTrigger<?>> TRIGGERS = RegistryManager.obtain(SinoCore.MODID, Registries.TRIGGER_TYPE);

    public static final Supplier<PlayerCustomStatTrigger> CUSTOM_STAT_TRIGGER = TRIGGERS.register("custom_stat", PlayerCustomStatTrigger::new);

    public static void register() {
        TRIGGERS.register();
    }
}
