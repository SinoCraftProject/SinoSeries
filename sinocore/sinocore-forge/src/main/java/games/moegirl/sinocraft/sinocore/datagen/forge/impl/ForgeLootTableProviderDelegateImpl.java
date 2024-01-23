package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.datagen.delegate.LootTableProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.datagen.forge.ForgeDataGenContextImpl;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class ForgeLootTableProviderDelegateImpl extends LootTableProviderDelegateBase {

    public ForgeLootTableProviderDelegateImpl(ForgeDataGenContextImpl context) {
        super(new ForgeLootTableProviderImpl(context), context.getModId());
    }

    public void collectSubProviders(List<LootTableProvider.SubProviderEntry> tables) {
        if (!blocks.isEmpty()) {
            tables.add(new LootTableProvider.SubProviderEntry(blocks::asProvider, LootContextParamSets.BLOCK));
        }

        if (!entities.isEmpty()) {
            tables.add(new LootTableProvider.SubProviderEntry(entities::asProvider, LootContextParamSets.ENTITY));
        }

        simple.forEach((params, provider) -> tables.add(new LootTableProvider.SubProviderEntry(() -> provider, params)));
    }
}
