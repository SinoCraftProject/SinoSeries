package games.moegirl.sinocraft.sinodivination.mixins.mixin;

import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import games.moegirl.sinocraft.sinodivination.mixins.interfaces.IItemPropertiesTab;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luqin2007
 */
@Mixin(Item.Properties.class)
public abstract class ItemPropertiesMixin implements IItemPropertiesTab {

    private final List<TabItemGenerator> sinodivination$tabs = new ArrayList<>();
    private final List<TabItemGenerator> sinodivination$tabIcons = new ArrayList<>();

    @Override
    public void sinodivination$addTab(TabItemGenerator tab) {
        sinodivination$tabs.add(tab);
    }

    @Override
    public void sinodivination$addTabAsIcon(TabItemGenerator tab) {
        sinodivination$tabIcons.add(tab);
    }

    @Override
    public List<TabItemGenerator> sinodivination$getTabs() {
        return sinodivination$tabs;
    }

    @Override
    public List<TabItemGenerator> sinodivination$getTabIcons() {
        return sinodivination$tabIcons;
    }
}
