package games.moegirl.sinocraft.sinocore.data.gen;

import net.minecraft.data.DataProvider;

import java.util.function.Function;
import java.util.function.Supplier;

public interface IDataGenerator {

    <T extends DataProvider> Supplier<T> put(Function<IDataGenContext, ? extends T> builder, boolean run);

    default <T extends DataProvider> Supplier<T> put(Function<IDataGenContext, ? extends T> builder) {
        return put(builder, true);
    }
}
