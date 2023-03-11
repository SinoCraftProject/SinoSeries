package games.moegirl.sinocraft.sinocore.old.utility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public class NbtUtils {
    
    public static void saveContainer(CompoundTag tag, String name, Container container) {
        ListTag list = new ListTag();
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack item = container.getItem(i);
            if (item.isEmpty()) {
                continue;
            }
            CompoundTag itemTag = new CompoundTag();
            itemTag.putByte("Slot", (byte) i);
            item.save(itemTag);
            list.add(itemTag);
        }
        tag.put(name, list);
    }

    public static void loadContainer(CompoundTag tag, String name, Container container) {
        container.clearContent();
        for (Tag t : tag.getList(name, Tag.TAG_COMPOUND)) {
            CompoundTag itemTag = (CompoundTag) t;
            int slot = itemTag.getByte("Slot") & 255;
            ItemStack stack = ItemStack.of(itemTag);
            container.setItem(slot, stack);
        }
    }

    public static <T extends INBTSerializable<NBT>, NBT extends Tag> void saveList(CompoundTag tag, String name, T[] container) {
        ListTag list = new ListTag();
        for (T t : container) {
            list.add(t.serializeNBT());
        }
        tag.put(name, list);
    }

    public static <T extends INBTSerializable<NBT>, NBT extends Tag> void saveList(CompoundTag tag, String name, List<T> container) {
        ListTag list = new ListTag();
        for (T t : container) {
            list.add(t.serializeNBT());
        }
        tag.put(name, list);
    }

    public static <T extends INBTSerializable<NBT>, NBT extends Tag> void loadList(CompoundTag tag, String name, T[] container) {
        if (tag.contains(name, Tag.TAG_LIST)) {
            ListTag list = (ListTag) tag.get(name);
            assert list != null;
            int count = Math.min(container.length, list.size());
            for (int i = 0; i < count; i++) {
                container[i].deserializeNBT((NBT) list.get(i));
            }
        }
    }

    public static <T extends INBTSerializable<NBT>, NBT extends Tag> void loadList(CompoundTag tag, String name, List<T> container) {
        if (tag.contains(name, Tag.TAG_LIST)) {
            ListTag list = (ListTag) tag.get(name);
            assert list != null;
            int count = Math.min(container.size(), list.size());
            for (int i = 0; i < count; i++) {
                container.get(i).deserializeNBT((NBT) list.get(i));
            }
        }
    }
}
