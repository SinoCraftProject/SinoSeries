package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import games.moegirl.sinocraft.sinocore.data.gen.model.IModelResourceHelper;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public record NeoForgeResourceTypeWrapper(ExistingFileHelper.IResourceType type) implements IModelResourceHelper.IResourceType {
    @Override
    public PackType getPackType() {
        return type.getPackType();
    }

    @Override
    public String getSuffix() {
        return type.getSuffix();
    }

    @Override
    public String getPrefix() {
        return type.getPrefix();
    }
}
