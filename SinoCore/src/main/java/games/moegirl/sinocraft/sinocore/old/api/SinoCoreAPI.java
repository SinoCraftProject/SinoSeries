package games.moegirl.sinocraft.sinocore.old.api;

import games.moegirl.sinocraft.sinocore.api.crafting.CraftingApi;
import games.moegirl.sinocraft.sinocore.api.network.NetworkApi;
import net.minecraftforge.fml.ModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * SinoCore Public API
 */
@SuppressWarnings("unused")
public class SinoCoreAPI {
    public static final String MODID = "sinocore";
    public static final Logger LOGGER = LoggerFactory.getLogger("SinoCore");
    public static final boolean DEBUG = "true".equalsIgnoreCase(System.getProperty("forge.enableGameTest", "false"));

    private static String scId;
    private static CraftingApi crafting;
    private static NetworkApi network;
    private static boolean isInitialized = false;

    /**
     * Mod id of SinoCore
     * @return SinoCore modid
     */
    public static String getId() {
        return scId;
    }

    public static String getDefaultId() {
        return MODID;
    }

    /**
     * Crafting API,
     *
     * @return Crafting API
     */
    public static CraftingApi getCrafting() {
        return crafting;
    }

    /**
     * Network API
     *
     * @return Network API
     */
    public static NetworkApi getNetwork() {
        return network;
    }

    public static void _loadCoreApi(Consumer<ApiLoader> consumer) {
        if (!isInitialized && "sinocore".equals(ModLoadingContext.get().getActiveNamespace())) {
            consumer.accept(new ApiLoaderImpl());
            isInitialized = true;
        } else {
            throw new RuntimeException("DON'T reset SinoCore API!!!");
        }
    }

    private static class ApiLoaderImpl implements ApiLoader {
        @Override
        public void loadAll(String id, CraftingApi craftingApi, NetworkApi networkApi) {
            scId = id;
            crafting = craftingApi;
            network = networkApi;
        }
    }
}
