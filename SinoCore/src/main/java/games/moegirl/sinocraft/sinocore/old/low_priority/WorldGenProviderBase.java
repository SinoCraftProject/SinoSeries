package games.moegirl.sinocraft.sinocore.old.low_priority;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

// todo fix: WorldGenProviderBase
@SuppressWarnings({"unused"})
public abstract class WorldGenProviderBase implements DataProvider {

//    private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
//    private final Logger logger = LoggerFactory.getLogger(WorldGenProviderBase.class);
//
//    protected final RegistryAccess registryAccess;
//    protected final DynamicOps<JsonElement> ops;
//    private final boolean dump;
//
//    private final DataGenerator generator;
//    private final String modid;
//
//    protected final MappedRegistry<LevelStem> dimensions;
//    protected final MappedRegistry<DimensionType> dimensionTypes;
//    protected final MappedRegistry<Biome> biomes;
//    protected final MappedRegistry<ConfiguredWorldCarver<?>> configuredCarvers;
//    protected final MappedRegistry<ConfiguredFeature<?, ?>> configuredFeatures;
//    protected final MappedRegistry<ConfiguredStructureFeature<?, ?>> configuredStructureFeatures;
//    protected final MappedRegistry<DensityFunction> densityFunctions;
//    protected final MappedRegistry<NormalNoise.NoiseParameters> noiseParameters;
//    protected final MappedRegistry<NoiseGeneratorSettings> noiseGeneratorSettings;
//    protected final MappedRegistry<PlacedFeature> placedFeatures;
//    protected final MappedRegistry<StructureProcessorList> processorLists;
//    protected final MappedRegistry<StructureSet> structureSets;
//    protected final MappedRegistry<StructureTemplatePool> templatePools;
//
//    protected final Map<ResourceKey<Registry<?>>, Map<ResourceLocation, Object>> objects = new HashMap<>();

    public WorldGenProviderBase(DataGenerator generator, String modid, boolean dump) {
//        this.generator = generator;
//        this.modid = modid;
//        this.dump = dump;
//
//        this.registryAccess = RegistryAccess.builtinCopy();
//        this.dimensionTypes = (MappedRegistry<DimensionType>) registryAccess.ownedRegistryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
//        this.biomes = (MappedRegistry<Biome>) registryAccess.ownedRegistryOrThrow(Registry.BIOME_REGISTRY);
//        this.configuredCarvers = (MappedRegistry<ConfiguredWorldCarver<?>>) registryAccess.ownedRegistryOrThrow(Registry.CONFIGURED_CARVER_REGISTRY);
//        this.configuredFeatures = (MappedRegistry<ConfiguredFeature<?, ?>>) registryAccess.ownedRegistryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY);
//        this.configuredStructureFeatures = (MappedRegistry<ConfiguredStructureFeature<?, ?>>) registryAccess.ownedRegistryOrThrow(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY);
//        this.densityFunctions = (MappedRegistry<DensityFunction>) registryAccess.ownedRegistryOrThrow(Registry.DENSITY_FUNCTION_REGISTRY);
//        this.noiseParameters = (MappedRegistry<NormalNoise.NoiseParameters>) registryAccess.ownedRegistryOrThrow(Registry.NOISE_REGISTRY);
//        this.noiseGeneratorSettings = (MappedRegistry<NoiseGeneratorSettings>) registryAccess.ownedRegistryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
//        this.placedFeatures = (MappedRegistry<PlacedFeature>) registryAccess.ownedRegistryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
//        this.processorLists = (MappedRegistry<StructureProcessorList>) registryAccess.ownedRegistryOrThrow(Registry.PROCESSOR_LIST_REGISTRY);
//        this.structureSets = (MappedRegistry<StructureSet>) registryAccess.ownedRegistryOrThrow(Registry.STRUCTURE_SET_REGISTRY);
//        this.templatePools = (MappedRegistry<StructureTemplatePool>) registryAccess.ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
//        this.dimensions = (MappedRegistry<LevelStem>) DimensionType.defaultDimensions(registryAccess, 0);
//
//        this.ops = RegistryOps.create(JsonOps.INSTANCE, registryAccess);
    }

