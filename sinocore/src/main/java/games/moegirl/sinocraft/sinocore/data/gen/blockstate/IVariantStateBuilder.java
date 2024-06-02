package games.moegirl.sinocraft.sinocore.data.gen.blockstate;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.SortedMap;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IVariantStateBuilder extends IBlockStateBuilder {

    IVariantStateBuilder addModels(IPartialState state, IConfiguredModel... models);

    IPartialState partialState();

    IVariantStateBuilder forAllStates(Function<BlockState, IConfiguredModel[]> mapper);

    IVariantStateBuilder forAllStatesExcept(Function<BlockState, IConfiguredModel[]> mapper, Property<?>... ignored);

    interface IPartialState extends Predicate<BlockState> {

        Block getOwner();

        IPartialState partialState();

        SortedMap<Property<?>, Comparable<?>> getStatesSet();

        IVariantStateBuilder setModels(IConfiguredModel... models);

        IPartialState addModels(IConfiguredModel... models);

        IConfiguredModel.Builder<IVariantStateBuilder> modelForState();

        <T extends Comparable<T>> IPartialState with(Property<T> prop, T value);
    }
}
