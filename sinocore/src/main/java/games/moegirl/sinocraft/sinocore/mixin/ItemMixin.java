package games.moegirl.sinocraft.sinocore.mixin;

import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow public abstract Item asItem();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectConstructor(Item.Properties properties, CallbackInfo ci) {
        // todo 获取注册时的 ModId
        String currentMod = "???";
        properties.sino$getTabs().forEach(p -> RegistryManager
                .obtainTab(p.getKey().location().getNamespace() /* currentMod */)
                .tabItems(p.getKey())
                .addStack(() -> p.getValue().apply(asItem())));
        properties.sino$getTabIcon().forEach((key, icon) -> RegistryManager
                .obtainTab(key.location().getNamespace() /* currentMod */)
                .tabItems(key)
                .setIcon(() -> icon.apply(asItem())));
    }
}
