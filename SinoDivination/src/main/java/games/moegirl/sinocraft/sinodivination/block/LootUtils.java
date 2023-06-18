package games.moegirl.sinocraft.sinodivination.block;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.Supplier;

/**
 * 关于方块掉落物的若干方法
 */
public class LootUtils {

    /**
     * 掉落物：单个物品
     * @param item 物品
     */
    public static LootPoolSingletonContainer.Builder<?> item(ItemLike item) {
        return LootItem.lootTableItem(item);
    }

    /**
     * 掉落物：单个物品
     * @param item 物品
     */
    public static LootPoolSingletonContainer.Builder<?> item(Supplier<? extends ItemLike> item) {
        return LootItem.lootTableItem(item.get());
    }

    /**
     * 掉落物：多个物品
     * @param item 物品
     * @param min 数目最小值
     * @param max 数目最大值
     */
    public static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int min, int max) {
        return LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(range(min, max)));
    }

    /**
     * 掉落物：多个物品
     * @param item 物品
     * @param min 数目最小值
     * @param max 数目最大值
     */
    public static LootPoolSingletonContainer.Builder<?> item(Supplier<? extends ItemLike> item, int min, int max) {
        return LootItem.lootTableItem(item.get()).apply(SetItemCountFunction.setCount(range(min, max)));
    }

    /**
     * 掉落物数量：数量范围
     * @param min 最小值
     * @param max 最大值
     */
    public static NumberProvider range(int min, int max) {
        if (min > max) {
            int tmp = max;
            max = min;
            min = tmp;
        }
        return min == max ? ConstantValue.exactly(max) : UniformGenerator.between(min, max);
    }
}
