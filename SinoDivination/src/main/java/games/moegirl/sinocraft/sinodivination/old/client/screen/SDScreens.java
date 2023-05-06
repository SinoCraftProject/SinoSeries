package games.moegirl.sinocraft.sinodivination.old.client.screen;

import games.moegirl.sinocraft.sinodivination.old.menu.SDMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class SDScreens {

    public static void register(IEventBus bus) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(SDScreens::onSetup));
    }

    private static void onSetup(FMLClientSetupEvent event) {
        MenuScreens.register(SDMenus.SILKWORM_PLAQUE.get(), SilkwormPlaqueScreen::new);
        MenuScreens.register(SDMenus.CARVING_TABLE.get(), CarvingTableScreen::new);
    }
}
