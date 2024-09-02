package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.interfaces.bridge.IRenamedProviderBridge;
import net.neoforged.neoforge.common.data.AdvancementProvider;

import java.util.List;

public class NeoForgeAdvancementProviderImpl extends AdvancementProvider implements IRenamedProviderBridge {

    private final String name;

    public NeoForgeAdvancementProviderImpl(NeoForgeDataGenContextImpl context, List<AdvancementGenerator> subProviders, String name) {
        super(context.getOutput(), context.getRegistries(), context.getExistingFileHelper(), subProviders);
        this.name = name;
    }

    @Override
    public String sino$getNewName() {
        return "Advancements: " + name;
    }
}
