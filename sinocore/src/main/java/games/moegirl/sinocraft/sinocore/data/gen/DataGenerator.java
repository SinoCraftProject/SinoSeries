package games.moegirl.sinocraft.sinocore.data.gen;

import games.moegirl.sinocraft.sinocore.utility.Reference;
import net.minecraft.data.DataProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class DataGenerator {

    protected final Map<Function<DataGenContext, DataProvider>, Boolean> providers = new HashMap<>();

    public <T extends DataProvider> Supplier<T> put(Function<DataGenContext, ? extends T> builder, boolean run) {
        Reference<T> reference = new Reference<>();
        providers.put(ctx -> {
            T provider = builder.apply(ctx);
            reference.set(provider);
            return provider;
        }, run);
        return reference;
    }

    public <T extends DataProvider> Supplier<T> put(Function<DataGenContext, ? extends T> builder) {
        return put(builder, true);
    }

    public void accept(DataGenContext context) {
        // No-op
    }
}
