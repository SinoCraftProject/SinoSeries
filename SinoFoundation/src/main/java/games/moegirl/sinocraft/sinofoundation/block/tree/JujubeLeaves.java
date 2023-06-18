package games.moegirl.sinocraft.sinofoundation.block.tree;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class JujubeLeaves extends LeavesBlock implements ILootableBlock {

    public JujubeLeaves(Tree tree, Properties properties) {
        super(properties);
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        LootItemCondition.Builder fortune = BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F);
        LootPoolSingletonContainer.Builder<?> stick = LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)));
        LootPoolSingletonContainer.Builder<?> jujube = LootItem.lootTableItem(SFDItems.JUJUBE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)));
        return helper.createSilkTouchOrShearsDispatchTable(this, ((LootPoolSingletonContainer.Builder<?>) helper.applyExplosionCondition(false, LootItem.lootTableItem(SFDTrees.JUJUBE.getItem(TreeBlockType.SAPLING))))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, BlockLootables.NORMAL_LEAVES_SAPLING_CHANCES)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(BlockLootables.HAS_NO_SHEARS_OR_SILK_TOUCH)
                        .add(((LootPoolSingletonContainer.Builder<?>) helper.applyExplosionDecay(false, stick)).when(fortune)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(BlockLootables.HAS_NO_SHEARS_OR_SILK_TOUCH)
                        .add(((LootPoolSingletonContainer.Builder<?>) helper.applyExplosionDecay(false, jujube)).when(fortune)));
    }
}
