package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.DataProviderBuilderBase;
import net.minecraft.core.RegistrySetBuilder;

public class ForgeDatapackProviderBuilderImpl
        extends DataProviderBuilderBase<ForgeDatapackProviderDelegateImpl, ForgeDatapackBuiltinEntriesProviderImpl> {

    private final IDataGenContext context;

    public ForgeDatapackProviderBuilderImpl(IDataGenContext context) {
        this.context = context;
    }

    @Override
    public ForgeDatapackBuiltinEntriesProviderImpl build(ForgeDatapackProviderDelegateImpl delegate) {
        RegistrySetBuilder builder = new RegistrySetBuilder();
        delegate.getData().forEach(entry -> builder.add(entry.type, ctx -> entry.consumer.accept(ctx)));
        return new ForgeDatapackBuiltinEntriesProviderImpl(context, builder, getDataProviderName());
    }

    @Override
    public String getDataProviderName() {
        return "Registries: " + context.getModId();
    }
}
