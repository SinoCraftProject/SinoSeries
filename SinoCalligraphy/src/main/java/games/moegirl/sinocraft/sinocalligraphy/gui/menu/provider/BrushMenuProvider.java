package games.moegirl.sinocraft.sinocalligraphy.gui.menu.provider;

import games.moegirl.sinocraft.sinocalligraphy.gui.menu.BrushMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BrushMenuProvider implements MenuProvider {
    protected ItemStack stack;

    public BrushMenuProvider(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public Component getDisplayName() {
//        return Component.translatable(SCAConstants.TRANSLATE_BRUSH_MENU_DISPLAY_NAME);
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new BrushMenu(id, inv, player, stack);
    }
}
