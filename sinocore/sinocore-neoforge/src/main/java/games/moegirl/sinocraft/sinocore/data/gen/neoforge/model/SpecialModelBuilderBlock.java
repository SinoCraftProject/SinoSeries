package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.NeoForgeBlockModelProviderDelegate;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.NeoForgeBlockModelProviderImpl;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelProvider;
import games.moegirl.sinocraft.sinocore.data.gen.model.ISpecialModelBuilder;
import net.minecraft.resources.ResourceLocation;

public class SpecialModelBuilderBlock implements ISpecialModelBuilder<NeoForgeBlockModelBuilderWrapper> {

    private final NeoForgeBlockModelProviderImpl impl;
    private final NeoForgeBlockModelProviderDelegate provider;

    public SpecialModelBuilderBlock(NeoForgeBlockModelProviderImpl impl, NeoForgeBlockModelProviderDelegate provider) {
        this.impl = impl;
        this.provider = provider;
    }

    @Override
    public IModelProvider<NeoForgeBlockModelBuilderWrapper> getModelProvider() {
        return provider;
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper crop(String name, ResourceLocation crop) {
        return new NeoForgeBlockModelBuilderWrapper(impl.crop(name, crop));
    }
    @Override
    public NeoForgeBlockModelBuilderWrapper stairs(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.stairs(name, side, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper stairsOuter(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.stairsOuter(name, side, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper stairsInner(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.stairsInner(name, side, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper slab(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.slab(name, side, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper slabTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.slabTop(name, side, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper button(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.button(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper buttonPressed(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.buttonPressed(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper buttonInventory(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.buttonInventory(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper pressurePlate(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.pressurePlate(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper pressurePlateDown(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.pressurePlateDown(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper sign(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.sign(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper fencePost(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.fencePost(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper fenceSide(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.fenceSide(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper fenceInventory(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.fenceInventory(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper fenceGate(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.fenceGate(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper fenceGateOpen(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.fenceGateOpen(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper fenceGateWall(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.fenceGateWall(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper fenceGateWallOpen(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.fenceGateWallOpen(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper wallPost(String name, ResourceLocation wall) {
        return new NeoForgeBlockModelBuilderWrapper(impl.wallPost(name, wall));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper wallSide(String name, ResourceLocation wall) {
        return new NeoForgeBlockModelBuilderWrapper(impl.wallSide(name, wall));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper wallSideTall(String name, ResourceLocation wall) {
        return new NeoForgeBlockModelBuilderWrapper(impl.wallSideTall(name, wall));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper wallInventory(String name, ResourceLocation wall) {
        return new NeoForgeBlockModelBuilderWrapper(impl.wallInventory(name, wall));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper panePost(String name, ResourceLocation pane, ResourceLocation edge) {
        return new NeoForgeBlockModelBuilderWrapper(impl.panePost(name, pane, edge));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper paneSide(String name, ResourceLocation pane, ResourceLocation edge) {
        return new NeoForgeBlockModelBuilderWrapper(impl.paneSide(name, pane, edge));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper paneSideAlt(String name, ResourceLocation pane, ResourceLocation edge) {
        return new NeoForgeBlockModelBuilderWrapper(impl.paneSideAlt(name, pane, edge));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper paneNoSide(String name, ResourceLocation pane) {
        return new NeoForgeBlockModelBuilderWrapper(impl.paneNoSide(name, pane));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper paneNoSideAlt(String name, ResourceLocation pane) {
        return new NeoForgeBlockModelBuilderWrapper(impl.paneNoSideAlt(name, pane));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper doorBottomLeft(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.doorBottomLeft(name, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper doorBottomLeftOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.doorBottomLeftOpen(name, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper doorBottomRight(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.doorBottomRight(name, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper doorBottomRightOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.doorBottomRightOpen(name, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper doorTopLeft(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.doorTopLeft(name, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper doorTopLeftOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.doorTopLeftOpen(name, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper doorTopRight(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.doorTopRight(name, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper doorTopRightOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeBlockModelBuilderWrapper(impl.doorTopRightOpen(name, bottom, top));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper trapdoorBottom(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.trapdoorBottom(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper trapdoorTop(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.trapdoorTop(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper trapdoorOpen(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.trapdoorOpen(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper trapdoorOrientableBottom(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.trapdoorOrientableBottom(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper trapdoorOrientableTop(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.trapdoorOrientableTop(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper trapdoorOrientableOpen(String name, ResourceLocation texture) {
        return new NeoForgeBlockModelBuilderWrapper(impl.trapdoorOrientableOpen(name, texture));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper torch(String name, ResourceLocation torch) {
        return new NeoForgeBlockModelBuilderWrapper(impl.torch(name, torch));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper torchWall(String name, ResourceLocation torch) {
        return new NeoForgeBlockModelBuilderWrapper(impl.torchWall(name, torch));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper carpet(String name, ResourceLocation wool) {
        return new NeoForgeBlockModelBuilderWrapper(impl.carpet(name, wool));
    }

    @Override
    public NeoForgeBlockModelBuilderWrapper nested() {
        return new NeoForgeBlockModelBuilderWrapper(impl.nested());
    }
}
