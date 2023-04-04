package games.moegirl.sinocraft.sinocore.mixin.mixins;

import games.moegirl.sinocraft.sinocore.mixin.interfaces.IContainerMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author luqin2007
 */
@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin implements IContainerMenu {

    @Shadow protected abstract Slot addSlot(Slot slot);

    @Override
    public void sinocoreAddSlot(Slot slot) {
        addSlot(slot);
    }
}
