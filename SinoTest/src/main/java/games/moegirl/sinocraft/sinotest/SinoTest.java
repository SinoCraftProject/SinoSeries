package games.moegirl.sinocraft.sinotest;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import games.moegirl.sinocraft.sinotest.sinocore.texture.TestTextureItem;
import games.moegirl.sinocraft.sinotest.sinocore.texture.TestTextureMenu;
import games.moegirl.sinocraft.sinotest.sinocore.texture.TestTextureScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static CreativeModeTab TAB;

    public static Tree TREE = Tree.builder(new ResourceLocation(MODID, "test"))
            .translate("zh_cn", "测试")
            .translate("zh_tw", "測試")
            .translate("en_us", "Test")
            .build();

    public static RegistryObject<MenuType<TestTextureMenu>> TEST_TEXTURE_MENU =
            MENUS.register("texture_test", () -> new MenuType<>(TestTextureMenu::new, FeatureFlagSet.of()));
    public static RegistryObject<Item> TEST_TEXTURE_ITEM = ITEMS.register("texture_test_item", TestTextureItem::new);

    public SinoTest() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        TreeRegistry.register(MODID, bus);
        ITEMS.register(bus);
        MENUS.register(bus);
        bus.register(this);
        bus.addListener(this::onClientInit);
    }

    public void onClientInit(FMLClientSetupEvent event) {
        MenuScreens.register(TEST_TEXTURE_MENU.get(), TestTextureScreen::new);
    }

    @SubscribeEvent
    public void onTabRegister(CreativeModeTabEvent.Register event) {
        TAB = event.registerCreativeModeTab(new ResourceLocation(MODID, "test_all"), builder -> builder
                .icon(() -> new ItemStack(Items.BAMBOO))
                .withSearchBar()
                .title(Component.literal("SinoTest")));
    }

    @SubscribeEvent
    public void onTabBuilder(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == TAB) {
            event.accept(TEST_TEXTURE_ITEM);
        }
    }
}
