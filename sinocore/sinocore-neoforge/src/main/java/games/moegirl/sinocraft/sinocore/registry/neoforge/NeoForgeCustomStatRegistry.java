package games.moegirl.sinocraft.sinocore.registry.neoforge;

import com.google.common.base.Suppliers;
import games.moegirl.sinocraft.sinocore.registry.ICustomStatRegistry;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.utility.neoforge.ModBusHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Optional;

public class NeoForgeCustomStatRegistry implements ICustomStatRegistry {

    private final IRegistry<ResourceLocation> reg;
    private final IEventBus bus;
    private final String modId;

    public NeoForgeCustomStatRegistry(String modId) {
        this.reg = new NeoForgeRegistry<>(modId, Registries.CUSTOM_STAT);
        this.bus = ModBusHelper.getModBus(modId);
        this.modId = modId;
    }

    @Override
    public String modId() {
        return modId;
    }

    @Override
    public void register() {
        reg.register();
        bus.addListener(this::onSetup);
    }

    @Override
    public ResourceLocation register(String name, StatFormatter statFormatter) {
        ResourceLocation statKey = ResourceLocation.fromNamespaceAndPath(modId, name);
        reg.register(name, Suppliers.ofInstance(statKey));
        return statKey;
    }

    @Override
    public Registry<ResourceLocation> getRegistry() {
        return reg.getRegistry();
    }

    @Override
    public Iterable<IRegRef<ResourceLocation>> getEntries() {
        return reg.getEntries();
    }

    @Override
    public Optional<IRegRef<ResourceLocation>> get(ResourceLocation id) {
        return reg.get(id);
    }

    private void onSetup(FMLCommonSetupEvent event) {
        reg.getEntries().forEach(r -> Stats.CUSTOM.get(r.get()));
    }
}
