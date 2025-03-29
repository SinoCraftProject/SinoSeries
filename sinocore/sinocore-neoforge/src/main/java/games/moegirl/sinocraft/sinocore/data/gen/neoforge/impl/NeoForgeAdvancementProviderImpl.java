package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContext;
import games.moegirl.sinocraft.sinocore.interfaces.bridge.ISinoRenamedProviderBridge;
import net.neoforged.neoforge.common.data.AdvancementProvider;

import java.util.List;

public class NeoForgeAdvancementProviderImpl extends AdvancementProvider implements ISinoRenamedProviderBridge {

    private final String name;

    public NeoForgeAdvancementProviderImpl(NeoForgeDataGenContext context, List<AdvancementGenerator> subProviders, String name) {
        super(context.getOutput(), context.getRegistries(), context.getExistingFileHelper(), subProviders);
        this.name = name;
    }

    @Override
    public String sino$getNewName() {
        return "Advancements: " + name;
    }
}
