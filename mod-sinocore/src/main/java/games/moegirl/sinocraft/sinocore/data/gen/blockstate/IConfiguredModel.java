package games.moegirl.sinocraft.sinocore.data.gen.blockstate;

import games.moegirl.sinocraft.sinocore.data.gen.model.IModelFile;

public interface IConfiguredModel {

    public interface Builder<T extends IBlockStateBuilder> {
        Builder<T> modelFile(IModelFile file);

        Builder<T> rotationX(int value);

        Builder<T> rotationY(int value);

        Builder<T> uvLock(boolean value);

        Builder<T> weight(int value);

        IConfiguredModel buildLast();

        IConfiguredModel[] build();

        T addModel();

        Builder<T> nextModel();
    }
}
