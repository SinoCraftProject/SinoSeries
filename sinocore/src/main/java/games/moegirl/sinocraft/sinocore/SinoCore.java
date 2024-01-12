package games.moegirl.sinocraft.sinocore;

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

import java.util.function.Supplier;

public class SinoCore {
    public static final String MODID = "sinocore";

    public static IRegistry<Item> ITEMS;
    public static IRegistry<Block> BLOCKS;
    public static ITabRegistry TABS;

    public static void registerAll() {
        ITEMS = RegistryManager.obtain(MODID, Registries.ITEM);
        BLOCKS = RegistryManager.obtain(MODID, Registries.BLOCK);
        TABS = RegistryManager.obtainTab(MODID);

        ITEMS.register();
        BLOCKS.register();
        TABS.register();

        registerTabs();
        registerBlocks();
        registerItems();
    }

    public static ResourceKey<CreativeModeTab> TEST_TAB;
    public static Supplier<Item> TEST_ITEM_MOD_TAB;
    public static Supplier<Item> TEST_ITEM_MC_TAB;
    public static Supplier<Item> TEST_ITEM_MOD_MC_TAB;
    public static Supplier<Block> TEST_BLOCK;
    public static Supplier<Item> TEST_BLOCK_ITEM;

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
}
