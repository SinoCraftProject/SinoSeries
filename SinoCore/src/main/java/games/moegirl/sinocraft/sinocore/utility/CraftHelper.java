package games.moegirl.sinocraft.sinocore.utility;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiPredicate;

/**
 * 匹配无序配方的工具类
 *
 * @author luqin2007
 */
public class CraftHelper {

    /**
     * 匹配无序配方
     *
     * @param inputs      输入对象
     * @param ingredients 匹配对象
     * @return 匹配失败返回 null，否则返回每个 slot 对应的匹配对象
     */
    @Nullable
    public static Int2ObjectMap<Ingredient> matchShapeless(Container inputs, Ingredient[] ingredients) {
        return matchShapeless(inputs, ingredients, Ingredient::test);
    }

    /**
     * 匹配无序配方
     *
     * @param inputs      输入对象
     * @param ingredients 匹配对象
     * @return 匹配失败返回 null，否则返回每个 slot 对应的匹配对象
     */
    @Nullable
    public static Int2ObjectMap<Ingredient> matchShapeless(Container inputs, Collection<Ingredient> ingredients) {
        return matchShapeless(inputs, ingredients, Ingredient::test);
    }

    /**
     * 匹配无序配方
     *
     * @param inputs      输入对象
     * @param ingredients 匹配对象
     * @param checker     匹配函数
     * @return 匹配失败返回 null，否则返回每个 slot 对应的匹配对象
     */
    @Nullable
    public static <T> Int2ObjectMap<T> matchShapeless(Container inputs, T[] ingredients, BiPredicate<T, ItemStack> checker) {
        return matchShapeless(inputs, Arrays.asList(ingredients), checker);
    }

    /**
     * @param <T>         判定类型，通常为 Ingredient
     * @param inputs      输入对象
     * @param ingredients 匹配对象
     * @param checker     匹配函数
     * @return 匹配失败返回 null，否则返回每个 slot 对应的匹配对象
     */
    @Nullable
    public static <T> Int2ObjectMap<T> matchShapeless(Container inputs, Collection<T> ingredients, BiPredicate<T, ItemStack> checker) {
        List<T> notFound = new LinkedList<>(ingredients);
        if (notFound.size() > inputs.getContainerSize()) {
            return null;
        }
        Int2ObjectMap<T> result = new Int2ObjectArrayMap<>(notFound.size());
        // loop0
        boolean[] take = new boolean[inputs.getContainerSize()];
        Arrays.fill(take, false);
        Iterator<T> iterator = notFound.iterator();
        while (iterator.hasNext()) {
            T ingredient = iterator.next();
            for (int i = 0; i < take.length; i++) {
                if (take[i]) {
                    continue;
                }
                if (checker.test(ingredient, inputs.getItem(i))) {
                    take[i] = true;
                    result.put(i, ingredient);
                    iterator.remove();
                }
            }
        }
        if (notFound.isEmpty()) {
            return result;
        }

        // loop1: build table
        List<Pair<T, IntList>> list = new LinkedList<>();
        for (T ingredient : notFound) {
            IntList l = new IntArrayList();
            for (int i = 0; i < take.length; i++) {
                if (!take[i]) {
                    continue;
                }
                if (checker.test(ingredient, inputs.getItem(i))) {
                    l.add(i);
                }
            }
            if (l.isEmpty()) {
                return null;
            }
            list.add(Pair.of(ingredient, l));
        }

        // loop2: adjust
        for (Pair<T, IntList> pair : list) {
            IntList second = pair.second();
            IntListIterator itr = second.iterator();
            while (itr.hasNext()) {
                int i = itr.nextInt();
                T exist = result.get(i);
                boolean failed = true;
                for (int t = 0; t < take.length; t++) {
                    if (take[t]) {
                        continue;
                    }
                    if (checker.test(exist, inputs.getItem(t))) {
                        take[t] = true;
                        result.put(t, exist);
                        result.put(i, pair.first());
                        failed = false;
                    }
                }
                if (failed) {
                    return null;
                }
            }
        }

        return result;
    }
}
