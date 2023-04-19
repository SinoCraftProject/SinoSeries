package games.moegirl.sinocraft.sinocalligraphy.gui;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.gui.menu.BrushMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCAMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SinoCalligraphy.MODID);

    public static final RegistryObject<MenuType<BrushMenu>> BRUSH = MENUS.register("chinese_brush", () -> IForgeMenuType.create(BrushMenu::new));

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
