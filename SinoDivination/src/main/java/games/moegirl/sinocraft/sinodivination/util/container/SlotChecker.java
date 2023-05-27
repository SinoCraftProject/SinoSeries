package games.moegirl.sinocraft.sinodivination.util.container;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

/**
 * 用于测试 Container 中是否可以操作某个槽位物品
 */
public interface SlotChecker {

    SlotChecker TRUE = (_1, _2) -> true;

    boolean check(int slot, ItemStack stack);

//    static SlotChecker not(SlotChecker another) {
//        return (_1, _2) -> !another.check(_1, _2);
//    }

    static SlotChecker and(SlotChecker a, SlotChecker b) {
        return (i, s) -> a.check(i, s) && b.check(i, s);
    }
//
//    static SlotChecker or(SlotChecker a, SlotChecker b) {
//        return (i, s) -> a.check(i, s) || b.check(i, s);
//    }

    static SlotChecker of(Supplier<? extends ItemLike> item) {
        return (__, s) -> s.is(item.get().asItem());
    }

//    static SlotChecker of(ItemLike item) {
//        return (__, s) -> s.is(item.asItem());
//    }

    static SlotChecker of(TagKey<Item> tag) {
        return (__, s) -> s.is(tag);
    }

//    static SlotChecker of(int slot, Supplier<? extends ItemLike> item) {
//        return (i, s) -> i != slot || s.is(item.get().asItem());
//    }
//
//    static SlotChecker of(int slot, ItemLike item) {
//        return (i, s) -> i != slot || s.is(item.asItem());
//    }
//
//    static SlotChecker of(int slot, TagKey<Item> tag) {
//        return (i, s) -> i != slot && s.is(tag);
//    }
//
//    static SlotChecker not(int slot) {
//        return (i, __) -> i != slot;
//    }
//
//    static SlotChecker not(Supplier<? extends ItemLike> item) {
//        return (__, s) -> !s.is(item.get().asItem());
//    }
//
//    static SlotChecker not(ItemLike item) {
//        return (__, s) -> !s.is(item.asItem());
//    }
//
//    static SlotChecker not(TagKey<Item> tag) {
//        return (__, s) -> !s.is(tag);
//    }
//
//    static SlotChecker not(int slot, Supplier<? extends ItemLike> item) {
//        return (i, s) -> i != slot || !s.is(item.get().asItem());
//    }
//
//    static SlotChecker not(int slot, ItemLike item) {
//        return (i, s) -> i != slot || !s.is(item.asItem());
//    }
//
//    static SlotChecker not(int slot, TagKey<Item> tag) {
//        return (i, s) -> i != slot && !s.is(tag);
//    }

}
