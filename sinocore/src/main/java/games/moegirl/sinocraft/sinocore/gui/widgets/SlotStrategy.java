package games.moegirl.sinocraft.sinocore.gui.widgets;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface SlotStrategy<C extends Container> {

    static SlotStrategy<Container> blockAll() {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPickup(Player player) {
                return false;
            }

            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        };
    }

    static SlotStrategy<Container> simple() {
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

    static SlotStrategy<Container> noLimit() {
        return Slot::new;
    }

    static SlotStrategy<Container> onlyInsert() {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPickup(Player pPlayer) {
                return false;
            }
        };
    }

    static SlotStrategy<Container> onlyTake() {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        };
    }

    static SlotStrategy<Container> insertFilter(Predicate<ItemStack> test) {
        return (container, slot, x, y) -> new Slot(container, slot, x, y) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return test.test(pStack);
            }
        };
    }

    static SlotStrategy<Container> insertFilter(ItemLike item) {
        return insertFilter(is -> is.is(item.asItem()));
    }

    static SlotStrategy<Container> insertFilter(TagKey<Item> item) {
        return insertFilter(is -> is.is(item));
    }

    static SlotStrategy<Container> insertFilter(Supplier<? extends ItemLike> item) {
        return insertFilter(is -> is.is(item.get().asItem()));
    }

    Slot createSlot(C container, int slot, int x, int y);
}
