package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.datagen.AbstractLootTableProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.LootTableProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.datagen.loottable.IBlockLootTableSubProvider;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class TestLootTableProvider extends AbstractLootTableProvider {

    public TestLootTableProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    public void generateData(LootTableProviderDelegateBase delegate) {
        IBlockLootTableSubProvider blocks = delegate.getBlocks();
        delegate.addBlock(TestRegistry.TEST_BLOCK, blocks.createSelfDropWithConditionTable(TestRegistry.TEST_BLOCK.get(),
                blocks.hasSilkTouch(),
                LootItem.lootTableItem(TestRegistry.TEST_ITEM_MC_TAB.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5)))));
    }
}
