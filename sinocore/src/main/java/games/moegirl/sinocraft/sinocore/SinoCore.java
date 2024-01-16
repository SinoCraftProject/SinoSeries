package games.moegirl.sinocraft.sinocore;

import com.mojang.logging.LogUtils;
import games.moegirl.sinocraft.sinocore.registry.IRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinocore.test.TestRegistryData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class SinoCore {
    public static final String MODID = "sinocore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static IRegistry<Item> ITEMS;
    public static IRegistry<Block> BLOCKS;
    public static IRegistry<TestRegistryData> TEST_DATA;
    public static ITabRegistry TABS;

    public static void registerAll() {
        ITEMS = RegistryManager.obtain(MODID, Registries.ITEM);
        BLOCKS = RegistryManager.obtain(MODID, Registries.BLOCK);
        TABS = RegistryManager.obtainTab(MODID);
        TEST_DATA = RegistryManager.obtain(MODID, ResourceKey.createRegistryKey(new ResourceLocation(MODID, "test_data")));

        registerTabs();
        registerBlocks();
        registerItems();
        registerCustomRegistry();

        ITEMS.register();
        BLOCKS.register();
        TABS.register();
        TEST_DATA.register();
    }

    public static ResourceKey<CreativeModeTab> TEST_TAB;
    public static IRef<Item, Item> TEST_ITEM_MOD_TAB;
    public static IRef<Item, Item> TEST_ITEM_MC_TAB;
    public static IRef<Item, Item> TEST_ITEM_MOD_MC_TAB;
    public static IRef<Block, Block> TEST_BLOCK;
    public static IRef<Item, BlockItem> TEST_BLOCK_ITEM;

    private static void registerTabs() {
        TEST_TAB = TABS.register("sinocore_test");
    }

    private static void registerItems() {
        TEST_ITEM_MOD_TAB = ITEMS.register("test_item_mod_tab", () -> new Item(new Item.Properties()));
        TEST_ITEM_MC_TAB = ITEMS.register("test_item_mc_tab", () -> new Item(new Item.Properties()));
        TEST_ITEM_MOD_MC_TAB = ITEMS.register("test_item_mod_mc_tab", () -> new Item(new Item.Properties()));

        ResourceKey<CreativeModeTab> BUILDING_BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("building_blocks"));

        TABS.tabItems(TEST_TAB).addItemAsIcon(TEST_ITEM_MOD_TAB).addItem(TEST_ITEM_MOD_MC_TAB);
        TABS.tabItems(BUILDING_BLOCKS).addItem(TEST_ITEM_MC_TAB).addItem(TEST_ITEM_MC_TAB);
    }

    private static void registerBlocks() {
        TEST_BLOCK = BLOCKS.register("test_block", () -> new Block(BlockBehaviour.Properties.of()));
        TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItem(TEST_BLOCK.get(), new Item.Properties()));

        TABS.tabItems(TEST_TAB).addItem(TEST_BLOCK_ITEM);
    }

    private static void registerCustomRegistry() {
        TEST_DATA.register("test_hello", () -> new TestRegistryData("Hello"));
        TEST_DATA.register("test_sino", () -> new TestRegistryData(MODID));
    }
}
