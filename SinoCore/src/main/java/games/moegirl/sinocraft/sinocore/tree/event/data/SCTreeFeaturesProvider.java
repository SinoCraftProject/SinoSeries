package games.moegirl.sinocraft.sinocore.tree.event.data;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.world.gen.ModConfiguredFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SCTreeFeaturesProvider extends DatapackBuiltinEntriesProvider {

    protected final List<Tree> treeTypes;

    public SCTreeFeaturesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                  String modid, List<Tree> treeTypes) {
        super(output, lookupProvider,
                new RegistrySetBuilder().add(Registries.CONFIGURED_FEATURE,
                        context -> registerAll(context, treeTypes)),
                Set.of(modid));

        this.treeTypes = treeTypes;
    }

    protected static void registerAll(BootstapContext<ConfiguredFeature<?, ?>> context, List<Tree> treeTypes) {
        for (var tree : treeTypes) {
            ModConfiguredFeatures.registerTree(context,
                    tree.getGrower().getResourceKey(), tree.getFeaturedConfiguration());
        }
    }
}
