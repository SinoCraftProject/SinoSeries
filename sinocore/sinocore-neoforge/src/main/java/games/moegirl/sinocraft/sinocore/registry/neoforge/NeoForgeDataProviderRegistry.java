package games.moegirl.sinocraft.sinocore.registry.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCorePlatform;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.registry.IDataProviderRegistry;
import games.moegirl.sinocraft.sinocore.utility.Reference;
import games.moegirl.sinocraft.sinocore.utility.neoforge.ModBusHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgeDataProviderRegistry implements IDataProviderRegistry, Consumer<GatherDataEvent> {

    private final List<Function<IDataGenContext, DataProvider>> providers = new ArrayList<>();
    private final List<Function<IDataGenContext, DataProvider>> providersNotRun = new ArrayList<>();

    public NeoForgeDataProviderRegistry(String modId) {
        ModBusHelper.getModBus(modId).addListener(this);
    }

    @Override
    public <T extends DataProvider> Supplier<T> put(Function<IDataGenContext, ? extends T> builder, boolean run) {
        Reference<T> reference = new Reference<>();
        (run ? providers : providersNotRun).add(ctx -> {
            T provider = builder.apply(ctx);
            reference.set(provider);
            return provider;
        });
        return reference;
    }

    @Override
    public void accept(GatherDataEvent event) {
        IDataGenContext context = SinoCorePlatform.buildDataGeneratorContext(event, event.getLookupProvider());
        DataGenerator generator = event.getGenerator();
        providersNotRun.forEach(builder -> generator.addProvider(false, builder.apply(context)));
        providers.forEach(builder -> generator.addProvider(true, builder.apply(context)));
    }
}
