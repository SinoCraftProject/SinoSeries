package games.moegirl.sinocraft.sinocore.old.world;

import games.moegirl.sinocraft.sinocore.old.utility.Self;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedList;
import java.util.List;

/**
 * A builder for feature
 * <p>This used to build placed feature</p>
 */
public abstract class BaseFeatureBuilder<C extends FeatureConfiguration, SELF extends BaseFeatureBuilder<C, SELF>> implements Self<SELF> {

    protected final Feature<C> feature;
    protected final List<PlacementModifier> modifiers = new LinkedList<>();

    private volatile PlacedFeature result = null;
    private volatile Holder<PlacedFeature> holder = null;
    private volatile Holder<ConfiguredFeature<C, ?>> configured = null;

    private volatile boolean isConfiguredRegistered = false, isFeatureRegistered = false;

    public BaseFeatureBuilder(Feature<C> feature) {
        this.feature = feature;
    }

    /**
     * A modifier can set generated base position by range, times, etc...
     *
     * @param modifier generate modifier
     * @return this builder
     */
    public SELF addModifier(PlacementModifier modifier) {
        modifiers.add(modifier);
        return self();
    }

    /**
     * Add a modifier and remove same type of other modifiers
     *
     * @param modifier generate modifier
     * @return this builder
     */
    public SELF replaceModifier(PlacementModifier modifier) {
        modifiers.removeIf(modifier.getClass()::isInstance);
        modifiers.add(modifier);
        return self();
    }

    /**
     * Copy modifiers from another feature
     *
     * @param parent base feature
     * @return this builder
     */
    public SELF fromModifier(PlacedFeature parent) {
        modifiers.addAll(parent.placement());
        return self();
    }

    /**
     * Create a configuration base on parent
     *
     * @param parent base configuration
     * @return this builder
     */
    public abstract SELF fromConfiguration(C parent);

    /**
     * Create a configuration
     *
     * @return configuration
     */
    protected abstract C buildConfiguration();

    /**
     * Create {@link ConfiguredFeature} from the builder.
     * <p>Note: Not assure that the configured feature is registered in {@link FeatureUtils}.</p>
     *
     * @return configured feature, not ensure registered
     */
    public ConfiguredFeature<C, ?> buildConfigured() {
        return new ConfiguredFeature<>(feature, buildConfiguration());
    }

    /**
     * Create a {@link ConfiguredFeature} and register it.
     *
     * @param name default name of configured feature
     * @return a registered configured feature
     */
    // todo fix: feature registers
    public Holder<ConfiguredFeature<C,?>> registerConfigured(String name) {
//        if (!isConfiguredRegistered) {
//            configured = FeatureUtils.register(name, feature, buildConfiguration());
//            isConfiguredRegistered = true;
//        }
//        return configured;
        return null;
    }

    /**
     * Create a {@link ConfiguredFeature} and register it.
     *
     * @param modid mod id
     * @param name  name
     * @return a registered configured feature
     */
    public Holder<ConfiguredFeature<C,?>> registerConfigured(String modid, String name) {
        return registerConfigured(modid + ":" + name);
    }

    /**
     * Create a {@link ConfiguredFeature} and register it.
     *
     * @param id name
     * @return a registered configured feature
     */
    public Holder<ConfiguredFeature<C,?>> registerConfigured(ResourceLocation id) {
        return registerConfigured(id.toString());
    }

    /**
     * Create a {@link ConfiguredFeature} and register it.
     *
     * @param buildFor get name from it
     * @return a registered configured feature
     */
    public Holder<ConfiguredFeature<C,?>> registerConfigured(RegistryObject<?> buildFor) {
        return registerConfigured(buildFor.getId());
    }

    /**
     * Create {@link PlacedFeature} from the builder.
     * <p>Note: Not assure that the configured feature or placed feature is registered in {@link PlacementUtils}.</p>
     *
     * @return placed feature, not ensure registered
     */
    public PlacedFeature build() {
        if (result == null) {
            synchronized (this) {
                if (result == null) {
                    result = new PlacedFeature(Holder.direct(buildConfigured()), modifiers);
                }
            }
        }
        return result;
    }

    /**
     * Create a {@link PlacedFeature} and register it.
     *
     * @param name default name of configured feature and placed feature
     * @return a registered placed feature
     */
    public Holder<PlacedFeature> register(String name) {
        synchronized (this) {
            if (!isConfiguredRegistered) {
                registerConfigured(name);
                isConfiguredRegistered = true;
            }
            if (!isFeatureRegistered) {
                // todo fix: feature registers
//                holder = PlacementUtils.register(name, configured, modifiers);
                isFeatureRegistered = true;
            }
        }
        return holder;
    }

    /**
     * Create a {@link PlacedFeature} and register it, name is modid:name
     *
     * @param modid mod id
     * @param name  name
     * @return a registered placed feature
     */
    public Holder<PlacedFeature> register(String modid, String name) {
        return register(modid + ":" + name);
    }

    /**
     * Create a {@link PlacedFeature} and register it, name is modid:name
     *
     * @param id name
     * @return a registered placed feature
     */
    public Holder<PlacedFeature> register(ResourceLocation id) {
        return register(id.toString());
    }

    /**
     * Create a {@link PlacedFeature} and register it, name is builderFor.id
     *
     * @param buildFor get name from it
     * @return a registered placed feature
     */
    public Holder<PlacedFeature> register(RegistryObject<?> buildFor) {
        return register(buildFor.getId());
    }
}
