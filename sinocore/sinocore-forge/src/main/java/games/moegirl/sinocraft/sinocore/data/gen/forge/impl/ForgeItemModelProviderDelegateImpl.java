package games.moegirl.sinocraft.sinocore.data.gen.forge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.forge.model.ForgeItemModelBuilderWrapper;
import games.moegirl.sinocraft.sinocore.data.gen.forge.model.ForgeItemModelResourceHelper;
import games.moegirl.sinocraft.sinocore.data.gen.forge.ForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.data.gen.forge.model.SpecialModelBuilderItem;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class ForgeItemModelProviderDelegateImpl extends ItemModelProviderDelegateBase<ForgeItemModelBuilderWrapper> {

    private final ForgeItemModelProviderImpl impl;

    @SafeVarargs
    public ForgeItemModelProviderDelegateImpl(ForgeDataGenContextImpl context, IRegistry<Item>... registries) {
        super(new ForgeItemModelProviderImpl(context, false, registries));
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
    public ForgeItemModelResourceHelper getResourceHelper() {
        return new ForgeItemModelResourceHelper(impl);
    }

    @Override
    public ForgeItemModelBuilderWrapper getBuilder(String path) {
        return new ForgeItemModelBuilderWrapper(impl.getBuilder(path));
    }

    @Override
    public ForgeItemModelBuilderWrapper withExistingParent(String name, String parent) {
        return new ForgeItemModelBuilderWrapper(impl.withExistingParent(name, parent));
    }

    @Override
    public ForgeItemModelBuilderWrapper withExistingParent(String name, ResourceLocation parent) {
        return new ForgeItemModelBuilderWrapper(impl.withExistingParent(name, parent));
    }

    @Override
    public ForgeItemModelBuilderWrapper cube(String name, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        return new ForgeItemModelBuilderWrapper(impl.cube(name, down, up, north, south, east, west));
    }

    @Override
    public ForgeItemModelBuilderWrapper singleTexture(String name, ResourceLocation parent, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.singleTexture(name, parent, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper singleTexture(String name, ResourceLocation parent, String textureKey, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.singleTexture(name, parent, textureKey, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper cubeAll(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.cubeAll(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper cubeTop(String name, ResourceLocation side, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.cubeTop(name, side, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper cubeBottomTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.cubeBottomTop(name, side, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper cubeColumn(String name, ResourceLocation side, ResourceLocation end) {
        return new ForgeItemModelBuilderWrapper(impl.cubeColumn(name, side, end));
    }

    @Override
    public ForgeItemModelBuilderWrapper cubeColumnHorizontal(String name, ResourceLocation side, ResourceLocation end) {
        return new ForgeItemModelBuilderWrapper(impl.cubeColumnHorizontal(name, side, end));
    }

    @Override
    public ForgeItemModelBuilderWrapper orientableVertical(String name, ResourceLocation side, ResourceLocation front) {
        return new ForgeItemModelBuilderWrapper(impl.orientableVertical(name, side, front));
    }

    @Override
    public ForgeItemModelBuilderWrapper orientableWithBottom(String name, ResourceLocation side, ResourceLocation front, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.orientableWithBottom(name, side, front, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper orientable(String name, ResourceLocation side, ResourceLocation front, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.orientable(name, side, front, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper cross(String name, ResourceLocation cross) {
        return new ForgeItemModelBuilderWrapper(impl.cross(name, cross));
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
    public ForgeItemModelBuilderWrapper basicItem(Item item) {
        return new ForgeItemModelBuilderWrapper(impl.basicItem(item));
    }

    @Override
    public ForgeItemModelBuilderWrapper basicItem(ResourceLocation item) {
        return new ForgeItemModelBuilderWrapper(impl.basicItem(item));
    }

    @Override
    public ForgeItemModelBuilderWrapper withBlockParent(IRegRef<Block, ?> block) {
        return new ForgeItemModelBuilderWrapper(impl.withBlockParent(block));
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
