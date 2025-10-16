package games.moegirl.sinocraft.sinocore.advancement.criterion;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

@SuppressWarnings("all")
public class SCCriteriaTriggers {
    public static final IRegistry<CriterionTrigger<?>> TRIGGERS = RegistryManager.create(SinoCore.MODID, Registries.TRIGGER_TYPE);

    public static final IRegRef<PlayerCustomStatTrigger> CUSTOM_STAT_TRIGGER = TRIGGERS.register("custom_stat", PlayerCustomStatTrigger::new);

    public static void register() {
        TRIGGERS.register();
    }
}
