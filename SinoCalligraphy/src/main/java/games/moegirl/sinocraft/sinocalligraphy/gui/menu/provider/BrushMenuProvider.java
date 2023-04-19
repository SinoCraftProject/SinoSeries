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
    public static final String DISPLAY_NAME_KEY = "sinocalligraphy.gui.brush.provider.display_name";

    protected ItemStack stack;

    public BrushMenuProvider(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(DISPLAY_NAME_KEY);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new BrushMenu(id, inv, player, stack);
    }
}
