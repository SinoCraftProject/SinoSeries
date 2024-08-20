package games.moegirl.sinocraft.sinocore.mixin.event.game.crafting;

import games.moegirl.sinocraft.sinocore.event.game.CraftingEvents;
import games.moegirl.sinocraft.sinocore.event.game.args.crafting.CartographyTableCraftArgs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CartographyTableMenu.class)
public abstract class CartographyTableMenuMixin extends AbstractContainerMenu {
    @Shadow @Final private ResultContainer resultContainer;

    protected CartographyTableMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    // qyl27: setupResultSlot -> this.access.execute -> method_17382
    @Inject(method = "method_17382", at = @At("RETURN"))
    private void afterSetupResult(ItemStack mapInput, ItemStack otherSlotInput, ItemStack __,
                                  Level level, BlockPos blockPos, CallbackInfo ci) {
        var output = resultContainer.getItem(CartographyTableMenu.RESULT_SLOT);
        var args = CraftingEvents.CARTOGRAPHY_CRAFT.invoke(new CartographyTableCraftArgs(mapInput, otherSlotInput, output));
        if (!args.isCancelled()) {
            resultContainer.setItem(CartographyTableMenu.RESULT_SLOT, args.getOutput());
            broadcastChanges();
        }
    }
}
