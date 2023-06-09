package games.moegirl.sinocraft.sinofoundation.block.entity;

import games.moegirl.sinocraft.sinofoundation.capability.SFDCapabilities;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

public class StoveBlockEntity extends BlockEntity {
    public static final String TAG_HEAT_SOURCE = "heatSource";
    public static final String TAG_COMBUSTIBLE = "combustible";

    public StoveBlockEntity(BlockPos pos, BlockState state) {
        super(SFDBlockEntities.STOVE.get(), pos, state);
    }

    public boolean addFuel(ItemStack fuel) {
        var heatSource = getCapability(SFDCapabilities.HEAT_SOURCE_BLOCK).orElseThrow(RuntimeException::new);
        var combustible = getCapability(SFDCapabilities.COMBUSTIBLE_BLOCK).orElseThrow(RuntimeException::new);

        var burnTime = ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);

        if (combustible.isFullOfAshes()) {
            return false;
        }

        combustible.addBurnTime(burnTime * fuel.getCount());
        if (!combustible.getBurningFuel().is(fuel.getItem())) {
            combustible.setBurningFuel(fuel);
        }
        combustible.addAsh(getAsh(fuel));
        heatSource.changeHeatTo(getHeatValue(fuel));

        return true;
    }

    public static ItemStack getAsh(ItemStack itemStack) {
        // Todo: qyl27: ashes different?
        var result = new ItemStack(SFDItems.ASHES.get());
        result.setCount(itemStack.getCount());
        return result;
    }

    public static int getHeatValue(ItemStack itemStack) {
        // Todo: qyl27: heat value different! for lava or coal.
        return 800;
    }

    public ItemStack removeAshes() {
        var combustible = getCapability(SFDCapabilities.COMBUSTIBLE_BLOCK).orElseThrow(RuntimeException::new);
        return combustible.takeAsh();
    }

    public void removeAllAshes() {
        var combustible = getCapability(SFDCapabilities.COMBUSTIBLE_BLOCK).orElseThrow(RuntimeException::new);
        combustible.clearAshes();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        var heatSource = getCapability(SFDCapabilities.HEAT_SOURCE_BLOCK).orElseThrow(RuntimeException::new);
        var combustible = getCapability(SFDCapabilities.COMBUSTIBLE_BLOCK).orElseThrow(RuntimeException::new);
        heatSource.deserializeNBT(tag.getCompound(TAG_HEAT_SOURCE));
        combustible.deserializeNBT(tag.getCompound(TAG_COMBUSTIBLE));
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = super.getUpdateTag();
        var heatSource = getCapability(SFDCapabilities.HEAT_SOURCE_BLOCK).orElseThrow(RuntimeException::new);
        var combustible = getCapability(SFDCapabilities.COMBUSTIBLE_BLOCK).orElseThrow(RuntimeException::new);
        tag.put(TAG_HEAT_SOURCE, heatSource.serializeNBT());
        tag.put(TAG_COMBUSTIBLE, combustible.serializeNBT());
        return tag;
    }
}
