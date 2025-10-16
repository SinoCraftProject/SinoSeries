package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.LootTableProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContext;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class NeoForgeLootTableProviderDelegate extends LootTableProviderDelegateBase {

    public NeoForgeLootTableProviderDelegate(NeoForgeDataGenContext context) {
        super(new NeoForgeLootTableProviderImpl(context), context.getModId(), context.getRegistries());
        ((NeoForgeLootTableProviderImpl) getForgeProvider()).setDelegate(this);
    }

    public void collectSubProviders(List<LootTableProvider.SubProviderEntry> tables) {
        if (!blocks.isEmpty()) {
            tables.add(new LootTableProvider.SubProviderEntry((registries) -> blocks.asProvider(), LootContextParamSets.BLOCK));
        }

        if (!entities.isEmpty()) {
            tables.add(new LootTableProvider.SubProviderEntry((registries) -> entities.asProvider(), LootContextParamSets.ENTITY));
        }

        simple.forEach((params, provider) -> tables.add(new LootTableProvider.SubProviderEntry((registries) -> provider, params)));
    }
}
