package games.moegirl.sinocraft.sinocore.registry.neoforge;

import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.registry.IKeyRegistry;
import games.moegirl.sinocraft.sinocore.utility.neoforge.ModBusHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class NeoForgeKeyRegistry implements IKeyRegistry {

    private final Set<Pair<KeyMapping, BiConsumer<KeyMapping, Minecraft>>> keys = new HashSet<>();
    private final String modId;
    private final IEventBus bus;

    public NeoForgeKeyRegistry(String modId) {
        this.modId = modId;
        this.bus = ModBusHelper.getModBus(modId);
    }

    @Override
    public KeyMapping register(KeyMapping key, BiConsumer<KeyMapping, Minecraft> action) {
        keys.add(new Pair<>(key, action));
        return key;
    }

    @Override
    public KeyMapping register(KeyMapping key, Runnable action) {
        register(key, (k, c) -> action.run());
        return key;
    }

    @Override
    public String modId() {
        return modId;
    }

    @Override
    public void register() {
        bus.register(new KeyRegisterHandler());
        NeoForge.EVENT_BUS.register(new KeyActionHandler());
    }

    private final class KeyRegisterHandler {

        @SubscribeEvent
        public void onKeyRegister(RegisterKeyMappingsEvent event) {
            for (Pair<KeyMapping, BiConsumer<KeyMapping, Minecraft>> key : keys) {
                event.register(key.getFirst());
            }
        }
    }

    private final class KeyActionHandler {

        @SubscribeEvent
        public void onClientTick(ClientTickEvent.Post event) {
            for (Pair<KeyMapping, BiConsumer<KeyMapping, Minecraft>> key : keys) {
                while (key.getFirst().consumeClick()) {
                    key.getSecond().accept(key.getFirst(), Minecraft.getInstance());
                }
            }
        }
    }
}
