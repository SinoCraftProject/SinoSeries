package games.moegirl.sinocraft.sinobrush.item;

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
        var item = player.getItemInHand(usedHand);
        var offhandItem = player.getOffhandItem();

        // Todo: qyl27: open the GUI.
        // qyl27: The argument "limitedTag" is true in general.
        // lq2007: writeItem equals to writeItemStack(item, true)
        if (!level.isClientSide) {
            if (offhandItem.is(SBRItems.FAN.get())) {

            }
//            NetworkHooks.openScreen((ServerPlayer) player, new BrushMenuProvider(item),
//                    (FriendlyByteBuf byteBuf) -> byteBuf.writeItem(item));
        }

        return InteractionResultHolder.success(item);
    }
}
