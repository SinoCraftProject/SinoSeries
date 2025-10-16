package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinobrush.network.Common2FanLines;
import games.moegirl.sinocraft.sinocore.gui.menu.IExtraDataMenuProvider;
import games.moegirl.sinocraft.sinocore.gui.menu.MenuHelper;
import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
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

    public static void damage(ItemStack stack, Player player, EquipmentSlot slot) {
        if (!player.isCreative()) {
            stack.hurtAndBreak(1, player, slot);
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            // Fan editing.
            var offHand = player.getOffhandItem();
            if (usedHand == InteractionHand.MAIN_HAND && offHand.is(SBRItems.FAN.get())) {
                NetworkManager.send(new Common2FanLines(FanItem.getLines(offHand)), serverPlayer);
                return InteractionResultHolder.success(player.getItemInHand(usedHand));
            }

            // Brush.
            var slot = usedHand == InteractionHand.MAIN_HAND ? player.getInventory().selected : Inventory.SLOT_OFFHAND;
            MenuHelper.openMenuWithData(serverPlayer, new IExtraDataMenuProvider() {
                @Override
                public void writeExtraData(RegistryFriendlyByteBuf buf) {
                    buf.writeVarInt(slot);
                }

                @Override
                public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return new BrushMenu(i, inventory, slot);
                }
            });
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }

        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }
}
