package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.datagen.advancement.IAdvancementGenerator;
import games.moegirl.sinocraft.sinocore.datagen.delegate.AdvancementProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.datagen.forge.ForgeDataGenContextImpl;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ForgeAdvancementProviderDelegateImpl extends AdvancementProviderDelegateBase {

    private final List<ForgeAdvancementProvider.AdvancementGenerator> generators = new ArrayList<>();
    private final ForgeDataGenContextImpl context;

    public ForgeAdvancementProviderDelegateImpl(ForgeDataGenContextImpl context) {
        super("Advancements: " + context.getModId(), new ForgeAdvancementProviderImpl.Builder(context));
        this.context = context;
        this.getDataProviderIfExist()
                .map(sup -> (ForgeAdvancementProviderImpl.Builder) sup)
                .ifPresent(b -> b.setGenerators(generators));
    }

    @Override
    public void addAdvancement(IAdvancementGenerator generator) {
        generators.add(new ForgeAdvancementProvider.AdvancementGenerator() {
            @Override
            public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
                generator.generate(provider, consumer, context);
            }

            @Override
            public AdvancementSubProvider toSubProvider(ExistingFileHelper existingFileHelper) {
                return Objects.requireNonNullElseGet(generator.toSubProvider(context),
                        () -> ForgeAdvancementProvider.AdvancementGenerator.super.toSubProvider(existingFileHelper));
            }
        });
    }
}
