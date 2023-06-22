package games.moegirl.sinocraft.sinodivination.mixins.interfaces;

import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;

import java.util.List;

/**
 * @author luqin2007
 */
public interface IItemPropertiesTab {

    void sinodivination$addTab(TabItemGenerator tab);

    void sinodivination$addTabAsIcon(TabItemGenerator tab);

    List<TabItemGenerator> sinodivination$getTabs();

    List<TabItemGenerator> sinodivination$getTabIcons();
}
