package games.moegirl.sinocraft.sinotest.sinocore.texture;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * @author luqin2007
 */
public class TestTextureItem extends Item {

    public TestTextureItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            player.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.empty();
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new TestTextureMenu(id, inventory);
                }
            });
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(usedHand), level.isClientSide);
    }
}
