package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.DataProviderBuilderBase;
import games.moegirl.sinocraft.sinocore.datagen.forge.ForgeDataGenContextImpl;

public class ForgeAdvancementProviderBuilderImpl
        extends DataProviderBuilderBase<ForgeAdvancementProviderDelegateImpl, ForgeAdvancementProviderImpl> {

    private final ForgeDataGenContextImpl context;

    public ForgeAdvancementProviderBuilderImpl(ForgeDataGenContextImpl context) {
        this.context = context;
    }

    @Override
    public ForgeAdvancementProviderImpl build(ForgeAdvancementProviderDelegateImpl delegate) {
        return new ForgeAdvancementProviderImpl(context, delegate.getGenerators(), getDataProviderName());
    }

    @Override
    public String getDataProviderName() {
        return "Advancements: " + context.getModId();
    }
}
