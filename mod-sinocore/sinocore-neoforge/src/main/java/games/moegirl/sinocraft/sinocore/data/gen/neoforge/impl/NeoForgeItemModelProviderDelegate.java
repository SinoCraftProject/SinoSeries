package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.model.NeoForgeItemModelBuilderWrapper;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.model.NeoForgeItemModelResourceHelper;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.model.SpecialModelBuilderItem;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class NeoForgeItemModelProviderDelegate extends ItemModelProviderDelegateBase<NeoForgeItemModelBuilderWrapper> {

    private final NeoForgeItemModelProviderImpl impl;

    @SafeVarargs
    public NeoForgeItemModelProviderDelegate(NeoForgeDataGenContext context, IRegistry<Item>... registries) {
        super(new NeoForgeItemModelProviderImpl(context, false, registries));
        impl = getForgeProvider();
        impl.setDelegate(this);
    }

    @Override
    public void genDefaultItemModel(Item item) {
        impl.genDefaultItemModel(item);
    }

    @Override
    public SpecialModelBuilderItem getSpecialBuilder() {
        return new SpecialModelBuilderItem(impl, this);
    }

    @Override
    public NeoForgeItemModelResourceHelper getResourceHelper() {
        return new NeoForgeItemModelResourceHelper(impl);
    }

    @Override
    public NeoForgeItemModelBuilderWrapper getBuilder(String path) {
        return new NeoForgeItemModelBuilderWrapper(impl.getBuilder(path));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper withExistingParent(String name, String parent) {
        return new NeoForgeItemModelBuilderWrapper(impl.withExistingParent(name, parent));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper withExistingParent(String name, ResourceLocation parent) {
        return new NeoForgeItemModelBuilderWrapper(impl.withExistingParent(name, parent));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper cube(String name, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        return new NeoForgeItemModelBuilderWrapper(impl.cube(name, down, up, north, south, east, west));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper singleTexture(String name, ResourceLocation parent, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.singleTexture(name, parent, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper singleTexture(String name, ResourceLocation parent, String textureKey, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.singleTexture(name, parent, textureKey, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper cubeAll(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.cubeAll(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper cubeTop(String name, ResourceLocation side, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.cubeTop(name, side, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper cubeBottomTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.cubeBottomTop(name, side, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper cubeColumn(String name, ResourceLocation side, ResourceLocation end) {
        return new NeoForgeItemModelBuilderWrapper(impl.cubeColumn(name, side, end));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper cubeColumnHorizontal(String name, ResourceLocation side, ResourceLocation end) {
        return new NeoForgeItemModelBuilderWrapper(impl.cubeColumnHorizontal(name, side, end));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper orientableVertical(String name, ResourceLocation side, ResourceLocation front) {
        return new NeoForgeItemModelBuilderWrapper(impl.orientableVertical(name, side, front));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper orientableWithBottom(String name, ResourceLocation side, ResourceLocation front, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.orientableWithBottom(name, side, front, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper orientable(String name, ResourceLocation side, ResourceLocation front, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.orientable(name, side, front, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper cross(String name, ResourceLocation cross) {
        return new NeoForgeItemModelBuilderWrapper(impl.cross(name, cross));
    }

    @Override
    public void skipItem(Item... items) {
        impl.skip(items);
    }

    @Override
    public void printExceptions() {
        impl.printExceptions();
    }

    @Override
    public NeoForgeItemModelBuilderWrapper basicItem(Item item) {
        return new NeoForgeItemModelBuilderWrapper(impl.basicItem(item));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper basicItem(ResourceLocation item) {
        return new NeoForgeItemModelBuilderWrapper(impl.basicItem(item));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper withBlockParent(IRegRef<Block> block) {
        return new NeoForgeItemModelBuilderWrapper(impl.withBlockParent(block));
    }

    @Override
    public void generated(ItemLike item) {
        impl.generated(item);
    }

    @Override
    public void handheld(ItemLike item) {
        if (item instanceof Block block) {
            impl.handheld(block);
        } else {
            impl.handheld(item);
        }
    }

    @Override
    public void blockItem(Block block) {
        impl.blockItem(block);
    }

    @Override
    public void blockItem(Block block, String statedModel) {
        impl.blockItem(block, statedModel);
    }
}
