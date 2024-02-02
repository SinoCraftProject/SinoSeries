package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.datagen.forge.ForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.mixin_interfaces.IRenamedProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ForgeLootTableProviderImpl extends LootTableProvider implements IRenamedProvider {

    private final String modId;
    private ForgeLootTableProviderDelegateImpl delegate;

    public ForgeLootTableProviderImpl(ForgeDataGenContextImpl context) {
        super(context.getOutput(), Set.of(), new ArrayList<>());
        modId = context.getModId();
    }

    public void setDelegate(ForgeLootTableProviderDelegateImpl delegate) {
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
    public String getNewName() {
        return "Loot Tables: " + modId;
    }
}
