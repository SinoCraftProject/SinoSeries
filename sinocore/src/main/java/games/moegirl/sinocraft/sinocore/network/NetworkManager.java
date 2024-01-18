package games.moegirl.sinocraft.sinocore.network;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于获取 {@link INetworkChannel} 的工具类
 */
public class NetworkManager {

    private static final Map<ResourceLocation, INetworkChannel> CHANNEL_MAP = new HashMap<>();

    public static INetworkChannel getOrCreateChannel(String modId) {
        return getOrCreateChannel(new ResourceLocation(modId, "default_network"));
    }

    public static INetworkChannel getOrCreateChannel(ResourceLocation networkId) {
        return CHANNEL_MAP.computeIfAbsent(networkId, NetworkManager::_create);
    }

    @ExpectPlatform
    public static INetworkChannel _create(ResourceLocation name) {
        throw new AssertionError();
    }
}
