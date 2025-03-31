package games.moegirl.sinocraft.sinocore.registry.fabric;

import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.registry.IKeyRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class FabricKeyRegistry implements IKeyRegistry {

    private final List<Pair<KeyMapping, BiConsumer<KeyMapping, Minecraft>>> keys = new ArrayList<>();
    private final String modId;

    public FabricKeyRegistry(String modId) {
        this.modId = modId;
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
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            for (Pair<KeyMapping, BiConsumer<KeyMapping, Minecraft>> key : keys) {
                KeyBindingHelper.registerKeyBinding(key.getFirst());
            }
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                for (Pair<KeyMapping, BiConsumer<KeyMapping, Minecraft>> key : keys) {
                    while (key.getFirst().consumeClick()) {
                        key.getSecond().accept(key.getFirst(), Minecraft.getInstance());
                    }
                }
            });
        } else {
            throw new RuntimeException("Key registry can only be registered in client side (use in ClientModInitializer entrypoint)!");
        }
    }
}
