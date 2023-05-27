package games.moegirl.sinocraft.sinotest;

import games.moegirl.sinocraft.sinocore.item.tab.TabsRegistry;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import games.moegirl.sinocraft.sinotest.sinocore.texture.TestTextureItem;
import games.moegirl.sinocraft.sinotest.sinocore.texture.TestTextureMenu;
import games.moegirl.sinocraft.sinotest.sinocore.texture.TestTextureScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(SinoTest.MODID)
public class SinoTest {
    public static final String MODID = "sinotest";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    private static final IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();
    public static TabsRegistry TAB = TabsRegistry.register(new ResourceLocation(MODID, "test_all"), BUS)
            .custom(CreativeModeTab.Builder::withSearchBar)
            .icon(() -> new ItemStack(Items.BAMBOO))
            .add(ITEMS);

    public static Tree TREE = Tree.builder(new ResourceLocation(MODID, "test"))
            .translate("zh_cn", "测试")
            .translate("zh_tw", "測試")
            .translate("en_us", "Test")
            .tab(TAB.name())
            .fillDefaultTabs(TreeBlockType.values())
            .blockTags(BlockTags.create(new ResourceLocation(MODID, "test_tree_blocks")))
            .itemTags(ItemTags.create(new ResourceLocation(MODID, "test_tree_items")))
            .build();

    public static RegistryObject<MenuType<TestTextureMenu>> TEST_TEXTURE_MENU =
            MENUS.register("texture_test", () -> new MenuType<>(TestTextureMenu::new, FeatureFlagSet.of()));
    public static RegistryObject<Item> TEST_TEXTURE_ITEM = ITEMS.register("texture_test_item", TestTextureItem::new);

    public SinoTest() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        BLOCKS.register(bus);
        MENUS.register(bus);
        TreeRegistry.register(MODID, bus, BLOCKS, BLOCK_ENTITY, ITEMS);
        bus.register(this);
        bus.addListener(this::onClientInit);
    }

    public void onClientInit(FMLClientSetupEvent event) {
        MenuScreens.register(TEST_TEXTURE_MENU.get(), TestTextureScreen::new);
    }
}
