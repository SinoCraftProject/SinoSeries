package games.moegirl.sinocraft.sinobrush.mixin;

import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CartographyTableMenu.class)
public abstract class CartographyTableMenuMixin extends AbstractContainerMenu {

    protected CartographyTableMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Mixin(targets = "net/minecraft/world/inventory/CartographyTableMenu$3")
    public static abstract class Slot1 {
        @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
        private void beforeMayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
            if (stack.is(SBRItems.XUAN_PAPER.get())) {
                cir.setReturnValue(true);
            }
        }
    }

    @Mixin(targets = "net/minecraft/world/inventory/CartographyTableMenu$4")
    public static abstract class Slot2 {
        @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
        private void beforeMayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
            if (stack.is(SBRItems.XUAN_PAPER.get())) {
                cir.setReturnValue(true);
            }
        }
    }
}
