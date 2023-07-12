package games.moegirl.sinocraft.sinodivination.util.register;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

/**
 * 对 DeferredRegister 的封装
 *
 * <p>通常用于在创建元素时进行某些操作，因此基类不实现 register 方法</p>
 *
 * @author luqin2007
 */
public abstract class DeferredRegisters<T> {

    public static ItemRegister item(String modid) {
        return new ItemRegister(modid);
    }

    private final String modid;
    private DeferredRegister<T> register;

    protected DeferredRegisters(String modid) {
        this.modid = modid;
    }

    public synchronized DeferredRegister<T> getRegister() {
        if (register == null) {
            register = create(modid);
        }
        return register;
    }

    public String getModId() {
        return modid;
    }

    abstract protected DeferredRegister<T> create(String modid);

    // delegate

    public void register(IEventBus bus) {
        getRegister().register(bus);
    }

    public Collection<RegistryObject<T>> getEntries() {
        return getRegister().getEntries();
    }

    public TagKey<T> createTagKey(String path) {
        return register.createTagKey(path);
    }

    public TagKey<T> createTagKey(ResourceLocation location) {
        return register.createTagKey(location);
    }

    public ResourceKey<? extends Registry<T>> getRegistryKey() {
        return register.getRegistryKey();
    }

    public ResourceLocation getRegistryName() {
        return register.getRegistryName();
    }
}
