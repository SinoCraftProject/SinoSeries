package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import net.minecraft.core.RegistrySetBuilder;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;

public class NeoForgeDatapackBuiltinEntriesProviderImpl extends DatapackBuiltinEntriesProvider {

    private final String name;

    public NeoForgeDatapackBuiltinEntriesProviderImpl(IDataGenContext context, RegistrySetBuilder builder, String name) {
        super(context.getOutput(), context.getRegistries(), builder, Set.of(context.getModId()));
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
