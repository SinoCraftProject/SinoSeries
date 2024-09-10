package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.DataProviderBuilderBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContextImpl;

public class NeoForgeAdvancementProviderBuilder
        extends DataProviderBuilderBase<NeoForgeAdvancementProviderDelegate, NeoForgeAdvancementProviderImpl> {

    private final NeoForgeDataGenContextImpl context;

    public NeoForgeAdvancementProviderBuilder(NeoForgeDataGenContextImpl context) {
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
