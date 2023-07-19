package games.moegirl.sinocraft.sinofeast.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.SimpleBlockLootTables;
import games.moegirl.sinocraft.sinocore.data.gen.loottable.LootTableProviderBase;
import games.moegirl.sinocraft.sinofeast.block.SFBlockItems;
import games.moegirl.sinocraft.sinofeast.block.SFBlocks;
import games.moegirl.sinocraft.sinofeast.block.TeaTreeBlock;
import games.moegirl.sinocraft.sinofeast.item.SFItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;

public class SFLootTableProvider extends LootTableProviderBase {
    public SFLootTableProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    public void getTables(List<SubProviderEntry> tables) {
        tables.add(new SubProviderEntry(LootSubProvider::new, LootContextParamSet.builder().build()));
    }

    public static class LootSubProvider extends SimpleBlockLootTables {
        @Override
        protected void generate() {
            add(SFBlocks.TEA_TREE_BLOCK.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(SFItems.TEA_SEED.get())
                                    .when(AnyOfCondition.anyOf(
                                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(SFBlocks.TEA_TREE_BLOCK.get())
                                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TeaTreeBlock.STAGE, 0)),
                                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(SFBlocks.TEA_TREE_BLOCK.get())
                                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TeaTreeBlock.STAGE, 1)),
                                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(SFBlocks.TEA_TREE_BLOCK.get())
                                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TeaTreeBlock.STAGE, 2)),
                                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(SFBlocks.TEA_TREE_BLOCK.get())
                                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TeaTreeBlock.STAGE, 3))
                                    )))
                            .add(LootItem.lootTableItem(Items.STICK)
                                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(SFBlocks.TEA_TREE_BLOCK.get())
                                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TeaTreeBlock.STAGE, 13)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(8))))
                            .add(LootItem.lootTableItem(Items.STICK)
                                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(SFBlocks.TEA_TREE_BLOCK.get())
                                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TeaTreeBlock.STAGE, 14)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                            .add(LootItem.lootTableItem(Items.STICK)
                                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(SFBlocks.TEA_TREE_BLOCK.get())
                                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TeaTreeBlock.STAGE, 15)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                            .add(LootItem.lootTableItem(SFBlockItems.TEA_TREE.get()))
                    ));
        }
    }
}
