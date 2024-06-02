package games.moegirl.sinocraft.sinocore.datagen.forge.model;

import games.moegirl.sinocraft.sinocore.datagen.forge.impl.ForgeItemModelProviderImpl;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelFile;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelResourceHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;

import java.nio.file.Path;

public class ForgeModelResourceHelperImpl implements IModelResourceHelper<ForgeItemModelBuilderWrapper> {

    private final ForgeItemModelProviderImpl impl;

    public ForgeModelResourceHelperImpl(ForgeItemModelProviderImpl impl) {
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
        return new ForgeResourceTypeWrapper(impl.getTextureResource());
    }

    @Override
    public IModelResourceHelper.IResourceType getModelResource() {
        return new ForgeResourceTypeWrapper(impl.getModelResource());
    }

    @Override
    public IModelResourceHelper.IResourceType getModelWithExtensionResource() {
        return new ForgeResourceTypeWrapper(impl.getModelWithExtensionResource());
    }

    @Override
    public String getFolder() {
        return impl.getFolder();
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
    public Path getPath(ForgeItemModelBuilderWrapper model) {
        return impl.getPath(model.getOrigin());
    }

    @Override
    public IModelFile weakCheckModel(ResourceLocation path) {
        return new ForgeModelFileWrapper<>(impl.weakCheckModel(path));
    }

    @Override
    public ResourceLocation blockLoc(ResourceLocation path) {
        return impl.blockLoc(path);
    }

    @Override
    public ResourceLocation foldedLoc(ResourceLocation path) {
        return impl.foldedLoc(path);
    }

}
