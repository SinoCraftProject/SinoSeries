package games.moegirl.sinocraft.sinocore.data.gen.model;

import net.minecraft.resources.ResourceLocation;

public interface IModelProvider<T extends IModelBuilder<T>> {

    ISpecialModelBuilder<T> getSpecialBuilder();

    IModelResourceHelper<T> getResourceHelper();

    T getBuilder(String path);

    T withExistingParent(String name, String parent);

    T withExistingParent(String name, ResourceLocation parent);

    T cube(String name, ResourceLocation down, ResourceLocation up, ResourceLocation north,
           ResourceLocation south, ResourceLocation east, ResourceLocation west);

    T singleTexture(String name, ResourceLocation parent, ResourceLocation texture);

    default T singleTexture(String name, String parent, ResourceLocation texture) {
        return singleTexture(name, getResourceHelper().mcLoc(parent), texture);
    }

    T singleTexture(String name, ResourceLocation parent, String textureKey, ResourceLocation texture);

    default T singleTexture(String name, String parent, String textureKey, ResourceLocation texture) {
        return singleTexture(name, getResourceHelper().mcLoc(parent), textureKey, texture);
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

    T cross(String name, ResourceLocation cross);
}
