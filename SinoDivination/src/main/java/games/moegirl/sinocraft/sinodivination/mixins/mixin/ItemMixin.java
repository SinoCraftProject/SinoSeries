package games.moegirl.sinocraft.sinodivination.mixins.mixin;

import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import games.moegirl.sinocraft.sinodivination.mixins.interfaces.IItemPropertiesTab;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author luqin2007
 */
@Mixin(Item.class)
public abstract class ItemMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    protected void injectConstructor(Item.Properties properties, CallbackInfo callback) {
        for (TabItemGenerator generator : ((IItemPropertiesTab) properties).sinodivination$getTabs()) {
            generator.addItem(((Item) (Object) this));
        }

        for (TabItemGenerator generator : ((IItemPropertiesTab) properties).sinodivination$getTabIcons()) {
            generator.addItemAsIcon(((Item) (Object) this));
        }
    }
}
