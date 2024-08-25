package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.ForgeBlockModelProviderDelegate;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.ForgeBlockModelProviderImpl;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelProvider;
import games.moegirl.sinocraft.sinocore.data.gen.model.ISpecialModelBuilder;
import net.minecraft.resources.ResourceLocation;

public class SpecialModelBuilderBlock implements ISpecialModelBuilder<ForgeBlockModelBuilderWrapper> {

    private final ForgeBlockModelProviderImpl impl;
    private final ForgeBlockModelProviderDelegate provider;

    public SpecialModelBuilderBlock(ForgeBlockModelProviderImpl impl, ForgeBlockModelProviderDelegate provider) {
        this.impl = impl;
        this.provider = provider;
    }

    @Override
    public IModelProvider<ForgeBlockModelBuilderWrapper> getModelProvider() {
        return provider;
    }

    @Override
    public ForgeBlockModelBuilderWrapper crop(String name, ResourceLocation crop) {
        return new ForgeBlockModelBuilderWrapper(impl.crop(name, crop));
    }
    @Override
    public ForgeBlockModelBuilderWrapper stairs(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.stairs(name, side, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper stairsOuter(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.stairsOuter(name, side, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper stairsInner(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.stairsInner(name, side, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper slab(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.slab(name, side, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper slabTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.slabTop(name, side, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper button(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.button(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper buttonPressed(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.buttonPressed(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper buttonInventory(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.buttonInventory(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper pressurePlate(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.pressurePlate(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper pressurePlateDown(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.pressurePlateDown(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper sign(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.sign(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper fencePost(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.fencePost(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper fenceSide(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.fenceSide(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper fenceInventory(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.fenceInventory(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper fenceGate(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.fenceGate(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper fenceGateOpen(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.fenceGateOpen(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper fenceGateWall(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.fenceGateWall(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper fenceGateWallOpen(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.fenceGateWallOpen(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper wallPost(String name, ResourceLocation wall) {
        return new ForgeBlockModelBuilderWrapper(impl.wallPost(name, wall));
    }

    @Override
    public ForgeBlockModelBuilderWrapper wallSide(String name, ResourceLocation wall) {
        return new ForgeBlockModelBuilderWrapper(impl.wallSide(name, wall));
    }

    @Override
    public ForgeBlockModelBuilderWrapper wallSideTall(String name, ResourceLocation wall) {
        return new ForgeBlockModelBuilderWrapper(impl.wallSideTall(name, wall));
    }

    @Override
    public ForgeBlockModelBuilderWrapper wallInventory(String name, ResourceLocation wall) {
        return new ForgeBlockModelBuilderWrapper(impl.wallInventory(name, wall));
    }

    @Override
    public ForgeBlockModelBuilderWrapper panePost(String name, ResourceLocation pane, ResourceLocation edge) {
        return new ForgeBlockModelBuilderWrapper(impl.panePost(name, pane, edge));
    }

    @Override
    public ForgeBlockModelBuilderWrapper paneSide(String name, ResourceLocation pane, ResourceLocation edge) {
        return new ForgeBlockModelBuilderWrapper(impl.paneSide(name, pane, edge));
    }

    @Override
    public ForgeBlockModelBuilderWrapper paneSideAlt(String name, ResourceLocation pane, ResourceLocation edge) {
        return new ForgeBlockModelBuilderWrapper(impl.paneSideAlt(name, pane, edge));
    }

    @Override
    public ForgeBlockModelBuilderWrapper paneNoSide(String name, ResourceLocation pane) {
        return new ForgeBlockModelBuilderWrapper(impl.paneNoSide(name, pane));
    }

    @Override
    public ForgeBlockModelBuilderWrapper paneNoSideAlt(String name, ResourceLocation pane) {
        return new ForgeBlockModelBuilderWrapper(impl.paneNoSideAlt(name, pane));
    }

    @Override
    public ForgeBlockModelBuilderWrapper doorBottomLeft(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.doorBottomLeft(name, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper doorBottomLeftOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.doorBottomLeftOpen(name, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper doorBottomRight(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.doorBottomRight(name, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper doorBottomRightOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.doorBottomRightOpen(name, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper doorTopLeft(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.doorTopLeft(name, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper doorTopLeftOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.doorTopLeftOpen(name, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper doorTopRight(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.doorTopRight(name, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper doorTopRightOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new ForgeBlockModelBuilderWrapper(impl.doorTopRightOpen(name, bottom, top));
    }

    @Override
    public ForgeBlockModelBuilderWrapper trapdoorBottom(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.trapdoorBottom(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper trapdoorTop(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.trapdoorTop(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper trapdoorOpen(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.trapdoorOpen(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper trapdoorOrientableBottom(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.trapdoorOrientableBottom(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper trapdoorOrientableTop(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.trapdoorOrientableTop(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper trapdoorOrientableOpen(String name, ResourceLocation texture) {
        return new ForgeBlockModelBuilderWrapper(impl.trapdoorOrientableOpen(name, texture));
    }

    @Override
    public ForgeBlockModelBuilderWrapper torch(String name, ResourceLocation torch) {
        return new ForgeBlockModelBuilderWrapper(impl.torch(name, torch));
    }

    @Override
    public ForgeBlockModelBuilderWrapper torchWall(String name, ResourceLocation torch) {
        return new ForgeBlockModelBuilderWrapper(impl.torchWall(name, torch));
    }

    @Override
    public ForgeBlockModelBuilderWrapper carpet(String name, ResourceLocation wool) {
        return new ForgeBlockModelBuilderWrapper(impl.carpet(name, wool));
    }

    @Override
    public ForgeBlockModelBuilderWrapper nested() {
        return new ForgeBlockModelBuilderWrapper(impl.nested());
    }
}
