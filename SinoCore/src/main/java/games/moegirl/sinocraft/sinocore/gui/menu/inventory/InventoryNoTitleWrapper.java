package games.moegirl.sinocraft.sinocore.gui.menu.inventory;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class InventoryNoTitleWrapper extends Inventory {
    protected Inventory inv;

    public InventoryNoTitleWrapper(Inventory inv) {
        super(inv.player);

        this.inv = inv;
    }

    @Override
    public Component getName() {
        return Component.empty();
    }
}
