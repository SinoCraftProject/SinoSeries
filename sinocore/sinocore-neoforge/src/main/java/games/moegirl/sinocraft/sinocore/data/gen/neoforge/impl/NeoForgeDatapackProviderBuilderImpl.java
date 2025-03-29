package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.DataProviderBuilderBase;
import net.minecraft.core.RegistrySetBuilder;

public class NeoForgeDatapackProviderBuilderImpl
        extends DataProviderBuilderBase<NeoForgeDatapackProviderDelegate, NeoForgeDatapackBuiltinEntriesProviderImpl> {

    private final DataGenContext context;

    public NeoForgeDatapackProviderBuilderImpl(DataGenContext context) {
        this.context = context;
    }

    @Override
    public NeoForgeDatapackBuiltinEntriesProviderImpl build(NeoForgeDatapackProviderDelegate delegate) {
        RegistrySetBuilder builder = new RegistrySetBuilder();
        delegate.getData().forEach(entry -> builder.add(entry.type, ctx -> entry.consumer.accept(ctx)));
        return new NeoForgeDatapackBuiltinEntriesProviderImpl(context, builder, getDataProviderName());
    }

    @Override
    public String getDataProviderName() {
        return "Registries: " + context.getModId();
    }
}
