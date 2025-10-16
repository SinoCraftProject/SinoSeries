package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.data.gen.loot.AbstractLootTableProvider;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LootTableProviderDelegateBase;

public class TestLootTableProvider extends AbstractLootTableProvider {

    public TestLootTableProvider(DataGenContext context) {
        super(context);
    }

    @Override
    public void generateData(LootTableProviderDelegateBase delegate) {
        // Todo: qyl27: fix it, broken in update to 1.21.
//        IBlockLootTableSubProvider blocks = delegate.getBlocks();
//        delegate.addBlock(TestRegistry.TEST_BLOCK, blocks.createSelfDropWithConditionTable(TestRegistry.TEST_BLOCK.get(),
//                blocks.hasSilkTouch(),
//                LootItem.lootTableItem(TestRegistry.TEST_ITEM_MC_TAB.get())
//                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5)))));
    }
}
