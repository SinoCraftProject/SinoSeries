package games.moegirl.sinocraft.sinocore.data.gen.forge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.forge.ForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.interfaces.bridge.IRenamedProviderBridge;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ForgeLootTableProviderImpl extends LootTableProvider implements IRenamedProviderBridge {

    private final String modId;
    private ForgeLootTableProviderDelegate delegate;

    public ForgeLootTableProviderImpl(ForgeDataGenContextImpl context) {
        super(context.getOutput(), Set.of(), new ArrayList<>());
        modId = context.getModId();
    }

    public void setDelegate(ForgeLootTableProviderDelegate delegate) {
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
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {
    }

    @Override
    public String sino$getNewName() {
        return "Loot Tables: " + modId;
    }
}
