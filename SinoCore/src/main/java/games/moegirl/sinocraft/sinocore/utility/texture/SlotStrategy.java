package games.moegirl.sinocraft.sinocore.utility.texture;

import games.moegirl.sinocraft.sinocore.gui.menu.slot.TakeOnlySlot;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Predicate;

public interface SlotStrategy<T extends Slot, C extends Container> {

    static SlotStrategy<Slot, Container> simple() {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                for (int i = 0; i < container.getContainerSize(); i++) {
                    if (container.canPlaceItem(i, pStack)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    static SlotStrategy<Slot, Container> noLimit() {
        return Slot::new;
    }

    static SlotStrategy<Slot, Container> onlyInsert() {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPickup(Player pPlayer) {
                return false;
            }
        };
    }

    static SlotStrategy<Slot, Container> onlyTake() {
        return TakeOnlySlot::new;
    }

    static SlotStrategy<Slot, Container> insertFilter(Predicate<ItemStack> test) {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return test.test(pStack);
            }
        };
    }

    static SlotStrategy<Slot, Container> insertFilter(ItemLike item) {
        return insertFilter(is -> is.is(item.asItem()));
    }

    static SlotStrategy<Slot, Container> insertFilter(TagKey<Item> item) {
        return insertFilter(is -> is.is(item));
    }

    static SlotStrategy<Slot, Container> insertFilter(RegistryObject<? extends ItemLike> item) {
        return insertFilter(is -> is.is(item.get().asItem()));
    }

    T createSlot(C container, int slot, int x, int y);
}
