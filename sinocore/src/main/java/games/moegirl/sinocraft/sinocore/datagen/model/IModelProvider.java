package games.moegirl.sinocraft.sinocore.datagen.model;

import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;

public interface IModelProvider<T extends IModelBuilder<T>> {

    String getBlockFolder();

    String getItemFolder();

    IResourceType getTextureResource();

    IResourceType getModelResource();

    IResourceType getModelWithExtensionResource();

    String getFolder();

    T getBuilder(String path);

    ResourceLocation modLoc(String name);

    ResourceLocation mcLoc(String name);

    T withExistingParent(String name, String parent);

    T withExistingParent(String name, ResourceLocation parent);

    T cube(String name, ResourceLocation down, ResourceLocation up, ResourceLocation north,
           ResourceLocation south, ResourceLocation east, ResourceLocation west);

    T singleTexture(String name, ResourceLocation parent, ResourceLocation texture);

    default T singleTexture(String name, String parent, ResourceLocation texture) {
        return singleTexture(name, mcLoc(parent), texture);
    }

    T singleTexture(String name, ResourceLocation parent, String textureKey, ResourceLocation texture);

    default T singleTexture(String name, String parent, String textureKey, ResourceLocation texture) {
        return singleTexture(name, mcLoc(parent), textureKey, texture);
    }

    T cubeAll(String name, ResourceLocation texture);

    T cubeTop(String name, ResourceLocation side, ResourceLocation top);

    default T sideBottomTop(String name, String parent, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return withExistingParent(name, parent)
                .texture("side", side)
                .texture("bottom", bottom)
                .texture("top", top);
    }

    T cubeBottomTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top);

    T cubeColumn(String name, ResourceLocation side, ResourceLocation end);

    T cubeColumnHorizontal(String name, ResourceLocation side, ResourceLocation end);

    T orientableVertical(String name, ResourceLocation side, ResourceLocation front);

    T orientableWithBottom(String name, ResourceLocation side, ResourceLocation front, ResourceLocation bottom, ResourceLocation top);

    T orientable(String name, ResourceLocation side, ResourceLocation front, ResourceLocation top);

    T crop(String name, ResourceLocation crop);

    T cross(String name, ResourceLocation cross);

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
        return withExistingParent(name, getBlockFolder() + "/" + parent)
                .texture("pane", pane)
                .texture("edge", edge);
    }

    T panePost(String name, ResourceLocation pane, ResourceLocation edge);

    T paneSide(String name, ResourceLocation pane, ResourceLocation edge);

    T paneSideAlt(String name, ResourceLocation pane, ResourceLocation edge);

    T paneNoSide(String name, ResourceLocation pane);

    T paneNoSideAlt(String name, ResourceLocation pane);

    default T door(String name, String model, ResourceLocation bottom, ResourceLocation top) {
        return withExistingParent(name, getBlockFolder() + "/" + model)
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

    IModelFile getExistingFile(ResourceLocation path);

    Path getPath(T model);
}
