package games.moegirl.sinocraft.sinocore.registry.fabric;

import com.google.common.base.Suppliers;
import games.moegirl.sinocraft.sinocore.registry.ICustomStatRegister;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

import java.util.Optional;

public class FabricCustomStatRegister implements ICustomStatRegister {

    private final IRegistry<ResourceLocation> reg;

    public FabricCustomStatRegister(String modId) {
        reg = RegistryManager._create(modId, Registries.CUSTOM_STAT);
    }

    @Override
    public String modId() {
        return reg.modId();
    }

    @Override
    public void register() {
        reg.register();
    }

    @Override
    public ResourceLocation register(String name, StatFormatter statFormatter) {
        ResourceLocation statKey = ResourceLocation.fromNamespaceAndPath(modId(), name);
        reg.register(name, Suppliers.ofInstance(statKey));
        Stats.CUSTOM.get(statKey, statFormatter);
        return statKey;
    }

    @Override
    public ResourceLocation register(String name) {
        return register(name, StatFormatter.DEFAULT);
    }

    @Override
    public Optional<Stat<?>> get(ResourceLocation key) {
        if (Stats.CUSTOM.contains(key)) {
            return Optional.empty();
        }
        return Optional.of(Stats.CUSTOM.get(key));
    }
}
