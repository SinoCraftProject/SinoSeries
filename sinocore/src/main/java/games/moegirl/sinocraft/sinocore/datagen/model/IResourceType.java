package games.moegirl.sinocraft.sinocore.datagen.model;

import net.minecraft.server.packs.PackType;

public interface IResourceType {

    PackType getPackType();

    String getSuffix();

    String getPrefix();
}
