package games.moegirl.sinocraft.sinocore.tree.event;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class SCTreeTabsBuildListener {
    private final Map<CreativeModeTab, List<Item>> tabItems;

    public SCTreeTabsBuildListener(Map<CreativeModeTab, List<Item>> tabItems) {
        this.tabItems = tabItems;
    }

    @SubscribeEvent
    public void onTabsBuild(CreativeModeTabEvent.BuildContents event) {
        var tab = event.getTab();

        if (tabItems.containsKey(tab)) {
            event.acceptAll(tabItems.get(tab)
                    .stream()
                    .map(ItemStack::new)
                    .toList());
        }
    }
}
