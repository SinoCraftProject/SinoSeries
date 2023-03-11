package games.moegirl.sinocraft.sinocore.old.world;

import games.moegirl.sinocraft.sinocore.old.utility.Functions;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PlacedFeatureRegister {

    private boolean isRegistered = false;
    private final List<Entry<?, ?>> features = new ArrayList<>();
    private final String modid;

    public PlacedFeatureRegister(String modid) {
        this.modid = modid;
    }

    public void register() {
        if (!isRegistered) {
            MinecraftForge.EVENT_BUS.register(this);
            isRegistered = true;
        }
    }

    public <C extends FeatureConfiguration, B extends BaseFeatureBuilder<C, B>> Entry<C, B>
    register(GenerationStep.Decoration decoration, String name, PlacedFilter filter, Supplier<B> feature) {
        Entry<C, B> e = new Entry<>(feature, filter, new ResourceLocation(modid, name), decoration);
        features.add(e);
        return e;
    }

    public Entry<OreConfiguration, OreFeatureBuilder> registerOre(String name, PlacedFilter filter, Supplier<OreFeatureBuilder> feature) {
        return register(GenerationStep.Decoration.UNDERGROUND_DECORATION, name, filter, feature);
    }

    public Entry<OreConfiguration, OreFeatureBuilder> registerOre(String name, PlacedFilter filter, Function<OreFeatureBuilder, OreFeatureBuilder> feature) {
        return registerOre(name, filter, Functions.decorate(OreFeatureBuilder::new, feature));
    }

    public Entry<OreConfiguration, OreFeatureBuilder> registerOre(String name, Function<OreFeatureBuilder, OreFeatureBuilder> feature) {
        return registerOre(name, PlacedFilter.ALL, feature);
    }

    public Entry<TreeConfiguration, TreeFeatureBuilder> registerTree(String name, PlacedFilter filter, Supplier<TreeFeatureBuilder> feature) {
        return register(GenerationStep.Decoration.VEGETAL_DECORATION, name, filter, feature);
    }

    public Entry<TreeConfiguration, TreeFeatureBuilder> registerTree(String name, PlacedFilter filter, Function<TreeFeatureBuilder, TreeFeatureBuilder> feature) {
        return registerTree(name, filter, Functions.decorate(TreeFeatureBuilder::new, feature));
    }

    public Entry<TreeConfiguration, TreeFeatureBuilder> registerTree(String name, Function<TreeFeatureBuilder, TreeFeatureBuilder> feature) {
        return registerTree(name, PlacedFilter.ALL, feature);
    }

    // todo fix: biome loading event
//    @SubscribeEvent(priority = EventPriority.HIGH)
//    public void onGenerator(BiomeLoadingEvent event) {
//        BiomeGenerationSettingsBuilder generation = event.getGeneration();
//        ResourceLocation name = event.getName();
//        Biome.BiomeCategory category = event.getCategory();
//        Biome.ClimateSettings climate = event.getClimate();
//        BiomeSpecialEffects effects = event.getEffects();
//        features.stream()
//                .filter(e -> e.test(name, category, climate, effects))
//                .forEach(e -> generation.addFeature(e.decoration(), e.get()));
//    }

    public static final class Entry<C extends FeatureConfiguration, B extends BaseFeatureBuilder<C, B>> implements Supplier<Holder<PlacedFeature>> {
        private final Supplier<B> supplier;
        private final PlacedFilter filter;
        private final ResourceLocation name;
        private final GenerationStep.Decoration decoration;

        private B builder = null;

        public Entry(Supplier<B> supplier,
                     PlacedFilter filter, ResourceLocation name,
                     GenerationStep.Decoration decoration) {
            this.supplier = supplier;
            this.filter = filter;
            this.name = name;
            this.decoration = decoration;
        }

        boolean test(ResourceLocation biomeName, Biome.Precipitation category, Biome.ClimateSettings climate, BiomeSpecialEffects effects) {
            return filter.test(biomeName, category, climate, effects);
        }

        @Override
        public Holder<PlacedFeature> get() {
            return getBuilder().register(name);
        }

        public GenerationStep.Decoration decoration() {
            return decoration;
        }

        public B getBuilder() {
            if (builder == null) {
                builder = supplier.get();
            }
            return builder;
        }
    }

}
