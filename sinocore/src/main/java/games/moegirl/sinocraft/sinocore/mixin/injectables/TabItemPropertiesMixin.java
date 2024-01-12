package games.moegirl.sinocraft.sinocore.mixin.injectables;

import games.moegirl.sinocraft.sinocore.injectables.ITabItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.Properties.class)
public abstract class TabItemPropertiesMixin implements ITabItemProperties {
    @Override
    public final Item.Properties sino$tabs(ResourceKey<CreativeModeTab> tab) {
        // Todo: qyl27: waiting for dev/register.
        return (Item.Properties) (Object) this;
    }
}
