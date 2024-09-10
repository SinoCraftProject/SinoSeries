package games.moegirl.sinocraft.sinocore.data.gen.neoforge.blockstate;

import games.moegirl.sinocraft.sinocore.data.gen.blockstate.IBlockStateBuilder;
import games.moegirl.sinocraft.sinocore.data.gen.blockstate.IConfiguredModel;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelFile;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import java.util.Arrays;

public record NeoForgeConfiguredModel(ConfiguredModel model) implements IConfiguredModel {

//    public static <T> Builder<T> builder() {
//        var obj = new Builder<T>();
//        internal = ConfiguredModel.builder();
//    }

//    public static Builder<IVariantStateBuilder> builder(IVariantStateBuilder outer,
//                                                        IVariantStateBuilder.IPartialState state) {
//    }

    public static class Builder<T extends IBlockStateBuilder> implements IConfiguredModel.Builder<T> {
        final T builder;
        final ConfiguredModel.Builder<?> internal;

        public Builder(T builder, ConfiguredModel.Builder<?> internal) {
            this.builder = builder;
            this.internal = internal;
        }

        @Override
        public IConfiguredModel.Builder<T> modelFile(IModelFile file) {
            internal.modelFile(new ModelFile.UncheckedModelFile(file.getLocation()));
            return this;
        }

        @Override
        public IConfiguredModel.Builder<T> rotationX(int value) {
            internal.rotationX(value);
            return this;
        }

        @Override
        public IConfiguredModel.Builder<T> rotationY(int value) {
            internal.rotationY(value);
            return this;
        }

        @Override
        public IConfiguredModel.Builder<T> uvLock(boolean value) {
            internal.uvLock(value);
            return this;
        }

        @Override
        public IConfiguredModel.Builder<T> weight(int value) {
            internal.weight(value);
            return this;
        }

        @Override
        public IConfiguredModel buildLast() {
            var obj = internal.buildLast();
            return new NeoForgeConfiguredModel(obj);
        }

        @Override
        public IConfiguredModel[] build() {
            return Arrays.stream(internal.build())
                    .map(NeoForgeConfiguredModel::new)
                    .toArray(NeoForgeConfiguredModel[]::new);
        }

        @Override
        public T addModel() {
            internal.addModel();
            return builder;
        }

        @Override
        public IConfiguredModel.Builder<T> nextModel() {
            return new Builder<>(builder, internal.nextModel());
        }
    }

}
