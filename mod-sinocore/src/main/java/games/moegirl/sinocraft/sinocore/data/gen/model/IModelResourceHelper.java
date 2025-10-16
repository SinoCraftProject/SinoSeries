package games.moegirl.sinocraft.sinocore.data.gen.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

public interface IModelResourceHelper<T extends IModelBuilder<T>> {

    String getBlockFolder();

    String getItemFolder();

    ResourceLocation blockLoc(ResourceLocation path);

    ResourceLocation modLoc(String name);

    ResourceLocation mcLoc(String name);

    IResourceType getTextureResource();

    IResourceType getModelResource();

    IResourceType getModelWithExtensionResource();

    IModelFile getExistingFile(ResourceLocation path);

    IModelFile weakCheckModel(ResourceLocation path);

    interface IResourceType {

        PackType getPackType();

        String getSuffix();

        String getPrefix();
    }
}
