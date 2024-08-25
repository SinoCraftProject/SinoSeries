package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.ForgeItemModelProviderImpl;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelFile;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelResourceHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelProvider;

import java.nio.file.Path;

public class ForgeItemModelResourceHelper implements IModelResourceHelper<ForgeItemModelBuilderWrapper> {

    private final ForgeItemModelProviderImpl impl;

    public ForgeItemModelResourceHelper(ForgeItemModelProviderImpl impl) {
        this.impl = impl;
    }

    @Override
    public String getBlockFolder() {
        return ItemModelProvider.BLOCK_FOLDER;
    }

    @Override
    public String getItemFolder() {
        return ItemModelProvider.ITEM_FOLDER;
    }

    @Override
    public IModelResourceHelper.IResourceType getTextureResource() {
        return new ForgeResourceTypeWrapper(ModelProvider.TEXTURE);
    }

    @Override
    public IModelResourceHelper.IResourceType getModelResource() {
        return new ForgeResourceTypeWrapper(ModelProvider.MODEL);
    }

    @Override
    public IModelResourceHelper.IResourceType getModelWithExtensionResource() {
        return new ForgeResourceTypeWrapper(ModelProvider.MODEL_WITH_EXTENSION);
    }

    @Override
    public String getFolder() {
        return impl.folder;
    }

    @Override
    public ResourceLocation modLoc(String name) {
        return impl.modLoc(name);
    }

    @Override
    public ResourceLocation mcLoc(String name) {
        return impl.mcLoc(name);
    }

    @Override
    public IModelFile getExistingFile(ResourceLocation path) {
        return new ForgeModelFileWrapper<>(impl.getExistingFile(path));
    }

    @Override
    public IModelFile weakCheckModel(ResourceLocation path) {
        return new ForgeModelFileWrapper<>(impl.weakCheckModel(path));
    }

    @Override
    public Path getPath(ForgeItemModelBuilderWrapper model) {
        return impl.getPath(model.getOrigin());
    }

    @Override
    public ResourceLocation blockLoc(ResourceLocation path) {
        return new ResourceLocation(path.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + path.getPath());
    }

    @Override
    public ResourceLocation foldedLoc(ResourceLocation path) {
        return path.getPath().contains("/") ? path :
                new ResourceLocation(path.getNamespace(), impl.folder + "/" + path.getPath());
    }
}
