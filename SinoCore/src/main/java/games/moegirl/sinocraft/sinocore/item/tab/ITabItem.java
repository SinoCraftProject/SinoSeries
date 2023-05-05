package games.moegirl.sinocraft.sinocore.item.tab;

import games.moegirl.sinocraft.sinocore.utility.Self;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * TODO 若确认删除 List<ResourceLocation> getTabs() 方法，移除泛型， extends Self<T> 和 acceptTabs 的默认实现
 */
public interface ITabItem<T extends ItemLike> extends Self<T> {

    /**
     * 允许一个物品在不同物品栏，或自定义物品栏中的 ItemStack
     */
    default void acceptTabs(BiConsumer<ResourceLocation, ItemStack> accept) {
        for (ResourceLocation tab : getTabs()) {
            accept.accept(tab, new ItemStack(self()));
        }
    }

    /**
     * 允许一个物品在不同物品栏
     *
     * @deprecated 计划替换成 {@link #acceptTabs(BiConsumer)}，为避免出 error 暂时保留
     */
    @Deprecated
    default List<ResourceLocation> getTabs() {
        return List.of();
    }
}
