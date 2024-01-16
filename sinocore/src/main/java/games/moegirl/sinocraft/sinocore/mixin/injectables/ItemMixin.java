package games.moegirl.sinocraft.sinocore.mixin.injectables;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectConstructor(Item.Properties properties, CallbackInfo ci) {
        ITabRegistry registry = RegistryManager.obtainTab(SinoCore.MODID);
        properties.sino$getAllTabs().forEach(p -> registry
                .tabItems(p.getLeft())
                .addStack(() -> p.getRight().apply(sino$getThis())));
        properties.sino$getTabIcon().forEach((key, icon) -> RegistryManager
                .obtainTab(key.location().getNamespace())
                .tabItems(key)
                .setIcon(() -> icon.apply(sino$getThis())));
    }

    private Item sino$getThis() {
        return (Item) (Object) this;
    }
}
