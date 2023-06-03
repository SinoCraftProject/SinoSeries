package games.moegirl.sinocraft.sinocore.item;

import games.moegirl.sinocraft.sinocore.item.tab.ITabItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;

public class TabNamedBlockItemBase extends ItemNameBlockItem implements ITabItem<TabNamedBlockItemBase> {
    protected final ResourceLocation[] tabs;

    public TabNamedBlockItemBase(Block block, Properties properties, ResourceLocation... tabs) {
        super(block, properties);
        this.tabs = tabs;
    }

    @Override
    public void acceptTabs(BiConsumer<ResourceLocation, ItemStack> consumer) {
        for (var rl : tabs) {
            consumer.accept(rl, new ItemStack(self()));
        }
    }
}
