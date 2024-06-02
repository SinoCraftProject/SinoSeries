package games.moegirl.sinocraft.sinocore.data.gen.forge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.LootTableProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.forge.ForgeDataGenContextImpl;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class ForgeLootTableProviderDelegate extends LootTableProviderDelegateBase {

    public ForgeLootTableProviderDelegate(ForgeDataGenContextImpl context) {
        super(new ForgeLootTableProviderImpl(context), context.getModId());
        ((ForgeLootTableProviderImpl) getForgeProvider()).setDelegate(this);
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
