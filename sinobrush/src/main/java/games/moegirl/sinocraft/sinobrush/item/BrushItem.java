package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinocore.utility.MenuHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BrushItem extends Item {
    public BrushItem() {
        super(new Properties()
                .sino$tab(SBRItems.SINO_BRUSH_TAB)
                .sino$tabIcon(SBRItems.SINO_BRUSH_TAB)
                .durability(127));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide() && player instanceof ServerPlayer sp) {
            MenuHelper.openMenu(sp, (i, inventory, p) -> new BrushMenu(i, inventory, null));
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        return super.use(level, player, usedHand);
    }
}
