package games.moegirl.sinocraft.sinocore.data.gen.forge.model;

import games.moegirl.sinocraft.sinocore.data.gen.forge.impl.ForgeItemModelProviderDelegateImpl;
import games.moegirl.sinocraft.sinocore.data.gen.forge.impl.ForgeItemModelProviderImpl;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelProvider;
import games.moegirl.sinocraft.sinocore.data.gen.model.ISpecialModelBuilder;
import net.minecraft.resources.ResourceLocation;

public class SpecialModelBuilderItem implements ISpecialModelBuilder<ForgeItemModelBuilderWrapper> {

    private final ForgeItemModelProviderImpl impl;
    private final ForgeItemModelProviderDelegateImpl provider;

    public SpecialModelBuilderItem(ForgeItemModelProviderImpl impl, ForgeItemModelProviderDelegateImpl provider) {
        this.impl = impl;
        this.provider = provider;
    }

    @Override
    public IModelProvider<ForgeItemModelBuilderWrapper> getModelProvider() {
        return provider;
    }

    @Override
    public ForgeItemModelBuilderWrapper crop(String name, ResourceLocation crop) {
        return new ForgeItemModelBuilderWrapper(impl.crop(name, crop));
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
}
