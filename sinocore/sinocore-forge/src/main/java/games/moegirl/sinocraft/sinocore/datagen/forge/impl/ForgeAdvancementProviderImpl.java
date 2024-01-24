package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.datagen.forge.ForgeDataGenContextImpl;
import games.moegirl.sinocraft.sinocore.mixin_interfaces.IRenamedProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ForgeAdvancementProviderImpl extends ForgeAdvancementProvider implements IRenamedProvider {

    private final String modId;

    public ForgeAdvancementProviderImpl(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper, List<AdvancementGenerator> subProviders, String modId) {
        super(output, registries, existingFileHelper, subProviders);
        this.modId = modId;
    }

    @Override
    public String getNewName() {
        return "Advancements: " + modId;
    }

    public static class Builder implements Supplier<DataProvider> {

        public List<AdvancementGenerator> generators;
        private final ForgeDataGenContextImpl context;

        public Builder(ForgeDataGenContextImpl context) {
            this.context = context;
        }

        public void setGenerators(List<AdvancementGenerator> generators) {
            this.generators = generators;
        }

        @Override
        public ForgeAdvancementProviderImpl get() {
            return new ForgeAdvancementProviderImpl(context.getOutput(), context.registriesFuture(),
                    context.getExistingFileHelper(), generators, context.getModId());
        }
    }
}
