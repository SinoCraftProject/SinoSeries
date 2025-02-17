package games.moegirl.sinocraft.sinotest.registry;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static games.moegirl.sinocraft.sinotest.SinoTest.MODID;

public class TestRegistry {

    public static IRegistry<Item> ITEMS;
    public static IRegistry<Block> BLOCKS;
    public static IRegistry<TestRegistryData> TEST_DATA;
    public static ITabRegistry TABS;

    public static ResourceKey<CreativeModeTab> TEST_TAB;
    public static IRegRef<Item> TEST_ITEM_MOD_TAB;
    public static IRegRef<Item> TEST_ITEM_MC_TAB;
    public static IRegRef<Item> TEST_ITEM_MOD_MC_TAB;
    public static IRegRef<Block> TEST_BLOCK;
    public static IRegRef<BlockItem> TEST_BLOCK_ITEM;

    public static void registerAll() {
        ITEMS = RegistryManager.create(MODID, Registries.ITEM);
        BLOCKS = RegistryManager.create(MODID, Registries.BLOCK);
        TABS = RegistryManager.createTab(MODID);
        TEST_DATA = RegistryManager.create(MODID, ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MODID, "test_data")));

        registerTabs();
        registerBlocks();
        registerItems();
        registerCustomRegistry();

        ITEMS.register();
        BLOCKS.register();
        TABS.register();
        TEST_DATA.register();
    }

    private static void registerTabs() {
        TEST_TAB = TABS.register("sinocore_test");
    }

    private static void registerItems() {
        ResourceKey<CreativeModeTab> BUILDING_BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.withDefaultNamespace("building_blocks"));

        TEST_ITEM_MOD_TAB = ITEMS.register("test_item_mod_tab", () ->
                new Item(new Item.Properties().sino$tab(TEST_TAB, true)));
        TEST_ITEM_MC_TAB = ITEMS.register("test_item_mc_tab", () ->
                new Item(new Item.Properties().sino$tab(BUILDING_BLOCKS)));
        TEST_ITEM_MOD_MC_TAB = ITEMS.register("test_item_mod_mc_tab", () ->
                new Item(new Item.Properties().sino$tab(TEST_TAB).sino$tab(BUILDING_BLOCKS)));
    }

    private static void registerBlocks() {
        TEST_BLOCK = BLOCKS.register("test_block", () -> new Block(BlockBehaviour.Properties.of()));
        TEST_BLOCK_ITEM = ITEMS.register("test_block", () ->
                new BlockItem(TEST_BLOCK.get(), new Item.Properties().sino$tab(TEST_TAB)));
    }

    private static void registerCustomRegistry() {
        TEST_DATA.register("test_hello", () -> new TestRegistryData("Hello"));
        TEST_DATA.register("test_sino", () -> new TestRegistryData(MODID));
    }
}
