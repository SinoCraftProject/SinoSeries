package games.moegirl.sinocraft.sinocore.mixin.item;

import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow
    public abstract Item asItem();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectConstructor(Item.Properties properties, CallbackInfo ci) {
        properties.sino$getTabs().forEach(p -> RegistryManager
                .getRegistries(p.getKey().location().getNamespace(), Registries.CREATIVE_MODE_TAB)
                .stream()
                .map(t -> ((ITabRegistry) t))
                .forEach(t -> t.tabItems(p.getKey())
                        .addStack(() -> p.getValue().apply(asItem()))));
        properties.sino$getTabIcon().forEach((key, icon) -> RegistryManager
                .getRegistries(key.location().getNamespace(), Registries.CREATIVE_MODE_TAB)
                .stream()
                .map(t -> ((ITabRegistry) t))
                .forEach(t -> t.tabItems(key)
                        .setIcon(() -> icon.apply(asItem()))));
    }
}
