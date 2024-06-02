package games.moegirl.sinocraft.sinocore.data.gen.forge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.BlockModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.forge.ForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.data.gen.forge.model.ForgeBlockModelBuilderWrapper;
import games.moegirl.sinocraft.sinocore.data.gen.forge.model.ForgeBlockModelResourceHelper;
import games.moegirl.sinocraft.sinocore.data.gen.forge.model.SpecialModelBuilderBlock;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelResourceHelper;
import games.moegirl.sinocraft.sinocore.data.gen.model.ISpecialModelBuilder;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ForgeBlockModelProviderDelegateImpl extends BlockModelProviderDelegateBase<ForgeBlockModelBuilderWrapper> {
    private final ForgeBlockModelProviderImpl impl;

    @SafeVarargs
    public ForgeBlockModelProviderDelegateImpl(ForgeDataGenContextImpl context, IRegistry<Block>... registries) {
        super(new ForgeBlockModelProviderImpl(context, false, registries));
        impl = getForgeProvider();
        impl.setDelegate(this);
    }

    @Override
    public ISpecialModelBuilder<ForgeBlockModelBuilderWrapper> getSpecialBuilder() {
        return new SpecialModelBuilderBlock(impl, this);
    }

    @Override
    public IModelResourceHelper<ForgeBlockModelBuilderWrapper> getResourceHelper() {
        return new ForgeBlockModelResourceHelper(impl);
    }

    @Override
    public ForgeBlockModelBuilderWrapper getBuilder(String path) {
        return new ForgeBlockModelBuilderWrapper(impl.getBuilder(path));
    }

    @Override
    public ForgeBlockModelBuilderWrapper withExistingParent(String name, String parent) {
        return new ForgeBlockModelBuilderWrapper(impl.withExistingParent(name, parent));
    }

    @Override
    public ForgeBlockModelBuilderWrapper withExistingParent(String name, ResourceLocation parent) {
        return new ForgeBlockModelBuilderWrapper(impl.withExistingParent(name, parent));
    }

    @Override
    public ForgeBlockModelBuilderWrapper cube(String name, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        return new ForgeBlockModelBuilderWrapper(impl.cube(name, down, up, north, south, east, west));
    }

    @Override
    public ForgeBlockModelBuilderWrapper singleTexture(String name, ResourceLocation parent, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.singleTexture(name, parent, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper singleTexture(String name, ResourceLocation parent, String textureKey, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.singleTexture(name, parent, textureKey, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper cubeAll(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.cubeAll(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper cubeTop(String name, ResourceLocation side, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.cubeTop(name, side, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper cubeBottomTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.cubeBottomTop(name, side, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper cubeColumn(String name, ResourceLocation side, ResourceLocation end) {
        return new ForgeBlockModelBuilderWrapper(impl.cubeColumn(name, side, end));
    }

    @Override
    public ForgeBlockModelBuilderWrapper cubeColumnHorizontal(String name, ResourceLocation side, ResourceLocation end) {
        return new ForgeBlockModelBuilderWrapper(impl.cubeColumnHorizontal(name, side, end));
    }

    @Override
    public ForgeBlockModelBuilderWrapper orientableVertical(String name, ResourceLocation side, ResourceLocation front) {
        return new ForgeBlockModelBuilderWrapper(impl.orientableVertical(name, side, front));
    }

    @Override
    public ForgeBlockModelBuilderWrapper orientableWithBottom(String name, ResourceLocation side, ResourceLocation front, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.orientableWithBottom(name, side, front, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper orientable(String name, ResourceLocation side, ResourceLocation front, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.orientable(name, side, front, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper cross(String name, ResourceLocation cross) {
        return new ForgeBlockModelBuilderWrapper(impl.cross(name, cross));
    }
}
