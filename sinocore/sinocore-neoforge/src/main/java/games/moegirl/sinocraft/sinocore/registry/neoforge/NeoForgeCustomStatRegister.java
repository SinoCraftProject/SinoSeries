package games.moegirl.sinocraft.sinocore.registry.neoforge;

import com.google.common.base.Suppliers;
import games.moegirl.sinocraft.sinocore.neoforge.SinoCoreNeoForge;
import games.moegirl.sinocraft.sinocore.registry.ICustomStatRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NeoForgeCustomStatRegister implements ICustomStatRegister {

    private final DeferredRegister<ResourceLocation> reg;
    private final IEventBus bus;
    private final String modid;

    private final Map<ResourceLocation, StatFormatter> stats = new HashMap<>();

    public NeoForgeCustomStatRegister(String modId) {
        reg = DeferredRegister.create(Registries.CUSTOM_STAT, modId);
        bus = SinoCoreNeoForge.getModBus();
        modid = modId;
    }

    @Override
    public String modId() {
        return modid;
    }

    @Override
    public void register() {
        reg.register(bus);
        bus.addListener(this::onSetup);
    }

    @Override
    public ResourceLocation register(String name, StatFormatter statFormatter) {
        ResourceLocation statKey = ResourceLocation.fromNamespaceAndPath(modId(), name);
        reg.register(name, Suppliers.ofInstance(statKey));
        stats.put(statKey, statFormatter);
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

    private void onSetup(FMLCommonSetupEvent event) {
        stats.forEach(Stats.CUSTOM::get);
    }
}
