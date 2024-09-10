package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.NeoForgeItemModelProviderDelegate;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.NeoForgeItemModelProviderImpl;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelProvider;
import games.moegirl.sinocraft.sinocore.data.gen.model.ISpecialModelBuilder;
import net.minecraft.resources.ResourceLocation;

public class SpecialModelBuilderItem implements ISpecialModelBuilder<NeoForgeItemModelBuilderWrapper> {

    private final NeoForgeItemModelProviderImpl impl;
    private final NeoForgeItemModelProviderDelegate provider;

    public SpecialModelBuilderItem(NeoForgeItemModelProviderImpl impl, NeoForgeItemModelProviderDelegate provider) {
        this.impl = impl;
        this.provider = provider;
    }

    @Override
    public IModelProvider<NeoForgeItemModelBuilderWrapper> getModelProvider() {
        return provider;
    }

    @Override
    public NeoForgeItemModelBuilderWrapper crop(String name, ResourceLocation crop) {
        return new NeoForgeItemModelBuilderWrapper(impl.crop(name, crop));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper stairs(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.stairs(name, side, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper stairsOuter(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.stairsOuter(name, side, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper stairsInner(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.stairsInner(name, side, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper slab(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.slab(name, side, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper slabTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.slabTop(name, side, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper button(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.button(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper buttonPressed(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.buttonPressed(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper buttonInventory(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.buttonInventory(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper pressurePlate(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.pressurePlate(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper pressurePlateDown(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.pressurePlateDown(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper sign(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.sign(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper fencePost(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.fencePost(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper fenceSide(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.fenceSide(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper fenceInventory(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.fenceInventory(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper fenceGate(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.fenceGate(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper fenceGateOpen(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.fenceGateOpen(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper fenceGateWall(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.fenceGateWall(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper fenceGateWallOpen(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.fenceGateWallOpen(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper wallPost(String name, ResourceLocation wall) {
        return new NeoForgeItemModelBuilderWrapper(impl.wallPost(name, wall));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper wallSide(String name, ResourceLocation wall) {
        return new NeoForgeItemModelBuilderWrapper(impl.wallSide(name, wall));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper wallSideTall(String name, ResourceLocation wall) {
        return new NeoForgeItemModelBuilderWrapper(impl.wallSideTall(name, wall));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper wallInventory(String name, ResourceLocation wall) {
        return new NeoForgeItemModelBuilderWrapper(impl.wallInventory(name, wall));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper panePost(String name, ResourceLocation pane, ResourceLocation edge) {
        return new NeoForgeItemModelBuilderWrapper(impl.panePost(name, pane, edge));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper paneSide(String name, ResourceLocation pane, ResourceLocation edge) {
        return new NeoForgeItemModelBuilderWrapper(impl.paneSide(name, pane, edge));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper paneSideAlt(String name, ResourceLocation pane, ResourceLocation edge) {
        return new NeoForgeItemModelBuilderWrapper(impl.paneSideAlt(name, pane, edge));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper paneNoSide(String name, ResourceLocation pane) {
        return new NeoForgeItemModelBuilderWrapper(impl.paneNoSide(name, pane));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper paneNoSideAlt(String name, ResourceLocation pane) {
        return new NeoForgeItemModelBuilderWrapper(impl.paneNoSideAlt(name, pane));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper doorBottomLeft(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.doorBottomLeft(name, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper doorBottomLeftOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.doorBottomLeftOpen(name, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper doorBottomRight(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.doorBottomRight(name, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper doorBottomRightOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.doorBottomRightOpen(name, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper doorTopLeft(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.doorTopLeft(name, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper doorTopLeftOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.doorTopLeftOpen(name, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper doorTopRight(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.doorTopRight(name, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper doorTopRightOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return new NeoForgeItemModelBuilderWrapper(impl.doorTopRightOpen(name, bottom, top));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper trapdoorBottom(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.trapdoorBottom(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper trapdoorTop(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.trapdoorTop(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper trapdoorOpen(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.trapdoorOpen(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper trapdoorOrientableBottom(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.trapdoorOrientableBottom(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper trapdoorOrientableTop(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.trapdoorOrientableTop(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper trapdoorOrientableOpen(String name, ResourceLocation texture) {
        return new NeoForgeItemModelBuilderWrapper(impl.trapdoorOrientableOpen(name, texture));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper torch(String name, ResourceLocation torch) {
        return new NeoForgeItemModelBuilderWrapper(impl.torch(name, torch));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper torchWall(String name, ResourceLocation torch) {
        return new NeoForgeItemModelBuilderWrapper(impl.torchWall(name, torch));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper carpet(String name, ResourceLocation wool) {
        return new NeoForgeItemModelBuilderWrapper(impl.carpet(name, wool));
    }

    @Override
    public NeoForgeItemModelBuilderWrapper nested() {
        return new NeoForgeItemModelBuilderWrapper(impl.nested());
    }
}
