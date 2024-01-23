package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.datagen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.datagen.forge.ForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.datagen.forge.model.ForgeItemModelBuilderWrapper;
import games.moegirl.sinocraft.sinocore.datagen.forge.model.ForgeModelFileWrapper;
import games.moegirl.sinocraft.sinocore.datagen.forge.model.ForgeResourceTypeWrapper;
import games.moegirl.sinocraft.sinocore.datagen.model.*;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;

import java.nio.file.Path;

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
    public String getBlockFolder() {
        return ItemModelProvider.BLOCK_FOLDER;
    }

    @Override
    public String getItemFolder() {
        return ItemModelProvider.ITEM_FOLDER;
    }

    @Override
    public IResourceType getTextureResource() {
        return new ForgeResourceTypeWrapper(impl.getTextureResource());
    }

    @Override
    public IResourceType getModelResource() {
        return new ForgeResourceTypeWrapper(impl.getModelResource());
    }

    @Override
    public IResourceType getModelWithExtensionResource() {
        return new ForgeResourceTypeWrapper(impl.getModelWithExtensionResource());
    }

    @Override
    public String getFolder() {
        return impl.getFolder();
    }

    @Override
    public ForgeItemModelBuilderWrapper getBuilder(String path) {
        return new ForgeItemModelBuilderWrapper(impl.getBuilder(path));
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
    public ForgeItemModelBuilderWrapper crop(String name, ResourceLocation crop) {
        return new ForgeItemModelBuilderWrapper(impl.crop(name, crop));
    }

    @Override
    public ForgeItemModelBuilderWrapper cross(String name, ResourceLocation cross) {
        return new ForgeItemModelBuilderWrapper(impl.cross(name, cross));
    }

    @Override
    public ForgeItemModelBuilderWrapper stairs(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.stairs(name, side, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper stairsOuter(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.stairsOuter(name, side, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper stairsInner(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.stairsInner(name, side, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper slab(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.slab(name, side, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper slabTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.slabTop(name, side, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper button(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.button(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper buttonPressed(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.buttonPressed(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper buttonInventory(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.buttonInventory(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper pressurePlate(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.pressurePlate(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper pressurePlateDown(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.pressurePlateDown(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper sign(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.sign(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper fencePost(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.fencePost(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper fenceSide(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.fenceSide(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper fenceInventory(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.fenceInventory(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper fenceGate(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.fenceGate(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper fenceGateOpen(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.fenceGateOpen(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper fenceGateWall(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.fenceGateWall(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper fenceGateWallOpen(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.fenceGateWallOpen(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper wallPost(String name, ResourceLocation wall) {
        return new ForgeItemModelBuilderWrapper(impl.wallPost(name, wall));
    }

    @Override
    public ForgeItemModelBuilderWrapper wallSide(String name, ResourceLocation wall) {
        return new ForgeItemModelBuilderWrapper(impl.wallSide(name, wall));
    }

    @Override
    public ForgeItemModelBuilderWrapper wallSideTall(String name, ResourceLocation wall) {
        return new ForgeItemModelBuilderWrapper(impl.wallSideTall(name, wall));
    }

    @Override
    public ForgeItemModelBuilderWrapper wallInventory(String name, ResourceLocation wall) {
        return new ForgeItemModelBuilderWrapper(impl.wallInventory(name, wall));
    }

    @Override
    public ForgeItemModelBuilderWrapper panePost(String name, ResourceLocation pane, ResourceLocation edge) {
        return new ForgeItemModelBuilderWrapper(impl.panePost(name, pane, edge));
    }

    @Override
    public ForgeItemModelBuilderWrapper paneSide(String name, ResourceLocation pane, ResourceLocation edge) {
        return new ForgeItemModelBuilderWrapper(impl.paneSide(name, pane, edge));
    }

    @Override
    public ForgeItemModelBuilderWrapper paneSideAlt(String name, ResourceLocation pane, ResourceLocation edge) {
        return new ForgeItemModelBuilderWrapper(impl.paneSideAlt(name, pane, edge));
    }

    @Override
    public ForgeItemModelBuilderWrapper paneNoSide(String name, ResourceLocation pane) {
        return new ForgeItemModelBuilderWrapper(impl.paneNoSide(name, pane));
    }

    @Override
    public ForgeItemModelBuilderWrapper paneNoSideAlt(String name, ResourceLocation pane) {
        return new ForgeItemModelBuilderWrapper(impl.paneNoSideAlt(name, pane));
    }

    @Override
    public ForgeItemModelBuilderWrapper doorBottomLeft(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.doorBottomLeft(name, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper doorBottomLeftOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.doorBottomLeftOpen(name, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper doorBottomRight(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.doorBottomRight(name, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper doorBottomRightOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.doorBottomRightOpen(name, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper doorTopLeft(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.doorTopLeft(name, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper doorTopLeftOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.doorTopLeftOpen(name, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper doorTopRight(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.doorTopRight(name, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper doorTopRightOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeItemModelBuilderWrapper(impl.doorTopRightOpen(name, bottom, top));
    }

    @Override
    public ForgeItemModelBuilderWrapper trapdoorBottom(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.trapdoorBottom(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper trapdoorTop(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.trapdoorTop(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper trapdoorOpen(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.trapdoorOpen(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper trapdoorOrientableBottom(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.trapdoorOrientableBottom(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper trapdoorOrientableTop(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.trapdoorOrientableTop(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper trapdoorOrientableOpen(String name, ResourceLocation texture) {
        return new ForgeItemModelBuilderWrapper(impl.trapdoorOrientableOpen(name, texture));
    }

    @Override
    public ForgeItemModelBuilderWrapper torch(String name, ResourceLocation torch) {
        return new ForgeItemModelBuilderWrapper(impl.torch(name, torch));
    }

    @Override
    public ForgeItemModelBuilderWrapper torchWall(String name, ResourceLocation torch) {
        return new ForgeItemModelBuilderWrapper(impl.torchWall(name, torch));
    }

    @Override
    public ForgeItemModelBuilderWrapper carpet(String name, ResourceLocation wool) {
        return new ForgeItemModelBuilderWrapper(impl.carpet(name, wool));
    }

    @Override
    public ForgeItemModelBuilderWrapper nested() {
        return new ForgeItemModelBuilderWrapper(impl.nested());
    }

    @Override
    public void skipItem(Item... items) {
        impl.skipItem(items);
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
    public void printExceptions() {
        impl.printExceptions();
    }

    @Override
    public ResourceLocation blockLoc(ResourceLocation path) {
        return impl.blockLoc(path);
    }

    @Override
    public ResourceLocation foldedLoc(ResourceLocation path) {
        return impl.foldedLoc(path);
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
    public ForgeItemModelBuilderWrapper withBlockParent(Block block) {
        return new ForgeItemModelBuilderWrapper(impl.withBlockParent(block));
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
        impl.handheld(item);
    }

    @Override
    public void handheld(Block block) {
        impl.handheld(block);
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
