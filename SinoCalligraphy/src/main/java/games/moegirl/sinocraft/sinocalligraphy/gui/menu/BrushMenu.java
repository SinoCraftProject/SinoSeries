package games.moegirl.sinocraft.sinocalligraphy.gui.menu;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.data.SCAItemTags;
import games.moegirl.sinocraft.sinocalligraphy.gui.SCAMenus;
import games.moegirl.sinocraft.sinocalligraphy.gui.menu.container.BrushContainer;
import games.moegirl.sinocraft.sinocore.gui.menu.slot.RestrictInputSlot;
import games.moegirl.sinocraft.sinocore.gui.menu.slot.TakeOnlySlot;
import games.moegirl.sinocraft.sinocore.utility.texture.SlotStrategy;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BrushMenu extends AbstractContainerMenu {
    public static final ResourceLocation GUI = new ResourceLocation(SinoCalligraphy.MODID, "textures/gui/chinese_brush.png");
    public static final TextureMap TEXTURE = TextureMap.of(GUI);

    protected Inventory playerInv;
    protected Player player;
    protected ItemStack brush;

    protected BrushContainer brushContainer;

    public BrushMenu(int id, Inventory playerInv, Player player, ItemStack brush) {
        super(SCAMenus.BRUSH.get(), id);

        this.playerInv = playerInv;
        this.player = player;
        this.brush = brush;

        brushContainer = new BrushContainer(this);

        TEXTURE.placeSlot(this, brushContainer, "paper", BrushContainer.XUAN_PAPER_SLOT,
                ((container, slot, x, y) -> new RestrictInputSlot(container, slot, x, y, SCAItemTags.PAPERS)));
        TEXTURE.placeSlot(this, brushContainer, "ink", BrushContainer.INK_SLOT,
                ((container, slot, x, y) -> new RestrictInputSlot(container, slot, x, y, SCAItemTags.INKS)));
        TEXTURE.placeSlot(this, brushContainer, "result", BrushContainer.FILLED_XUAN_PAPER_SLOT,
                SlotStrategy.onlyTake());

        TEXTURE.placeSlots(this, playerInv, "inventory", 9, SlotStrategy.noLimit());
        TEXTURE.placeSlots(this, playerInv, "selection_bar", 0, SlotStrategy.noLimit());
    }

    public BrushMenu(int id, Inventory playerInv, FriendlyByteBuf buf) {
        this(id, playerInv, playerInv.player, buf.readItem());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        // Todo: this is had no test yet.
        ItemStack result = ItemStack.EMPTY;

        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();

            result = stackInSlot.copy();
            if (index < 3) {
                if (!moveItemStackTo(stackInSlot, 3, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(stackInSlot, 0, 3, false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getMainHandItem().is(SCAItemTags.BRUSHES)
                || player.getOffhandItem().is(SCAItemTags.BRUSHES);
    }

    public ItemStack getPaper() {
        return brushContainer.getPaper();
    }

    public ItemStack getInk() {
        return brushContainer.getInk();
    }

    protected int brushColorLevel = 0;

    public int getColorLevel() {
        return brushColorLevel;
    }

    public void setColorLevel(int colorLevel) {
        brushColorLevel = Mth.clamp(colorLevel, 0, 15);
    }
}
