package games.moegirl.sinocraft.sinodivination.old.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TagSerializers {

    public static void writeDate(LocalDateTime value, CompoundTag tag) {
        tag.putLong("date", value.toLocalDate().toEpochDay());
        tag.putLong("time", value.toLocalTime().getNano());
    }

    public static LocalDateTime readDate(CompoundTag tag) {
        long date = tag.getLong("date");
        long time = tag.getLong("time");
        return LocalDateTime.of(LocalDate.ofEpochDay(date), LocalTime.ofNanoOfDay(time));
    }

    public static StringTag componentSerializer(Component text) {
        return StringTag.valueOf(Component.Serializer.toJson(text));
    }
}
