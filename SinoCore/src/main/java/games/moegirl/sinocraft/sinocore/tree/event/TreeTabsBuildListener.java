package games.moegirl.sinocraft.sinocore.tree.event;

import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class TreeTabsBuildListener {

    private static final EnumMap<TreeBlockType, List<CreativeModeTab>> DEFAULT_TYPES = new EnumMap<>(TreeBlockType.class);

    static {
        for (TreeBlockType type : TreeBlockType.values()) {
            List<CreativeModeTab> defaultTabs = switch (type) {
                case LOG -> List.of(CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
                case LOG_WOOD, STRIPPED_LOG, STRIPPED_LOG_WOOD, PLANKS, SLAB, STAIRS, FENCE ->
                        List.of(CreativeModeTabs.BUILDING_BLOCKS);
                case LEAVES, SAPLING -> List.of(CreativeModeTabs.NATURAL_BLOCKS);
                case DOOR, TRAPDOOR, PRESSURE_PLATE, BUTTON, FENCE_GATE ->
                        List.of(CreativeModeTabs.REDSTONE_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
                case SIGN, WALL_SIGN, HANGING_SIGN, WALL_HANGING_SIGN -> List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS);
                case CHEST -> List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.REDSTONE_BLOCKS);
                case TRAPPED_CHEST -> List.of(CreativeModeTabs.REDSTONE_BLOCKS);
                case BOAT -> List.of(CreativeModeTabs.TOOLS_AND_UTILITIES);
                case CHEST_BOAT -> List.of(CreativeModeTabs.REDSTONE_BLOCKS, CreativeModeTabs.TOOLS_AND_UTILITIES);
                default -> List.of();
            };
            DEFAULT_TYPES.put(type, defaultTabs);
        }
    }

    private final Map<CreativeModeTab, List<RegistryObject<? extends Item>>> tabItems = new HashMap<>();

    public void addDefaultTabs(TreeBlockType type, RegistryObject<? extends Item> item) {
        for (CreativeModeTab tab : DEFAULT_TYPES.get(type))
            tabItems.computeIfAbsent(tab, t -> new ArrayList<>()).add(item);
    }

    @SubscribeEvent
    public void onTabsBuild(CreativeModeTabEvent.BuildContents event) {
        var tab = event.getTab();

        if (tabItems.containsKey(tab)) {
            event.acceptAll(tabItems.get(tab)
                    .stream()
                    .map(RegistryObject::get)
                    .map(ItemStack::new)
                    .toList());
        }
    }
}
