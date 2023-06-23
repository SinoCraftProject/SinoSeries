package games.moegirl.sinocraft.sinodivination.mixins.mixin;

import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import games.moegirl.sinocraft.sinodivination.util.ItemProperties;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * @author luqin2007
 */
@Mixin(Item.class)
public abstract class ItemMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    protected void injectConstructor(Item.Properties properties, CallbackInfo callback) {
        Pair<List<TabItemGenerator>, List<TabItemGenerator>> pair = ItemProperties.MAP.remove(properties);
        if (pair != null) {
            for (TabItemGenerator generator : pair.getFirst()) {
                generator.addItem(((Item) (Object) this));
            }

            for (TabItemGenerator generator : pair.getSecond()) {
                generator.addItemAsIcon(((Item) (Object) this));
            }
        }
    }
}
