package games.moegirl.sinocraft.sinocore.data.gen.model;

import net.minecraft.resources.ResourceLocation;

public interface ISpecialModelBuilder<T extends IModelBuilder<T>> {

    IModelProvider<T> getModelProvider();

    T crop(String name, ResourceLocation crop);

    T stairs(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top);

    T stairsOuter(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top);

    T stairsInner(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top);

    T slab(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top);

    T slabTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top);

    T button(String name, ResourceLocation texture);

    T buttonPressed(String name, ResourceLocation texture);

    T buttonInventory(String name, ResourceLocation texture);

    T pressurePlate(String name, ResourceLocation texture);

    T pressurePlateDown(String name, ResourceLocation texture);

    T sign(String name, ResourceLocation texture);

    T fencePost(String name, ResourceLocation texture);

    T fenceSide(String name, ResourceLocation texture);

    T fenceInventory(String name, ResourceLocation texture);

    T fenceGate(String name, ResourceLocation texture);

    T fenceGateOpen(String name, ResourceLocation texture);

    T fenceGateWall(String name, ResourceLocation texture);

    T fenceGateWallOpen(String name, ResourceLocation texture);

    T wallPost(String name, ResourceLocation wall);

    T wallSide(String name, ResourceLocation wall);

    T wallSideTall(String name, ResourceLocation wall);

    T wallInventory(String name, ResourceLocation wall);

    default T pane(String name, String parent, ResourceLocation pane, ResourceLocation edge) {
        IModelProvider<T> provider = getModelProvider();
        IModelResourceHelper<T> helper = provider.getResourceHelper();
        return provider.withExistingParent(name, helper.getBlockFolder() + "/" + parent)
                .texture("pane", pane)
                .texture("edge", edge);
    }

    T panePost(String name, ResourceLocation pane, ResourceLocation edge);

    T paneSide(String name, ResourceLocation pane, ResourceLocation edge);

    T paneSideAlt(String name, ResourceLocation pane, ResourceLocation edge);

    T paneNoSide(String name, ResourceLocation pane);

    T paneNoSideAlt(String name, ResourceLocation pane);

    default T door(String name, String model, ResourceLocation bottom, ResourceLocation top) {
        IModelProvider<T> provider = getModelProvider();
        IModelResourceHelper<T> helper = provider.getResourceHelper();
        return provider.withExistingParent(name, helper + "/" + model)
                .texture("bottom", bottom)
                .texture("top", top);
    }

    T doorBottomLeft(String name, ResourceLocation bottom, ResourceLocation top);

    T doorBottomLeftOpen(String name, ResourceLocation bottom, ResourceLocation top);

    T doorBottomRight(String name, ResourceLocation bottom, ResourceLocation top);

    T doorBottomRightOpen(String name, ResourceLocation bottom, ResourceLocation top);

    T doorTopLeft(String name, ResourceLocation bottom, ResourceLocation top);

    T doorTopLeftOpen(String name, ResourceLocation bottom, ResourceLocation top);

    T doorTopRight(String name, ResourceLocation bottom, ResourceLocation top);

    T doorTopRightOpen(String name, ResourceLocation bottom, ResourceLocation top);

    T trapdoorBottom(String name, ResourceLocation texture);

    T trapdoorTop(String name, ResourceLocation texture);

    T trapdoorOpen(String name, ResourceLocation texture);

    T trapdoorOrientableBottom(String name, ResourceLocation texture);

    T trapdoorOrientableTop(String name, ResourceLocation texture);

    T trapdoorOrientableOpen(String name, ResourceLocation texture);

    T torch(String name, ResourceLocation torch);

    T torchWall(String name, ResourceLocation torch);

    T carpet(String name, ResourceLocation wool);

    T nested();

}
