package games.moegirl.sinocraft.sinocore.registry;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import net.minecraft.data.DataProvider;

import java.util.function.Function;
import java.util.function.Supplier;

public interface IDataProviderRegistry {

    <T extends DataProvider> Supplier<T> put(Function<IDataGenContext, ? extends T> builder, boolean run);

    default <T extends DataProvider> Supplier<T> put(Function<IDataGenContext, ? extends T> builder) {
        return put(builder, true);
    }
}
