package games.moegirl.sinocraft.sinocalligraphy.drawing.simple;

import games.moegirl.sinocraft.sinocalligraphy.client.drawing.IDrawingRenderer;
import games.moegirl.sinocraft.sinocalligraphy.drawing.data.DrawingDataVersion;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

import java.time.ZonedDateTime;

public interface ISimpleDrawing extends INBTSerializable<CompoundTag> {
    DrawingDataVersion getVersion();
    void setVersion(DrawingDataVersion version);
    void setVersion(int version);

    Component getTitle();
    void setTitle(Component title);
    void setTitle(String title);

    Component getAuthor();
    void setAuthor(Component author);
    void setAuthor(String author);
    void setAuthor(Player author);

    long getDate();
    ZonedDateTime getZonedDate();
    void setDate(long date);
    void setZonedDate(ZonedDateTime date);

    int getSize();
    void setSize(int size);

    boolean isEmpty();
    byte[] getPixels();
    void setPixels(byte[] pixels);

    IDrawingRenderer getRenderer();
}
