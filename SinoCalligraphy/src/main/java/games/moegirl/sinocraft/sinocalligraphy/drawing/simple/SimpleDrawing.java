package games.moegirl.sinocraft.sinocalligraphy.drawing.simple;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.client.drawing.IDrawingRenderer;
import games.moegirl.sinocraft.sinocalligraphy.client.drawing.SimpleDrawingRenderer;
import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.PaperType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.DrawingDataVersion;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.traits.IHasPaperType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.traits.IHasInkType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SimpleDrawing implements ISimpleDrawing, IHasPaperType, IHasInkType {
    protected DrawingDataVersion version = DrawingDataVersion.getLatest();

    protected Component title = Component.translatable(SCAConstants.TRANSLATE_DRAWING_TITLE_UNKNOWN_KEY);
    protected Component author = Component.translatable(SCAConstants.TRANSLATE_DRAWING_AUTHOR_UNKNOWN_KEY);
    protected long date = 1680220800;   // 2023.3.31 8:00 by default.

    protected int size = 32;
    protected byte[] pixels = new byte[0];

    protected PaperType paperType = PaperType.WHITE;
    protected InkType inkType = InkType.BLACK;

    public SimpleDrawing(int size) {
        this.version = DrawingDataVersion.V1;
        this.size = size;
        this.pixels = new byte[size * size];
    }

    public SimpleDrawing(DrawingDataVersion version, int size) {
        this(size);
        this.version = version;
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

    @Override
    public DrawingDataVersion getVersion() {
        return version;
    }

    @Override
    public void setVersion(DrawingDataVersion version) {
        this.version = version;
    }

    @Override
    public void setVersion(int version) {
        setVersion(DrawingDataVersion.fromVersion(version));
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public void setTitle(Component title) {
        this.title = title;
    }

    @Override
    public void setTitle(String title) {
        setTitle(Component.literal(title));
    }

    @Override
    public Component getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(Component author) {
        this.author = author;
    }

    @Override
    public void setAuthor(String author) {
        setAuthor(Component.literal(author));
    }

    @Override
    public void setAuthor(Player author) {
        setAuthor(author.getDisplayName());
    }

    @Override
    public long getDate() {
        return date;
    }

    @Override
    public ZonedDateTime getZonedDate() {
        var instant = Instant.ofEpochSecond(getDate());
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @Override
    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public void setZonedDate(ZonedDateTime date) {
        setDate(date.toEpochSecond());
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean isEmpty() {
        for (byte b : pixels) {
            if (b != 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public byte[] getPixels() {
        return pixels;
    }

    @Override
    public void setPixels(byte[] pixels) {
        this.pixels = pixels;
    }

    @Override
    public PaperType getPaperType() {
        return paperType;
    }

    @Override
    public void setPaperType(PaperType type) {
        this.paperType = type;
    }

    @Override
    public InkType getInkType() {
        return inkType;
    }

    @Override
    public void setInkType(InkType type) {
        this.inkType = type;
    }

    // </editor-fold>

    // <editor-fold desc="Serializer and deserializer.">

    @Override
    public void deserializeNBT(CompoundTag tag) {
        var version = tag.getInt(SCAConstants.DRAWING_TAG_VERSION_NAME);
        if (version != 0) {
            setVersion(version);
        }

        var title = Component.Serializer.fromJson(tag.getString(SCAConstants.DRAWING_TAG_TITLE_NAME));
        if (title != null) {
            setTitle(title);
        }

        var author = Component.Serializer.fromJson(tag.getString(SCAConstants.DRAWING_TAG_AUTHOR_NAME));
        if (author != null) {
            setAuthor(author);
        }

        var date = tag.getLong(SCAConstants.DRAWING_TAG_DATE_NAME);
        if (date != 0) {
            setDate(date);
        }

        var size = tag.getInt(SCAConstants.DRAWING_TAG_SIZE_NAME);
        if (size != 0) {
            setSize(size);
        }

        var pixels = tag.getByteArray(SCAConstants.DRAWING_TAG_PIXELS_NAME);
        if (pixels.length != 0) {
            setPixels(pixels);
        }

        var paper = tag.getString(SCAConstants.DRAWING_TAG_PAPER_TYPE);
        if (!paper.isBlank()) {
            setPaperType(PaperType.of(paper));
        }

        var ink = tag.getString(SCAConstants.DRAWING_TAG_INK_TYPE);
        if (!ink.isBlank()) {
            setInkType(InkType.of(ink));
        }
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
        tag.putString(SCAConstants.DRAWING_TAG_PAPER_TYPE, paperType.getName());
        tag.putString(SCAConstants.DRAWING_TAG_INK_TYPE, inkType.getName());
        return tag;
    }

    public static ISimpleDrawing from(CompoundTag tag) {
        var drawing = new SimpleDrawing(32);
        drawing.deserializeNBT(tag);
        return drawing;
    }

    // </editor-fold>

    // <editor-fold desc="Renderer">

    protected IDrawingRenderer renderer;

    @Override
    public IDrawingRenderer getRenderer() {
        if (renderer == null) {
            renderer = new SimpleDrawingRenderer(this);
        }

        return renderer;
    }

    // </editor-fold>
}
