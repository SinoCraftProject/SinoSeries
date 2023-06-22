package games.moegirl.sinocraft.sinodivination.plugin.jei.guihandler;

import games.moegirl.sinocraft.sinodivination.client.screen.SilkwormPlaqueScreen;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.api.gui.handlers.IScreenHandler;
import mezz.jei.gui.GuiProperties;
import net.minecraft.client.Minecraft;

public enum SilkwormPlaqueScreenHandler implements IScreenHandler<SilkwormPlaqueScreen> {

    INSTANCE;

    @Override
    public IGuiProperties apply(SilkwormPlaqueScreen guiScreen) {
        Minecraft mc = Minecraft.getInstance();
        return new GuiProperties(SilkwormPlaqueScreen.class,
                guiScreen.getGuiLeft(), guiScreen.getGuiTop(), guiScreen.width, guiScreen.height,
                mc.getWindow().getScreenWidth(), mc.getWindow().getScreenHeight());
    }
}
