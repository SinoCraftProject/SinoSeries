package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.DataProviderBuilderBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContext;

public class NeoForgeAdvancementProviderBuilder
        extends DataProviderBuilderBase<NeoForgeAdvancementProviderDelegate, NeoForgeAdvancementProviderImpl> {

    private final NeoForgeDataGenContext context;

    public NeoForgeAdvancementProviderBuilder(NeoForgeDataGenContext context) {
        this.context = context;
    }

    @Override
    public NeoForgeAdvancementProviderImpl build(NeoForgeAdvancementProviderDelegate delegate) {
        return new NeoForgeAdvancementProviderImpl(context, delegate.getGenerators(), getDataProviderName());
    }

    @Override
    public String getDataProviderName() {
        return "Advancements: " + context.getModId();
    }
}
