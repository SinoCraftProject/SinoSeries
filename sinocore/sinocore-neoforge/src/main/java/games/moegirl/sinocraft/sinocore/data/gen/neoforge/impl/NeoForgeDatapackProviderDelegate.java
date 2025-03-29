package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.DatapackProviderDelegateBase;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NeoForgeDatapackProviderDelegate extends DatapackProviderDelegateBase {

    // RegistryBootstrap 内 key 依赖于注册先后顺序
    private List<Entry> entries = new ArrayList<>();
    private Entry lastEntry = null;

    public NeoForgeDatapackProviderDelegate(DataGenContext context) {
        super(new NeoForgeDatapackProviderBuilderImpl(context));
    }

    public List<Entry> getData() {
        return entries;
    }

    // Fixme: qyl27: migrate to 1.21
//    @Override
//    public <T> void add(ResourceKey<? extends Registry<T>> type, Consumer<BootstapContext<T>> register) {
//        if (lastEntry != null && Objects.equals(lastEntry.type, type)) {
//            lastEntry.consumer = Functions.compose(lastEntry.consumer, register);
//        } else {
//            entries.add(lastEntry = new Entry(type, register));
//        }
//    }

    public static class Entry {
        final ResourceKey type;
        Consumer consumer;

        public Entry(ResourceKey type, Consumer consumer) {
            this.type = type;
            this.consumer = consumer;
        }
    }
}
