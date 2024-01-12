package games.moegirl.sinocraft.sinocore.injectables;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public interface ITabItemProperties {
    Item.Properties sino$tabs(ResourceKey<CreativeModeTab> tab);
}
