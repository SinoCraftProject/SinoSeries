package games.moegirl.sinocraft.sinocore.data.gen;

import games.moegirl.sinocraft.sinocore.utility.Reference;
import net.minecraft.data.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class DataGenerator {

    protected final List<Function<DataGenContext, DataProvider>> providers = new ArrayList<>();

    public <T extends DataProvider> Supplier<T> put(Function<DataGenContext, T> builder) {
        Reference<T> reference = new Reference<>();
        providers.add(ctx -> {
            T provider = builder.apply(ctx);
            reference.set(provider);
            return provider;
        });
        return reference;
    }

    public void accept(DataGenContext context) {
        // No-op
    }
}
