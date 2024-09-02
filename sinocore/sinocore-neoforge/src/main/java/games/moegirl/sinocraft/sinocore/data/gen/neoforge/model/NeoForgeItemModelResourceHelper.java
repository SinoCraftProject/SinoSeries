package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.NeoForgeItemModelProviderImpl;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelFile;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelResourceHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelProvider;

import java.nio.file.Path;

public class NeoForgeItemModelResourceHelper implements IModelResourceHelper<NeoForgeItemModelBuilderWrapper> {

    private final NeoForgeItemModelProviderImpl impl;

    public NeoForgeItemModelResourceHelper(NeoForgeItemModelProviderImpl impl) {
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
        return new NeoForgeResourceTypeWrapper(ModelProvider.TEXTURE);
    }

    @Override
    public IModelResourceHelper.IResourceType getModelResource() {
        return new NeoForgeResourceTypeWrapper(ModelProvider.MODEL);
    }

    @Override
    public IModelResourceHelper.IResourceType getModelWithExtensionResource() {
        return new NeoForgeResourceTypeWrapper(ModelProvider.MODEL_WITH_EXTENSION);
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
        return new NeoForgeModelFileWrapper<>(impl.getExistingFile(path));
    }

    @Override
    public IModelFile weakCheckModel(ResourceLocation path) {
        return new NeoForgeModelFileWrapper<>(impl.weakCheckModel(path));
    }

    @Override
    public Path getPath(NeoForgeItemModelBuilderWrapper model) {
        return impl.getPath(model.getOrigin());
    }

    @Override
    public ResourceLocation blockLoc(ResourceLocation path) {
        return ResourceLocation.fromNamespaceAndPath(path.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + path.getPath());
    }

    @Override
    public ResourceLocation foldedLoc(ResourceLocation path) {
        return path.getPath().contains("/") ? path :
                ResourceLocation.fromNamespaceAndPath(path.getNamespace(), impl.folder + "/" + path.getPath());
    }
}