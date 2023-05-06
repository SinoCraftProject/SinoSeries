package games.moegirl.sinocraft.sinocore.utility;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemStackHelper {
    public static ItemStack mergeStack(ItemStack source, ItemStack toMerge) {
        if (toMerge.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int limit = source.getMaxStackSize();

        if (!source.isEmpty())
        {
            if (!ItemHandlerHelper.canItemStacksStack(source, toMerge)) {
                return toMerge;
            }

            limit -= source.getCount();
        }

        if (limit <= 0) {   // qyl27: it cannot merge.
            return toMerge;
        }

        boolean reachedLimit = toMerge.getCount() > limit;

        if (source.isEmpty())
        {
            source.deserializeNBT(toMerge.serializeNBT());
            if (reachedLimit) {
                source.setCount(limit);
            }
        }
        else
        {
            source.grow(reachedLimit ? limit : toMerge.getCount());
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(toMerge, toMerge.getCount() - limit) : ItemStack.EMPTY;
    }
}
