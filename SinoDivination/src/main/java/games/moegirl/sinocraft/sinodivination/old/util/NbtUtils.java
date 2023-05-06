package games.moegirl.sinocraft.sinodivination.old.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class NbtUtils {

    public static <T extends INBTSerializable<NBT>, NBT extends Tag> void saveList(CompoundTag tag, String name, T[] container) {
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
}
