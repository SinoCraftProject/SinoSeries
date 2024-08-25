package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.neoforge.ForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.interfaces.bridge.IRenamedProviderBridge;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;

public class ForgeAdvancementProviderImpl extends ForgeAdvancementProvider implements IRenamedProviderBridge {

    private final String name;

    public ForgeAdvancementProviderImpl(ForgeDataGenContextImpl context, List<AdvancementGenerator> subProviders, String name) {
        super(context.getOutput(), context.registriesFuture(), context.getExistingFileHelper(), subProviders);
        this.name = name;
    }

    @Override
    public String sino$getNewName() {
        return "Advancements: " + name;
    }
}
