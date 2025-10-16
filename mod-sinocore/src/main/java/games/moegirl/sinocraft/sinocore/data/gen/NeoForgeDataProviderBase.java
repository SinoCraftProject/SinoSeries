package games.moegirl.sinocraft.sinocore.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.ProviderDelegateBase;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.util.concurrent.CompletableFuture;

/**
 * 引用 Forge 子端的 DataProvider
 * <p></p>
 * 为适应不同平台的现有实现，将 DataProvider 拆分成两部分
 * <ul>
 *     <li>{@link NeoForgeDataProviderBase}: 负责将 Forge 的 DataProvider 转发到 common 端的 DataGenerator</li>
 *     <li>{@link ProviderDelegateBase}: 代理平台相关的 provider 实现，并负责收集数据</li>
 * </ul>
 */
public abstract class NeoForgeDataProviderBase<T extends ProviderDelegateBase<T>> implements ISinoDataProvider {

    private final T delegate;

    public NeoForgeDataProviderBase(T delegate) {
        this.delegate = delegate;
        delegate.setDelegateProvider(this);
    }

    /**
     * 添加元素，由具体的 {@link DataProvider} 负责调用
     *
     * @param delegate 代理对象
     */
    public abstract void generateData(T delegate);

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return delegate.getForgeProvider().run(output);
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
