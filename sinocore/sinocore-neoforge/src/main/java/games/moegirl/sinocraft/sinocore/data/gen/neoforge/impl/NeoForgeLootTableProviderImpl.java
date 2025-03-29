package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContext;
import games.moegirl.sinocraft.sinocore.interfaces.bridge.ISinoRenamedProviderBridge;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NeoForgeLootTableProviderImpl extends LootTableProvider implements ISinoRenamedProviderBridge {

    private final String modId;
    private NeoForgeLootTableProviderDelegate delegate;

    public NeoForgeLootTableProviderImpl(NeoForgeDataGenContext context) {
        super(context.getOutput(), Set.of(), new ArrayList<>(), context.getRegistries());
        modId = context.getModId();
    }

    public void setDelegate(NeoForgeLootTableProviderDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<SubProviderEntry> getTables() {
        List<SubProviderEntry> tables = super.getTables();
        delegate.generateData();
        delegate.collectSubProviders(tables);
        return tables;
    }

    @Override
    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext,
                            ProblemReporter.Collector problemCollector) {
    }

    @Override
    public String sino$getNewName() {
        return "Loot Tables: " + modId;
    }
}
