package games.moegirl.sinocraft.sinocore.data.gen.model;

import com.google.gson.JsonArray;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.function.BiConsumer;

public interface IModelBuilder<T extends IModelBuilder<T>> extends IModelFile {

    T parent(IModelFile parent);

    T texture(String key, String texture);

    T texture(String key, ResourceLocation texture);

    T renderType(String renderType);

    T renderType(ResourceLocation renderType);

    ITransformsBuilder<T> transforms();

    T ambientOcclusion(boolean ao);

    T guiLight(BlockModel.GuiLight light);

    IElementBuilder<T> element();

    IElementBuilder<T> element(int index);

    int getElementCount();

    default String serializeLocOrKey(String tex) {
        if (tex.charAt(0) == '#') {
            return tex;
        }
        return ResourceLocation.parse(tex).toString();
    }

    default JsonArray serializeVector3f(Vector3f vec) {
        JsonArray ret = new JsonArray();
        ret.add(serializeFloat(vec.x()));
        ret.add(serializeFloat(vec.y()));
        ret.add(serializeFloat(vec.z()));
        return ret;
    }

    default Number serializeFloat(float f) {
        if ((int) f == f) {
            return (int) f;
        }
        return f;
    }

    interface ITransformsBuilder<T extends IModelBuilder<T>> {
        ITransformVecBuilder<T> transform(ItemDisplayContext type);

        T end();
    }

    interface ITransformVecBuilder<T extends IModelBuilder<T>> {

        ITransformVecBuilder<T> rotation(float x, float y, float z);

        ITransformVecBuilder<T> leftRotation(float x, float y, float z);

        ITransformVecBuilder<T> translation(float x, float y, float z);

        ITransformVecBuilder<T> scale(float sc);

        ITransformVecBuilder<T> scale(float x, float y, float z);

        ITransformVecBuilder<T> rightRotation(float x, float y, float z);

        ITransformsBuilder<T> end();
    }
    
    interface IElementBuilder<T extends IModelBuilder<T>> {

        IElementBuilder<T> from(float x, float y, float z);

        IElementBuilder<T> to(float x, float y, float z);

        IFaceBuilder<T> face(Direction dir);

        IRotationBuilder<T> rotation();

        IElementBuilder<T> shade(boolean shade);

        IElementBuilder<T> allFaces(BiConsumer<Direction, IFaceBuilder<T>> action);

        IElementBuilder<T> faces(BiConsumer<Direction, IFaceBuilder<T>> action);

        IElementBuilder<T> textureAll(String texture);

        IElementBuilder<T> texture(String texture);

        IElementBuilder<T> cube(String texture);

        IElementBuilder<T> emissivity(int blockLight, int skyLight);

        IElementBuilder<T> color(int color);

        IElementBuilder<T> ambientOcclusion(boolean ao);

        T end();
    }

    interface IFaceBuilder<T extends IModelBuilder<T>> {

        IFaceBuilder<T> cullface(@Nullable Direction dir);

        IFaceBuilder<T> tintindex(int index);

        IFaceBuilder<T> texture(String texture);

        IFaceBuilder<T> uvs(float u1, float v1, float u2, float v2);

        IFaceBuilder<T> rotation(FaceRotation rot);

        IFaceBuilder<T> emissivity(int blockLight, int skyLight);

        IFaceBuilder<T> color(int color);

        IFaceBuilder<T> hasAmbientOcclusion(boolean ao);

        IElementBuilder<T> end();
    }

    interface IRotationBuilder<T extends IModelBuilder<T>> {

        IRotationBuilder<T> origin(float x, float y, float z);

        IRotationBuilder<T> axis(Direction.Axis axis);

        IRotationBuilder<T> angle(float angle);

        IRotationBuilder<T> rescale(boolean rescale);

        IElementBuilder<T> end();
    }

    enum FaceRotation {
        ZERO, CLOCKWISE_90, UPSIDE_DOWN, COUNTERCLOCKWISE_90
    }
}
