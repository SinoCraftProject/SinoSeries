package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.data.gen.advancement.AbstractAdvancementProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.advancement.AdvancementTree;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.AdvancementProviderDelegateBase;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class TestAdvancementProvider extends AbstractAdvancementProvider {

    public TestAdvancementProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    public void generateData(AdvancementProviderDelegateBase delegate) {
        delegate.addAdvancementTree(saver -> new AdvancementTree(saver)
                .root(modLoc("test_adv"), Advancement.Builder.advancement()
                        .display(new ItemStack(TestRegistry.TEST_ITEM_MC_TAB.get()),
                                Component.literal("测试成就"),
                                Component.literal("这是一个用于测试的成就"),
                                null, AdvancementType.TASK, false, true, false)
                        .addCriterion("test", PlayerTrigger.TriggerInstance.tick()))
        );
    }
}
