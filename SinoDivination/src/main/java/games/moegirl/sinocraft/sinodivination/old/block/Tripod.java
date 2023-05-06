package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinodivination.old.block.base.AltarBlock;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.old.blockentity.TripodEntity;
import net.minecraft.world.item.ItemStack;

public class Tripod extends AltarBlock<TripodEntity> {

    public Tripod() {
        super(SDBlockEntities.TRIPOD);
    }

    @Override
    public ItemStack takeItem(TripodEntity be) {
        return be.takeItem();
    }

    @Override
    public ItemStack putItem(TripodEntity be, ItemStack stack) {
        return be.putItem(stack);
    }
}
