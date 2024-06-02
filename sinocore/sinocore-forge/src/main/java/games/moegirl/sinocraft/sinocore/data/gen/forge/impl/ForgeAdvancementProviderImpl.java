package games.moegirl.sinocraft.sinocore.data.gen.forge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.forge.ForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.mixin_interfaces.interfaces.IRenamedProvider;
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
