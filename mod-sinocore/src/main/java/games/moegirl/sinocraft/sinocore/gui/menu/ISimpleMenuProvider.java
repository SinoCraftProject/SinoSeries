package games.moegirl.sinocraft.sinocore.gui.menu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import org.jetbrains.annotations.NotNull;

public interface ISimpleMenuProvider extends MenuProvider {
    @Override
    default @NotNull Component getDisplayName() {
        return Component.empty();
    }
}
