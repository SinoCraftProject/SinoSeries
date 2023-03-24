package games.moegirl.sinocraft.sinocore.tree.event;

import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SCTreeTabsBuildListener {
    private final String modid;

    private boolean hasInit = false;
    private Map<CreativeModeTab, List<Item>> tabItems;

    public SCTreeTabsBuildListener(String modid) {
        this.modid = modid;
    }

    @SubscribeEvent
    public void onTabsBuild(CreativeModeTabEvent.BuildContents event) {
        if (!hasInit) {
            tabItems = getTabItems(modid);
            hasInit = true;
        }

        var tab = event.getTab();

        if (tabItems.containsKey(tab)) {
            event.acceptAll(tabItems.get(tab)
                    .stream()
                    .map(ItemStack::new)
                    .toList());
        }
    }

    private static Map<CreativeModeTab, List<Item>> getTabItems(String modid) {
        var map = new HashMap<CreativeModeTab, List<Item>>();
        TreeRegistry.getRegistry().get(modid)
                .forEach(tree -> {
                    for (var entry : tree.getTabItems().entrySet()) {
                        var tab = entry.getKey();

                        if (!map.containsKey(tab)) {
                            map.put(tab, new ArrayList<>());
                        }

                        map.get(tab).addAll(entry.getValue().stream().map(Supplier::get).toList());
                    }
                });
        return map;
    }
}
