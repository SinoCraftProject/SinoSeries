package games.moegirl.sinocraft.sinocore.data.loottable;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SCLootConditions {
    public static final DeferredRegister<LootItemConditionType> CONDITIONS = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, SinoCore.MODID);

    public static final RegistryObject<LootItemConditionType> PARITY_DAYS_TRIGGER = CONDITIONS.register("parity_days", () -> ParityDaysCondition.PARITY_DAYS_CONDITION_TYPE);

    public static void register(IEventBus bus) {
        CONDITIONS.register(bus);
    }
}
