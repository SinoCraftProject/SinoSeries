package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.datagen.AbstractAdvancementProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.AdvancementProviderDelegateBase;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class TestAdvancementProvider extends AbstractAdvancementProvider {

    public TestAdvancementProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    public void generateData(AdvancementProviderDelegateBase delegate) {
        delegate.addAdvancement(builder -> builder
                .root("test_adv", Advancement.Builder.advancement()
                        .display(new ItemStack(TestRegistry.TEST_ITEM_MC_TAB.get()),
                                Component.literal("测试成就"),
                                Component.literal("这是一个用于测试的成就"),
                                null, FrameType.TASK, false, true, false))
        );
    }
}
