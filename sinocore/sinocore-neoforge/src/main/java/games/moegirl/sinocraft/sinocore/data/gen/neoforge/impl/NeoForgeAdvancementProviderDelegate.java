package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.advancement.IAdvancementGenerator;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.AdvancementProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContext;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class NeoForgeAdvancementProviderDelegate extends AdvancementProviderDelegateBase {

    private final List<AdvancementProvider.AdvancementGenerator> generators = new ArrayList<>();
    private final NeoForgeDataGenContext context;

    public NeoForgeAdvancementProviderDelegate(NeoForgeDataGenContext context) {
        super(new NeoForgeAdvancementProviderBuilder(context));
        this.context = context;
    }

    public List<AdvancementProvider.AdvancementGenerator> getGenerators() {
        return generators;
    }

    @Override
    public void addAdvancement(IAdvancementGenerator generator) {
        generators.add(new AdvancementProvider.AdvancementGenerator() {

            @Override
            public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver,
                                 ExistingFileHelper existingFileHelper) {
                generator.generate(registries, saver, context);
            }

            @Override
            public AdvancementSubProvider toSubProvider(ExistingFileHelper existingFileHelper) {
                return Objects.requireNonNullElseGet(generator.toSubProvider(context),
                        () -> AdvancementProvider.AdvancementGenerator.super.toSubProvider(existingFileHelper));
            }
        });
    }
}
