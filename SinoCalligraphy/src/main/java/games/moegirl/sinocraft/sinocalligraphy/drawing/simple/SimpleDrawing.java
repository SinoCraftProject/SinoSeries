package games.moegirl.sinocraft.sinocalligraphy.drawing.simple;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.drawing.data.DrawingDataVersion;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SimpleDrawing implements INBTSerializable<CompoundTag> {
    protected DrawingDataVersion version = DrawingDataVersion.getLatest();

    protected Component title = Component.translatable(SCAConstants.TRANSLATE_DRAWING_TITLE_UNKNOWN_KEY);
    protected Component author = Component.translatable(SCAConstants.TRANSLATE_DRAWING_AUTHOR_UNKNOWN_KEY);
    protected long date = 1680220800;   // 2023.3.31 8:00 by default.

    protected int size = 32;
    protected byte[] pixels = new byte[0];

    public SimpleDrawing() {
        // Nothing here?
    }

    public SimpleDrawing(DrawingDataVersion version, int size) {
        this();
        this.version = version;
        this.size = size;
    }

    public SimpleDrawing(DrawingDataVersion version, int size,
                         Component title, Component author, long date) {
        this(version, size);
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public SimpleDrawing(DrawingDataVersion version, int size,
                         Component title, Component author, long date,
                         byte[] pixels) {
        this(version, size, title, author, date);
        this.pixels = pixels;
    }

    // <editor-fold desc="Getter and Setter.">

    public DrawingDataVersion getVersion() {
        return version;
    }

    public void setVersion(DrawingDataVersion version) {
        this.version = version;
    }

    public void setVersion(int version) {
        setVersion(DrawingDataVersion.fromVersion(version));
    }

    public Component getTitle() {
        return title;
    }

    public void setTitle(Component title) {
        this.title = title;
    }

    public void setTitle(String title) {
        setTitle(Component.literal(title));
    }

    public Component getAuthor() {
        return author;
    }

    public void setAuthor(Component author) {
        this.author = author;
    }

    public void setAuthor(String author) {
        setAuthor(Component.literal(author));
    }

    public void setAuthor(Player author) {
        setAuthor(author.getDisplayName());
    }

    public long getDate() {
        return date;
    }

    public ZonedDateTime getZonedDate() {
        var instant = Instant.ofEpochSecond(getDate());
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setZonedDate(ZonedDateTime date) {
        setDate(date.toEpochSecond());
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getPixels() {
        return pixels;
    }

    public void setPixels(byte[] pixels) {
        this.pixels = pixels;
    }

    // </editor-fold>

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setVersion(tag.getInt(SCAConstants.DRAWING_TAG_VERSION_NAME));
        setTitle(Component.Serializer.fromJson(tag.getString(SCAConstants.DRAWING_TAG_TITLE_NAME)));
        setAuthor(Component.Serializer.fromJson(tag.getString(SCAConstants.DRAWING_TAG_AUTHOR_NAME)));
        setDate(tag.getLong(SCAConstants.DRAWING_TAG_DATE_NAME));
        setSize(tag.getInt(SCAConstants.DRAWING_TAG_SIZE_NAME));
        setPixels(tag.getByteArray(SCAConstants.DRAWING_TAG_PIXELS_NAME));
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        tag.putInt(SCAConstants.DRAWING_TAG_VERSION_NAME, getVersion().getVersionNumber());
        tag.putString(SCAConstants.DRAWING_TAG_TITLE_NAME, Component.Serializer.toJson(getTitle()));
        tag.putString(SCAConstants.DRAWING_TAG_AUTHOR_NAME, Component.Serializer.toJson(getAuthor()));
        tag.putLong(SCAConstants.DRAWING_TAG_DATE_NAME, getDate());
        tag.putInt(SCAConstants.DRAWING_TAG_SIZE_NAME, getSize());
        tag.putByteArray(SCAConstants.DRAWING_TAG_PIXELS_NAME, pixels);
        return tag;
    }
}
