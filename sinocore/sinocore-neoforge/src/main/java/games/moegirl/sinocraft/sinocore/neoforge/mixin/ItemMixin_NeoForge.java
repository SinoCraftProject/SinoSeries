package games.moegirl.sinocraft.sinocore.neoforge.mixin;

import games.moegirl.sinocraft.sinocore.interfaces.injectable.ISinoItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(Item.class)
public abstract class ItemMixin_NeoForge implements ISinoItem {

    @Inject(at = @At(value = "TAIL"), method = "initializeClient", remap = false)
    private void afterInitClient(Consumer<IClientItemExtensions> consumer, CallbackInfo ci) {
        var renderer = sino$getCustomRender();
        if (renderer != null) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    return renderer;
                }
            });
        }
    }
}
