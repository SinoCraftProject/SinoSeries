package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.DatapackProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.util.Functions;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ForgeDatapackProviderDelegateImpl extends DatapackProviderDelegateBase {

    // RegistryBootstrap 内 key 依赖于注册先后顺序
    private List<Entry> entries = new ArrayList<>();
    private Entry lastEntry = null;

    public ForgeDatapackProviderDelegateImpl(IDataGenContext context) {
        super(new ForgeDatapackProviderBuilderImpl(context));
    }

    public List<Entry> getData() {
        return entries;
    }

    @Override
    public <T> void add(ResourceKey<? extends Registry<T>> type, Consumer<BootstapContext<T>> register) {
        if (lastEntry != null && Objects.equals(lastEntry.type, type)) {
            lastEntry.consumer = Functions.compose(lastEntry.consumer, register);
        } else {
            entries.add(lastEntry = new Entry(type, register));
        }
    }

    public static class Entry {
        final ResourceKey type;
        Consumer consumer;

        public Entry(ResourceKey type, Consumer consumer) {
            this.type = type;
            this.consumer = consumer;
        }
    }
}
