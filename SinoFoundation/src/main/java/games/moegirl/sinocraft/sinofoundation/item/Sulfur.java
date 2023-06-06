package games.moegirl.sinocraft.sinofoundation.item;

import games.moegirl.sinocraft.sinocore.item.tab.ITabItem;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiConsumer;

public class Sulfur extends FireChargeItem implements ITabItem<Sulfur> {

    public Sulfur(Properties properties) {
        super(properties);
    }

    @Override
    public void acceptTabs(BiConsumer<ResourceLocation, ItemStack> consumer) {
        consumer.accept(SinoSeriesTabs.MATERIALS, new ItemStack(self()));
    }
}
