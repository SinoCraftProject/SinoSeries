package games.moegirl.sinocraft.sinocore.item.tab;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public interface ITabItem {

    /**
     * 允许一个物品在不同物品栏（比如原版原木在建筑方块和自然方块两个栏目中）
     */
    List<ResourceLocation> getTabs();
}
