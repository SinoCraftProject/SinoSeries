package games.moegirl.sinocraft.sinodivination.menu;

import games.moegirl.sinocraft.sinocore.utility.texture.SlotStrategy;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureMap;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.data.gen.SDTags;
import games.moegirl.sinocraft.sinodivination.recipe.CarvingTableRecipeContainer;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;

public class CarvingTableMenu extends AbstractContainerMenu {

    public static final TextureMap TEXTURE = TextureMap.of(SinoDivination.MODID, "gui", "carving_table");

    public BlockPos pos = BlockPos.ZERO;
    @Nullable
    public Player owner = null;
    private final CarvingTableRecipeContainer container = new CarvingTableRecipeContainer(this);

    public CarvingTableMenu(int pContainerId, Inventory inventory) {
        super(SDMenus.CARVING_TABLE.get(), pContainerId);

        TEXTURE.placeSlots(this, inventory, "selection", 0, SlotStrategy.noLimit());
        TEXTURE.placeSlots(this, inventory, "inventory", 9, SlotStrategy.noLimit());
        TEXTURE.placeSlots(this, container, "input", 0, SlotStrategy.insertFilter(SDTags.SACRIFICIAL_UTENSIL_MATERIAL));
        TEXTURE.placeSlot(this, container, "dye", 16, SlotStrategy.insertFilter(Tags.Items.DYES));
        TEXTURE.placeSlot(this, container, "output", 17, (container, slot, x, y) -> new Slot(container, slot, x, y) {
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);
                container.clearContent();
            }
        });
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void slotsChanged(Container inventory) {
        if (owner != null) {
            SDRecipes.CARVING_TABLE.match(owner.level(), container)
                    .ifPresent(recipe -> container.setOutput(recipe.assemble(container, RegistryAccess.EMPTY)));
        }
        super.slotsChanged(inventory);
    }

    // [0, 8] selection
    // [9, 35] inventory
    // [36, 51] input
    // 52 dye
    // 53 output
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = getSlot(index).getItem().copy();
        if (index < 36) {
            // 染料
            if (itemStack.is(Tags.Items.DYES)) {
                if (!moveItemStackTo(itemStack, 52, 53, false)) {
                    return ItemStack.EMPTY;
                }
            }
            // 原料
            if (!moveItemStackTo(itemStack, 36, 52, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(itemStack, 0, 36, true)) {
                return ItemStack.EMPTY;
            }
        }
        return itemStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (player instanceof ServerPlayer sp) {
            if (player.isAlive() && !sp.hasDisconnected()) {
                container.forInputs(player.getInventory()::placeItemBackInInventory);
            } else {
                container.forInputs(is -> player.drop(is, false));
            }
            container.clearContent();
        }
    }
}