    protected abstract void addAll();
//
//    @Override
//    public void run(HashCache cache) {
//        if (dump) {
//            new WorldgenRegistryDumpReport(generator).run(cache);
//        }
//
//        Path path = generator.getOutputFolder();
//        addAll();
//
//        save(path, cache, dimensions, LevelStem.CODEC);
//        save(path, cache, dimensionTypes, DimensionType.DIRECT_CODEC);
//        save(path, cache, biomes, Biome.DIRECT_CODEC);
//        save(path, cache, configuredCarvers, ConfiguredWorldCarver.DIRECT_CODEC);
//        save(path, cache, configuredFeatures, ConfiguredFeature.DIRECT_CODEC);
//        save(path, cache, configuredStructureFeatures, ConfiguredStructureFeature.DIRECT_CODEC);
//        save(path, cache, densityFunctions, DensityFunction.DIRECT_CODEC);
//        save(path, cache, noiseParameters, NormalNoise.NoiseParameters.DIRECT_CODEC);
//        save(path, cache, noiseGeneratorSettings, NoiseGeneratorSettings.DIRECT_CODEC);
//        save(path, cache, placedFeatures, PlacedFeature.DIRECT_CODEC);
//        save(path, cache, processorLists, StructureProcessorType.DIRECT_CODEC);
//        save(path, cache, structureSets, StructureSet.DIRECT_CODEC);
//        save(path, cache, templatePools, StructureTemplatePool.DIRECT_CODEC);
//    }
//
//    private <T> void save(Path path, HashCache cache, Registry<T> register, Codec<T> codec) {
//        ResourceKey<? extends Registry<T>> key = register.key();
//        Path target = path.resolve("data").resolve(modid).resolve(key.location().getPath());
//        Map<ResourceLocation, T> values = (Map<ResourceLocation, T>) objects.get(key);
//        if (values != null && !values.isEmpty()) {
//            values.forEach((name, value) -> {
//                JsonElement json = codec.encodeStart(ops, value)
//                        .getOrThrow(false, s -> {
//                            throw new RuntimeException("Couldn't serialize element " + name + ": " + s);
//                        });
//                Path output = target.resolve(name.getPath() + ".json");
//                save(cache, json, output);
//            });
//        }
//    }
//
//    private void save(HashCache cache, JsonElement element, Path path) {
//        try {
//            DataProvider.save(gson, cache, element, path);
//        } catch (IOException e) {
//            logger.error("Couldn't save element {}", path, e);
//        }
//    }
//
//    protected void addDimension(ResourceLocation name, LevelStem stem) {
//        add(name, stem, dimensions);
//    }
//
//    protected ResourceLocation addDimensionType(ResourceLocation name, DimensionType type) {
//        return add(name, type, dimensionTypes);
//    }
//
//    protected Holder<DimensionType> getDimensionType(ResourceLocation name) {
//        return get(name, () -> DimensionType.create(OptionalLong.empty(), false, false, false,
//                false, 1, false, false, false,
//                false, false, 0, 16, 16,
//                BlockTags.INFINIBURN_OVERWORLD, DimensionType.OVERWORLD_EFFECTS, 1), dimensionTypes);
//    }
//
//    protected ResourceLocation addBiome(ResourceLocation name, Biome biome) {
//        return add(name, biome, biomes);
//    }
//
//    protected Holder<Biome> getBiome(ResourceLocation name) {
//        return get(name, () -> new Biome.BiomeBuilder()
//                .precipitation(Biome.Precipitation.NONE)
//                .biomeCategory(Biome.BiomeCategory.DESERT)
//                .temperature(100)
//                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
//                .downfall(1)
//                .specialEffects(new BiomeSpecialEffects.Builder()
//                        .waterFogColor(0)
//                        .skyColor(0)
//                        .fogColor(0)
//                        .waterColor(0)
//                        .build())
//                .mobSpawnSettings(MobSpawnSettings.EMPTY)
//                .generationSettings(BiomeGenerationSettings.EMPTY)
//                .build(), biomes);
//    }
//
//    protected ResourceLocation addConfiguredCarver(ResourceLocation name, ConfiguredWorldCarver<?> carver) {
//        return add(name, carver, configuredCarvers);
//    }
//
//    protected Holder<ConfiguredWorldCarver<?>> getConfiguredCarver(ResourceLocation name) {
//        return get(name, () -> new ConfiguredWorldCarver<>(WorldCarver.CAVE, new CaveCarverConfiguration(1,
//                ConstantHeight.of(VerticalAnchor.absolute(0)), ConstantFloat.of(0), VerticalAnchor.absolute(0),
//                false, ConstantFloat.of(0), ConstantFloat.of(0), ConstantFloat.of(0))), configuredCarvers);
//    }
//
//    protected ResourceLocation addConfiguredFeature(ResourceLocation name, ConfiguredFeature<?, ?> feature) {
//        return add(name, feature, configuredFeatures);
//    }
//
//    protected Holder<ConfiguredFeature<?, ?>> getConfiguredFeature(ResourceLocation name) {
//        return get(name, () -> new ConfiguredFeature<>(Feature.NO_OP, FeatureConfiguration.NONE), configuredFeatures);
//    }
//
//    protected ResourceLocation addConfiguredStructureFeature(ResourceLocation name, ConfiguredStructureFeature<?, ?> feature) {
//        return add(name, feature, configuredStructureFeatures);
//    }
//
//    protected Holder<ConfiguredStructureFeature<?, ?>> getConfiguredStructureFeature(ResourceLocation name) {
//        return get(name, () -> new ConfiguredStructureFeature<>(StructureFeature.WOODLAND_MANSION, NoneFeatureConfiguration.INSTANCE,
//                HolderSet.direct(getBiome(name)), false, new HashMap<>()), configuredStructureFeatures);
//    }
//
//    protected ResourceLocation addDensityFunction(ResourceLocation name, DensityFunction function) {
//        return add(name, function, densityFunctions);
//    }
//
//    protected Holder<DensityFunction> getDensityFunction(ResourceLocation name) {
//        return get(name, () -> DensityFunctions.endIslands(0), densityFunctions);
//    }
//
//    protected ResourceLocation addNoiseParameter(ResourceLocation name, NormalNoise.NoiseParameters parameters) {
//        return add(name, parameters, noiseParameters);
//    }
//
//    protected Holder<NormalNoise.NoiseParameters> getNoiseParameter(ResourceLocation name) {
//        return get(name, () -> new NormalNoise.NoiseParameters(0, 0), noiseParameters);
//    }
//
//    protected ResourceLocation addNoiseGeneratorSettings(ResourceLocation name, NoiseGeneratorSettings settings) {
//        return add(name, settings, noiseGeneratorSettings);
//    }
//
//    protected Holder<NoiseGeneratorSettings> getNoiseGeneratorSettings(ResourceLocation name) {
//        return get(name, () -> {
//            NoiseSettings settings = NoiseSettings.create(16, 16, new NoiseSamplingSettings(1, 1, 1, 1),
//                    new NoiseSlider(0, 0, 0),
//                    new NoiseSlider(0, 0, 0), 0, 0,
//                    TerrainShaper.overworld(false));
//            NoiseRouterWithOnlyNoises router = new NoiseRouterWithOnlyNoises(
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value(),
//                    getDensityFunction(modLoc("dummy")).value()
//            );
//            BlockState block = Blocks.STONE.defaultBlockState();
//            BlockState fluid = Blocks.WATER.defaultBlockState();
//            SurfaceRules.RuleSource rule = sequence()
//                    .ifAbovePreliminarySurface(SurfaceRules.state(block))
//                    .build();
//            return new NoiseGeneratorSettings(settings, block, fluid, router, rule, 1,
//                    false, false, false, false);
//        }, noiseGeneratorSettings);
//    }
//
//    protected ResourceLocation addPlacedFeature(ResourceLocation name, PlacedFeature feature) {
//        return add(name, feature, placedFeatures);
//    }
//
//    protected Holder<PlacedFeature> getPlacedFeature(ResourceLocation name) {
//        return get(name, () -> new PlacedFeature(getConfiguredFeature(name), new LinkedList<>()), placedFeatures);
//    }
//
//    protected ResourceLocation addProcessorList(ResourceLocation name, StructureProcessorList list) {
//        return add(name, list, processorLists);
//    }
//
//    protected Holder<StructureProcessorList> getProcessorList(ResourceLocation name) {
//        return get(name, () -> new StructureProcessorList(new LinkedList<>()), processorLists);
//    }
//
//    protected ResourceLocation addStructureSet(ResourceLocation name, StructureSet set) {
//        return add(name, set, structureSets);
//    }
//
//    protected Holder<StructureSet> getStructureSet(ResourceLocation name) {
//        return get(name, () -> new StructureSet(getConfiguredStructureFeature(name),
//                new ConcentricRingsStructurePlacement(1, 1, 1)), structureSets);
//    }
//
//    protected ResourceLocation addTemplatePool(ResourceLocation name, StructureTemplatePool pool) {
//        return add(name, pool, templatePools);
//    }
//
//    protected Holder<StructureTemplatePool> getTemplatePool(ResourceLocation name) {
//        return get(name, () -> new StructureTemplatePool(name, name, new LinkedList<>()), templatePools);
//    }
//
//    protected <T> Holder<T> get(ResourceLocation name, Supplier<T> value, WritableRegistry<T> registry) {
//        ResourceKey<T> key = ResourceKey.create(registry.key(), name);
//        if (registry.containsKey(key)) {
//            return registry.getHolderOrThrow(key);
//        }
//        return register(name, value.get(), registry, false);
//    }
//
//    protected <T> ResourceLocation add(ResourceLocation name, T value, WritableRegistry<T> registry) {
//        ResourceKey key = registry.key();
//        Holder<T> holder = register(name, value, registry, true);
//        objects.computeIfAbsent(key, __ -> new HashMap<>()).put(name, holder.value());
//        return name;
//    }
//
//    private <T> Holder<T> register(ResourceLocation name, T value, WritableRegistry<T> registry, boolean override) {
//        if (registry.containsKey(name)) {
//            if (override) {
//                int id = registry.getId(registry.get(name));
//                return registry.registerOrOverride(OptionalInt.of(id), ResourceKey.create(registry.key(), name), value, Lifecycle.stable());
//            } else {
//                return registry.getHolderOrThrow(ResourceKey.create(registry.key(), name));
//            }
//        } else {
//            return registry.register(ResourceKey.create(registry.key(), name), value, Lifecycle.stable());
//        }
//    }
//
//    public RuleSourceSequence sequence() {
//        return new RuleSourceSequence();
//    }
//
//    @Override
//    public String getName() {
//        return "Worldgen: " + modid;
//    }
//
//    protected ResourceLocation modLoc(String name) {
//        return new ResourceLocation(modid, name);
//    }
//
//    protected ResourceLocation mcLoc(String name) {
//        return new ResourceLocation(name);
//    }
//
//    public static class RuleSourceSequence {
//
//        private final List<SurfaceRules.RuleSource> rules = new ArrayList<>();
//
//        public RuleSourceSequence add(SurfaceRules.RuleSource rule) {
//            rules.add(rule);
//            return this;
//        }
//
//        public RuleSourceSequence ifTure(SurfaceRules.ConditionSource ifTrue, SurfaceRules.RuleSource thenRun) {
//            return add(ifTrue(ifTrue, thenRun));
//        }
//
//        public RuleSourceSequence ifTure(SurfaceRules.ConditionSource ifTrue, RuleSourceSequence thenRun) {
//            return add(ifTrue(ifTrue, thenRun.build()));
//        }
//
//        public RuleSourceSequence ifBottomY(String randomName, int min, int max, SurfaceRules.RuleSource thenRun) {
//            return ifTure(verticalGradient(randomName, VerticalAnchor.aboveBottom(min), VerticalAnchor.aboveBottom(max)), thenRun);
//        }
//
//        public RuleSourceSequence ifAbovePreliminarySurface(SurfaceRules.RuleSource thenRun) {
//            return ifTure(SurfaceRules.abovePreliminarySurface(), thenRun);
//        }
//
//        public SurfaceRules.RuleSource build() {
//            return SurfaceRules.sequence(rules.toArray(SurfaceRules.RuleSource[]::new));
//        }
//    }
}
