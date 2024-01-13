package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.IRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class FabricRegistryImpl<T> implements IRegistry<T> {

    final String modId;
    final ResourceKey<Registry<T>> key;
    Registry<T> registry;

    FabricRegistryImpl(String modId, ResourceKey<Registry<T>> key) {
        this.modId = modId;
        this.key = key;

        registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(key.location());
        if (registry == null) {
            // 不存在的注册表 -- 创建自定义注册表
            // todo 待测试
            registry = FabricRegistryBuilder.createSimple(key)
                    .attribute(RegistryAttribute.SYNCED)
                    .buildAndRegister();
            throw new RuntimeException();
        }
    }

    @Override
    public void register() {
    }

    @Override
    public <R extends T> IRef<T, R> register(String name, Supplier<? extends R> supplier) {
        ResourceLocation id = new ResourceLocation(modId, name);
        ResourceKey<T> eKey = ResourceKey.create(key, id);
        return (IRef<T, R>) new FabricRefImpl<>(Registry.registerForHolder(registry, eKey, supplier.get()));
    }
}
