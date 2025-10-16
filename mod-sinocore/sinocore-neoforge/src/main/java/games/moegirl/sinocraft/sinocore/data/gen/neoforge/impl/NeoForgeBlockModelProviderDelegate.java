package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.BlockModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.model.NeoForgeBlockModelBuilderWrapper;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.model.NeoForgeBlockModelResourceHelper;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.model.SpecialModelBuilderBlock;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelResourceHelper;
import games.moegirl.sinocraft.sinocore.data.gen.model.ISpecialModelBuilder;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class NeoForgeBlockModelProviderDelegate extends BlockModelProviderDelegateBase<NeoForgeBlockModelBuilderWrapper> {
    private final NeoForgeBlockModelProviderImpl impl;

    @SafeVarargs
    public NeoForgeBlockModelProviderDelegate(NeoForgeDataGenContext context, IRegistry<Block>... registries) {
        super(new NeoForgeBlockModelProviderImpl(context, false, registries));
        impl = getForgeProvider();
        impl.setDelegate(this);
    }

    @Override
    public ISpecialModelBuilder<NeoForgeBlockModelBuilderWrapper> getSpecialBuilder() {
        return new SpecialModelBuilderBlock(impl, this);
    }

    @Override
    public IModelResourceHelper<NeoForgeBlockModelBuilderWrapper> getResourceHelper() {
        return new NeoForgeBlockModelResourceHelper(impl);
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper getBuilder(String path) {
        return new NeoForgeBlockModelBuilderWrapper(impl.getBuilder(path));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper withExistingParent(String name, String parent) {
        return new NeoForgeBlockModelBuilderWrapper(impl.withExistingParent(name, parent));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper withExistingParent(String name, ResourceLocation parent) {
        return new NeoForgeBlockModelBuilderWrapper(impl.withExistingParent(name, parent));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper cube(String name, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        return new NeoForgeBlockModelBuilderWrapper(impl.cube(name, down, up, north, south, east, west));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper singleTexture(String name, ResourceLocation parent, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.singleTexture(name, parent, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper singleTexture(String name, ResourceLocation parent, String textureKey, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.singleTexture(name, parent, textureKey, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper cubeAll(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.cubeAll(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper cubeTop(String name, ResourceLocation side, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.cubeTop(name, side, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper cubeBottomTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.cubeBottomTop(name, side, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper cubeColumn(String name, ResourceLocation side, ResourceLocation end) {
        return new NeoForgeBlockModelBuilderWrapper(impl.cubeColumn(name, side, end));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper cubeColumnHorizontal(String name, ResourceLocation side, ResourceLocation end) {
        return new NeoForgeBlockModelBuilderWrapper(impl.cubeColumnHorizontal(name, side, end));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper orientableVertical(String name, ResourceLocation side, ResourceLocation front) {
        return new NeoForgeBlockModelBuilderWrapper(impl.orientableVertical(name, side, front));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper orientableWithBottom(String name, ResourceLocation side, ResourceLocation front, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.orientableWithBottom(name, side, front, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper orientable(String name, ResourceLocation side, ResourceLocation front, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.orientable(name, side, front, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper cross(String name, ResourceLocation cross) {
        return new NeoForgeBlockModelBuilderWrapper(impl.cross(name, cross));
    }
}
