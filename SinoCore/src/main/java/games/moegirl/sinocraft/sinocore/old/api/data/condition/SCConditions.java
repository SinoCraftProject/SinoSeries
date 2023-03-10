package games.moegirl.sinocraft.sinocore.old.api.data.condition;

import games.moegirl.sinocraft.sinocore.api.SinoCoreAPI;
import games.moegirl.sinocraft.sinocore.api.data.condition.trigger.QuizSucceedrigger;
import games.moegirl.sinocraft.sinocore.api.data.condition.trigger.ParityDaysTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoCoreAPI.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SCConditions {
    public static final ResourceLocation PARITY_DAYS_ID = new ResourceLocation(SinoCoreAPI.getId(), "parity_days");
    public static final ResourceLocation QUIZ_SUCCEED_ID = new ResourceLocation(SinoCoreAPI.getId(), "quiz_succeed");

    public static final ParityDaysTrigger PARITY_DAYS_TRIGGER = CriteriaTriggers.register(new ParityDaysTrigger(PARITY_DAYS_ID));
    public static final QuizSucceedrigger QUIZ_SUCCEED_TRIGGER = CriteriaTriggers.register(new QuizSucceedrigger(QUIZ_SUCCEED_ID));

    @SubscribeEvent
    public static void onRegisterPredicates(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        Registry.register(Registry.LOOT_CONDITION_TYPE, PARITY_DAYS_ID, ParityDaysCondition.PARITY_DAYS_CONDITION_TYPE);
        Registry.register(Registry.LOOT_CONDITION_TYPE, QUIZ_SUCCEED_ID, QuizSucceedCondition.QUIZ_SUCCEED_TYPE);
    }
}
