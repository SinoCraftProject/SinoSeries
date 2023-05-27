package games.moegirl.sinocraft.sinodivination.menu;

import games.moegirl.sinocraft.sinocore.utility.texture.SlotStrategy;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureMap;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.blockentity.SilkwormPlaqueEntity;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class SilkwormPlaqueMenu extends AbstractContainerMenu {

    public static final TextureMap TEXTURE = TextureMap.of(SinoDivination.MODID, "gui", "silkworm_plaque");

    private final SilkwormPlaqueEntity entity;

    public SilkwormPlaqueMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        super(SDMenus.SILKWORM_PLAQUE.get(), containerId);
        entity = (SilkwormPlaqueEntity) Minecraft.getInstance().level.getBlockEntity(buffer.readBlockPos());
        initialize(inventory);
    }

    public SilkwormPlaqueMenu(int containerId, Inventory inventory, SilkwormPlaqueEntity entity) {
        super(SDMenus.SILKWORM_PLAQUE.get(), containerId);
        this.entity = entity;
        initialize(inventory);
    }

    void initialize(Inventory inventory) {
        TEXTURE.placeSlots(this, inventory, "selection", 0, SlotStrategy.noLimit());
        TEXTURE.placeSlots(this, inventory, "inventory", 9, SlotStrategy.noLimit());
        for (int i = 0; i < SilkwormPlaqueEntity.SILKWORM_COUNT; i++) {
            TEXTURE.placeSlot(this, entity.in().getInv(), "in" + i, i, SlotStrategy.insertFilter(SDItems.SILKWORM_BABY));
        }

        TEXTURE.placeSlot(this, entity.out().getInv(), "out", 0, SlotStrategy.onlyTake());
        TEXTURE.placeSlot(this, entity.feed().getInv(), "feed", 0, SlotStrategy.insertFilter(ItemTags.LEAVES));
    }

    // [0, 8]: selection
    // [9, 35]: inventory
    // [36, 43]: in
    // 44: out
    // 45: feed
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = getSlot(index).getItem().copy();
        if (index < 36) {
            // 从背包到 SilkwormPlaque
            if (itemStack.is(SDItems.SILKWORM_BABY.get())) {
                // 蚕
                if (!moveItemStackTo(itemStack, 36, 44, false)) {
                    return ItemStack.EMPTY;
                }
                return itemStack;
            } else if (itemStack.is(ItemTags.LEAVES)) {
                // 树叶
                if (!moveItemStackTo(itemStack, 45, 46, false)) {
                    return ItemStack.EMPTY;
                }
                return itemStack;
            }
        } else {
            // 从 SilkwormPlaque 到背包
            if (!moveItemStackTo(itemStack, 0, 36, true)) {
                return ItemStack.EMPTY;
            }
            return itemStack;
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public SilkwormPlaqueEntity entity() {
        return entity;
    }
}
