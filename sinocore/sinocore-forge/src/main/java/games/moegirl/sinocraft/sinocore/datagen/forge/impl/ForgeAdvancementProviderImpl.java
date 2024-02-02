package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.datagen.forge.ForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.mixin_interfaces.IRenamedProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;

public class ForgeAdvancementProviderImpl extends ForgeAdvancementProvider implements IRenamedProvider {

    private final String name;

    public ForgeAdvancementProviderImpl(ForgeDataGenContextImpl context, List<AdvancementGenerator> subProviders, String name) {
        super(context.getOutput(), context.registriesFuture(), context.getExistingFileHelper(), subProviders);
        this.name = name;
    }

    @Override
    public String getNewName() {
        return "Advancements: " + name;
    }
}
