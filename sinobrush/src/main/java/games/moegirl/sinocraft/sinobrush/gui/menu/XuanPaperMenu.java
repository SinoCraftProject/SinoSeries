package games.moegirl.sinocraft.sinobrush.gui.menu;

import games.moegirl.sinocraft.sinobrush.gui.SBRGui;
import games.moegirl.sinocraft.sinocore.gui.WidgetMenuBase;
import games.moegirl.sinocraft.sinocore.gui.widgets.SlotStrategy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * TODO 未完成：Container 的双端同步，两个 container 的作用（应改名）
 */
public class XuanPaperMenu extends WidgetMenuBase {

    private static final ResourceLocation MENU_LOCATION = new ResourceLocation("sinobrush", "textures/gui/xuanpaper");

    // 右侧竖着的两个容器，不知道干啥的
    private final Container rightContainer = new SimpleContainer(2);
    // 右侧下方的容器，不知道干啥的
    private final Container bottomContainer = new SimpleContainer(1);

    public XuanPaperMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(SBRGui.XUAN_PAPER.get(), id, MENU_LOCATION);

        addSlots(inventory, "slots_left", 9, SlotStrategy.simple());
        addSlots(inventory, "slot_bottom", 0, SlotStrategy.simple());
        addSlots(rightContainer, "slots_right", 0, SlotStrategy.simple());
        addSlot(bottomContainer, "slot_right_bottom", 0, SlotStrategy.simple());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
