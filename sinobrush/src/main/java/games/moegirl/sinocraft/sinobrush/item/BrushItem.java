package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinocore.gui.menu.IExtraDataMenuProvider;
import games.moegirl.sinocraft.sinocore.gui.menu.MenuHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BrushItem extends Item {
    public BrushItem() {
        super(new Properties()
                .sino$tab(SBRItems.SINO_BRUSH_TAB, true)
                .durability(127));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide() && player instanceof ServerPlayer sp) {
            var slot = usedHand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            MenuHelper.openMenuWithData(sp, new IExtraDataMenuProvider() {
                @Override
                public void writeExtraData(FriendlyByteBuf buf) {
                    buf.writeEnum(slot);
                }

                @Override
                public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return new BrushMenu(i, inventory, slot);
                }
            });
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        return super.use(level, player, usedHand);
    }
}
