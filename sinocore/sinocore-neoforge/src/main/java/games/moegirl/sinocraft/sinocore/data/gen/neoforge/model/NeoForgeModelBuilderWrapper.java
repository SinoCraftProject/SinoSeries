package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import games.moegirl.sinocraft.sinocore.data.gen.model.IModelBuilder;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelFile;
import games.moegirl.sinocraft.sinocore.utility.ISelf;
import games.moegirl.sinocraft.sinocore.utility.IWrapper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class NeoForgeModelBuilderWrapper<F extends ModelBuilder<F>, T extends NeoForgeModelBuilderWrapper<F, T>>
        extends NeoForgeModelFileWrapper<F, T> implements IModelBuilder<T> {

    public NeoForgeModelBuilderWrapper(F modelBuilder) {
        super(modelBuilder);
    }

    @Override
    public T parent(IModelFile parent) {
        if (parent instanceof NeoForgeModelFileWrapper<?, ?> wrapper) {
            return reWrapper(modelFile.parent(wrapper.getOrigin()));
        }

        return self();
    }

    @Override
    public T texture(String key, String texture) {
        modelFile.texture(key, texture);
        return self();
    }

    @Override
    public T texture(String key, ResourceLocation texture) {
        modelFile.texture(key, texture);
        return self();
    }

    @Override
    public T renderType(String renderType) {
        modelFile.renderType(renderType);
        return self();
    }

    @Override
    public T renderType(ResourceLocation renderType) {
        modelFile.renderType(renderType);
        return self();
    }

    @Override
    public ITransformsBuilder<T> transforms() {
        return new NeoForgeTransformsBuilderWrapper<>(modelFile.transforms(), self());
    }

    @Override
    public T ambientOcclusion(boolean ao) {
        modelFile.ao(ao);
        return self();
    }

    @Override
    public T guiLight(BlockModel.GuiLight light) {
        modelFile.guiLight(light);
        return self();
    }

    @Override
    public IElementBuilder<T> element() {
        return new NeoForgeElementBuilderWrapper<>(modelFile.element(), self());
    }

    @Override
    public IElementBuilder<T> element(int index) {
        return new NeoForgeElementBuilderWrapper<>(modelFile.element(index), self());
    }

    @Override
    public int getElementCount() {
        return modelFile.getElementCount();
    }

    @Override
    public @NotNull ISelf<? extends T> newWrapper(F object) {
        return new NeoForgeModelBuilderWrapper<>(modelFile);
    }

    record NeoForgeTransformsBuilderWrapper<
            F extends ModelBuilder<F>,
            T extends NeoForgeModelBuilderWrapper<F, T>,
            S extends NeoForgeTransformsBuilderWrapper<F, T, S>
            >
            (ModelBuilder<?>.TransformsBuilder builder, T parent)
            implements ITransformsBuilder<T>, IWrapper<ModelBuilder<?>.TransformsBuilder, S> {

        @Override
        public ITransformVecBuilder<T> transform(ItemDisplayContext type) {
            return new NeoForgeTransformVecBuilderWrapper<>(builder.transform(type), self());
        }

        @Override
        public T end() {
            return parent.reWrapper((F) builder.end());
        }

        @Override
        public ModelBuilder<?>.@NotNull TransformsBuilder getOrigin() {
            return builder;
        }

        @Override
        public @NotNull ISelf<? extends S> newWrapper(ModelBuilder<?>.TransformsBuilder object) {
            return new NeoForgeTransformsBuilderWrapper<>(object, parent);
        }
    }

    record NeoForgeTransformVecBuilderWrapper<
            F extends ModelBuilder<F>,
            T extends NeoForgeModelBuilderWrapper<F, T>,
            S extends NeoForgeTransformsBuilderWrapper<F, T, S>,
            P extends NeoForgeTransformVecBuilderWrapper<F, T, S, P>
            >
            (ModelBuilder<?>.TransformsBuilder.TransformVecBuilder builder, S parent)
            implements ITransformVecBuilder<T>, IWrapper<ModelBuilder<?>.TransformsBuilder.TransformVecBuilder, P> {

        @Override
        public ITransformVecBuilder<T> rotation(float x, float y, float z) {
            return reWrapper(builder.rotation(x, y, z));
        }

        @Override
        public ITransformVecBuilder<T> leftRotation(float x, float y, float z) {
            return reWrapper(builder.leftRotation(x, y, z));
        }

        @Override
        public ITransformVecBuilder<T> translation(float x, float y, float z) {
            return reWrapper(builder.translation(x, y, z));
        }

        @Override
        public ITransformVecBuilder<T> scale(float sc) {
            return reWrapper(builder.scale(sc));
        }

        @Override
        public ITransformVecBuilder<T> scale(float x, float y, float z) {
            return reWrapper(builder.scale(x, y, z));
        }

        @Override
        public ITransformVecBuilder<T> rightRotation(float x, float y, float z) {
            return reWrapper(builder.rightRotation(x, y, z));
        }

        @Override
        public ITransformsBuilder<T> end() {
            return parent.reWrapper(builder.end());
        }

        @Override
        public ModelBuilder<?>.TransformsBuilder.@NotNull TransformVecBuilder getOrigin() {
            return builder;
        }

        @Override
        public @NotNull ISelf<? extends P> newWrapper(ModelBuilder<?>.TransformsBuilder.TransformVecBuilder object) {
            return new NeoForgeTransformVecBuilderWrapper<>(object, parent);
        }
    }

    record NeoForgeElementBuilderWrapper<
            F extends ModelBuilder<F>,
            T extends NeoForgeModelBuilderWrapper<F, T>,
            S extends NeoForgeElementBuilderWrapper<F, T, S>
            >
            (ModelBuilder<?>.ElementBuilder builder, T parent)
            implements IModelBuilder.IElementBuilder<T>, IWrapper<ModelBuilder<?>.ElementBuilder, S> {

        @Override
        public IModelBuilder.IElementBuilder<T> from(float x, float y, float z) {
            return reWrapper(builder.from(x, y, z));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> to(float x, float y, float z) {
            return reWrapper(builder.to(x, y, z));
        }

        @Override
        public IModelBuilder.IFaceBuilder<T> face(Direction dir) {
            return new ForgeFaceBuilderWrapper<>(builder.face(dir), self());
        }

        @Override
        public IModelBuilder.IRotationBuilder<T> rotation() {
            return new NeoForgeRotationBuilderWrapper<>(builder.rotation(), self());
        }

        @Override
        public IModelBuilder.IElementBuilder<T> shade(boolean shade) {
            return reWrapper(builder.shade(shade));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> allFaces(BiConsumer<Direction, IFaceBuilder<T>> action) {
            return reWrapper(builder.allFaces((direction, faceBuilder) -> action.accept(direction, new ForgeFaceBuilderWrapper<>(faceBuilder, self()))));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> faces(BiConsumer<Direction, IModelBuilder.IFaceBuilder<T>> action) {
            return reWrapper(builder.faces((direction, faceBuilder) -> action.accept(direction, new ForgeFaceBuilderWrapper<>(faceBuilder, self()))));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> textureAll(String texture) {
            return reWrapper(builder.textureAll(texture));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> texture(String texture) {
            return reWrapper(builder.texture(texture));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> cube(String texture) {
            return reWrapper(builder.cube(texture));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> emissivity(int blockLight, int skyLight) {
            return reWrapper(builder.emissivity(blockLight, skyLight));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> color(int color) {
            return reWrapper(builder.color(color));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> ambientOcclusion(boolean ao) {
            return reWrapper(builder.ao(ao));
        }

        @Override
        public T end() {
            return parent.reWrapper((F) builder.end());
        }

        @Override
        public ModelBuilder<?>.@NotNull ElementBuilder getOrigin() {
            return builder;
        }

        @Override
        public @NotNull ISelf<? extends S> newWrapper(ModelBuilder<?>.ElementBuilder object) {
            return new NeoForgeElementBuilderWrapper<>(object, parent);
        }
    }

    record ForgeFaceBuilderWrapper<
            F extends ModelBuilder<F>,
            T extends NeoForgeModelBuilderWrapper<F, T>,
            S extends NeoForgeElementBuilderWrapper<F, T, S>,
            P extends ForgeFaceBuilderWrapper<F, T, S, P>
            >
            (ModelBuilder<?>.ElementBuilder.FaceBuilder builder, S parent)
            implements IModelBuilder.IFaceBuilder<T>, IWrapper<ModelBuilder<?>.ElementBuilder.FaceBuilder, P> {

        @Override
        public IModelBuilder.IFaceBuilder<T> cullface(@Nullable Direction dir) {
            return reWrapper(builder.cullface(dir));
        }

        @Override
        public IModelBuilder.IFaceBuilder<T> tintindex(int index) {
            return reWrapper(builder.tintindex(index));
        }

        @Override
        public IModelBuilder.IFaceBuilder<T> texture(String texture) {
            return reWrapper(builder.texture(texture));
        }

        @Override
        public IModelBuilder.IFaceBuilder<T> uvs(float u1, float v1, float u2, float v2) {
            return reWrapper(builder.uvs(u1, v1, u2, v2));
        }

        @Override
        public IModelBuilder.IFaceBuilder<T> rotation(IModelBuilder.FaceRotation rot) {
            return reWrapper(builder.rotation(switch (rot) {
                case ZERO -> ModelBuilder.FaceRotation.ZERO;
                case UPSIDE_DOWN -> ModelBuilder.FaceRotation.UPSIDE_DOWN;
                case CLOCKWISE_90 -> ModelBuilder.FaceRotation.CLOCKWISE_90;
                case COUNTERCLOCKWISE_90 -> ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90;
            }));
        }

        @Override
        public IModelBuilder.IFaceBuilder<T> emissivity(int blockLight, int skyLight) {
            return reWrapper(builder.emissivity(blockLight, skyLight));
        }

        @Override
        public IModelBuilder.IFaceBuilder<T> color(int color) {
            return reWrapper(builder.color(color));
        }

        @Override
        public IModelBuilder.IFaceBuilder<T> hasAmbientOcclusion(boolean ao) {
            return reWrapper(builder.ao(ao));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> end() {
            return parent.reWrapper(builder.end());
        }

        @Override
        public ModelBuilder<?>.ElementBuilder.@NotNull FaceBuilder getOrigin() {
            return builder;
        }

        @Override
        public @NotNull ISelf<? extends P> newWrapper(ModelBuilder<?>.ElementBuilder.FaceBuilder object) {
            return new ForgeFaceBuilderWrapper<>(object, parent);
        }
    }

    record NeoForgeRotationBuilderWrapper<
            F extends ModelBuilder<F>,
            T extends NeoForgeModelBuilderWrapper<F, T>,
            S extends NeoForgeElementBuilderWrapper<F, T, S>,
            P extends NeoForgeRotationBuilderWrapper<F, T, S, P>
            >
            (ModelBuilder<?>.ElementBuilder.RotationBuilder builder, S parent)
            implements IModelBuilder.IRotationBuilder<T>, IWrapper<ModelBuilder<?>.ElementBuilder.RotationBuilder, P> {

        @Override
        public IModelBuilder.IRotationBuilder<T> origin(float x, float y, float z) {
            return reWrapper(builder.origin(x, y, z));
        }

        @Override
        public IModelBuilder.IRotationBuilder<T> axis(Direction.Axis axis) {
            return reWrapper(builder.axis(axis));
        }

        @Override
        public IModelBuilder.IRotationBuilder<T> angle(float angle) {
            return reWrapper(builder.angle(angle));
        }

        @Override
        public IModelBuilder.IRotationBuilder<T> rescale(boolean rescale) {
            return reWrapper(builder.rescale(rescale));
        }

        @Override
        public IModelBuilder.IElementBuilder<T> end() {
            return parent.reWrapper(builder.end());
        }

        @Override
        public ModelBuilder<?>.ElementBuilder.@NotNull RotationBuilder getOrigin() {
            return builder;
        }

        @Override
        public @NotNull ISelf<? extends P> newWrapper(ModelBuilder<?>.ElementBuilder.RotationBuilder object) {
            return new NeoForgeRotationBuilderWrapper<>(object, parent);
        }
    }
}
