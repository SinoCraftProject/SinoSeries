package games.moegirl.sinocraft.sinobrush.drawing;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinocore.data.migratable.IDataMigratable;
import games.moegirl.sinocraft.sinocore.nbt.IDataCompoundTagSerializable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Drawing implements IDataCompoundTagSerializable, IDataMigratable<Void, Void> {
    public static final Drawing EMPTY;

    static {
        EMPTY = new Drawing();
        EMPTY.pixels = new byte[16 * 16 / 2];
        EMPTY.xSize = 16;
        EMPTY.ySize = 16;
    }

    private DrawingVersion version = DrawingVersion.latest();

    private Component title = Component.translatable(SBRConstants.Translation.KEY_DRAWING_TITLE_UNKNOWN);
    private Component author = Component.translatable(SBRConstants.Translation.KEY_DRAWING_AUTHOR_UNKNOWN);

    private long date = 1680220800;   // 2023.3.31 8:00 by default.

    private byte[] pixels = new byte[0];

    private int xSize = SBRConstants.DRAWING_MIN_LENGTH;
    private int ySize = SBRConstants.DRAWING_MIN_LENGTH;

    private int paperColor = SBRConstants.COLOR_WHITE;
    private int inkColor = SBRConstants.COLOR_BLACK;

    // <editor-fold desc="Getter and Setter.">

    public DrawingVersion getVersion() {
        return version;
    }

    public void setVersion(DrawingVersion version) {
        this.version = version;
    }

    public void setVersion(int version) {
        setVersion(DrawingVersion.from(version));
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

    public byte[] getPixels() {
        return pixels;
    }

    public byte getPixel(int index) {
        var indexInArray = index / SBRConstants.DRAWING_COLOR_COUNT_IN_BYTE;
        var shift = SBRConstants.DRAWING_COLOR_COUNT_IN_BYTE - (index % SBRConstants.DRAWING_COLOR_COUNT_IN_BYTE);

        if (getPixels().length >= indexInArray) {
            throw new IllegalArgumentException("Index out of pixels bound: " + index);
        }

        var cell = getPixels()[indexInArray];
        cell >>= (shift * SBRConstants.DRAWING_COLOR_LENGTH);
        return cell;
    }

    public byte getPixel(int x, int y) {
        return getPixel(x * getWidth() + y);
    }

    public void setPixels(byte[] pixels) {
        this.pixels = pixels;
    }

    public void setPixel(int index, byte value) {
        if (value >= SBRConstants.DRAWING_COLOR_MAX) {
            throw new IllegalArgumentException("Value out of colors max bound: " + value);
        }

        if (value < 0) {
            throw new IllegalArgumentException("Value out of colors min bound: " + value);
        }

        var indexInArray = index / SBRConstants.DRAWING_COLOR_COUNT_IN_BYTE;
        var shift = SBRConstants.DRAWING_COLOR_COUNT_IN_BYTE - (index % SBRConstants.DRAWING_COLOR_COUNT_IN_BYTE);

        if (getPixels().length >= indexInArray) {
            throw new IllegalArgumentException("Index out of pixels bound: " + index);
        }

        var cell = getPixels()[indexInArray];
        var mask = SBRConstants.DRAWING_COLOR_MASK << (shift * SBRConstants.DRAWING_COLOR_LENGTH);
        cell &= (byte) ~mask;
        var shiftedValue = value << shift;
        cell |= (byte) shiftedValue;
        getPixels()[indexInArray] = cell;
    }

    public void setPixel(int x, int y, byte value) {
        setPixel(x * getWidth() + y, value);
    }

    public boolean isEmpty() {
        for (var l : pixels) {
            if (l != 0) {
                return true;
            }
        }

        return false;
    }

    public int getWidth() {
        return xSize;
    }

    public void setWidth(int x) {
        this.xSize = x;
    }

    public int getHeight() {
        return ySize;
    }

    public void setHeight(int y) {
        this.ySize = y;
    }

    public int getPaperColor() {
        return paperColor;
    }

    public void setPaperColor(int paperColor) {
        this.paperColor = paperColor;
    }

    public int getInkColor() {
        return inkColor;
    }

    public void setInkColor(int inkColor) {
        this.inkColor = inkColor;
    }

    // </editor-fold>

    @Override
    public void readFromCompound(CompoundTag tag) {
        var version = tag.getInt(SBRConstants.TagName.DRAWING_VERSION);
        setVersion(DrawingVersion.from(version));

        var titleJson = tag.getString(SBRConstants.TagName.DRAWING_TITLE);
        var title = Component.Serializer.fromJson(titleJson);
        if (title != null) {
            setTitle(title);
        }

        var authorJson = tag.getString(SBRConstants.TagName.DRAWING_AUTHOR);
        var author = Component.Serializer.fromJson(authorJson);
        if (author != null) {
            setAuthor(author);
        }

        var date = tag.getLong(SBRConstants.TagName.DRAWING_DATE);
        if (date != 0) {
            setDate(date);
        }

        var pixels = tag.getByteArray(SBRConstants.TagName.DRAWING_PIXELS);
        setPixels(pixels);

        var size = tag.getCompound(SBRConstants.TagName.DRAWING_SIZE);
        var x = size.getInt(SBRConstants.TagName.DRAWING_SIZE_X);
        if (x != 0) {
            setWidth(x);
        }
        var y = size.getInt(SBRConstants.TagName.DRAWING_SIZE_Y);
        if (y != 0) {
            setHeight(y);
        }

        var color = tag.getCompound(SBRConstants.TagName.DRAWING_COLOR);
        var paper = color.getInt(SBRConstants.TagName.DRAWING_COLOR_PAPER);
        setPaperColor(paper);
        var ink = color.getInt(SBRConstants.TagName.DRAWING_COLOR_INK);
        setInkColor(ink);
    }

    @Override
    public CompoundTag writeToCompound() {
        var tag = new CompoundTag();

        tag.putInt(SBRConstants.TagName.DRAWING_VERSION, getVersion().getVersion());
        tag.putString(SBRConstants.TagName.DRAWING_TITLE, Component.Serializer.toJson(getTitle()));
        tag.putString(SBRConstants.TagName.DRAWING_AUTHOR, Component.Serializer.toJson(getAuthor()));
        tag.putLong(SBRConstants.TagName.DRAWING_DATE, getDate());
        tag.putByteArray(SBRConstants.TagName.DRAWING_PIXELS, getPixels());

        var size = new CompoundTag();
        size.putInt(SBRConstants.TagName.DRAWING_SIZE_X, getWidth());
        size.putInt(SBRConstants.TagName.DRAWING_SIZE_Y, getHeight());
        tag.put(SBRConstants.TagName.DRAWING_SIZE, size);

        var color = new CompoundTag();
        color.putInt(SBRConstants.TagName.DRAWING_COLOR_PAPER, getPaperColor());
        color.putInt(SBRConstants.TagName.DRAWING_COLOR_INK, getInkColor());
        tag.put(SBRConstants.TagName.DRAWING_COLOR, color);

        return tag;
    }

    @Override
    public Void up() {
        return null;
    }

    @Override
    public Void down() {
        return null;
    }

    public static Drawing fromTag(CompoundTag tag) {
        var drawing = new Drawing();
        drawing.readFromCompound(tag);
        return drawing;
    }
}
