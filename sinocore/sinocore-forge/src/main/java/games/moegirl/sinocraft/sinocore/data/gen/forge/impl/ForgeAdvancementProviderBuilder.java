package games.moegirl.sinocraft.sinocore.data.gen.forge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.DataProviderBuilderBase;
import games.moegirl.sinocraft.sinocore.data.gen.forge.ForgeDataGenContextImpl;

public class ForgeAdvancementProviderBuilder
        extends DataProviderBuilderBase<ForgeAdvancementProviderDelegate, ForgeAdvancementProviderImpl> {

    private final ForgeDataGenContextImpl context;

    public ForgeAdvancementProviderBuilder(ForgeDataGenContextImpl context) {
        this.context = context;
    }

    @Override
    public ForgeAdvancementProviderImpl build(ForgeAdvancementProviderDelegate delegate) {
        return new ForgeAdvancementProviderImpl(context, delegate.getGenerators(), getDataProviderName());
    }

    @Override
    public String getDataProviderName() {
        return "Advancements: " + context.getModId();
    }
}
