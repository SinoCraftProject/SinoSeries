package games.moegirl.sinocraft.sinocore.item.tab;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

/**
 * @author luqin2007
 */
public class TabsRegistryEnd implements TabsRegistry {

    private final ResourceLocation name;
    private final CreativeModeTab tab;

    TabsRegistryEnd(ResourceLocation name, CreativeModeTab tab) {
        this.name = name;
        this.tab = tab;
    }

    @Override
    public ResourceLocation name() {
        return name;
    }

    @Override
    public CreativeModeTab tab() {
        return tab;
    }
}
