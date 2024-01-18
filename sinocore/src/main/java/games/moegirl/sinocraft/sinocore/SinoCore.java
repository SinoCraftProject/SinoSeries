package games.moegirl.sinocraft.sinocore;

import com.mojang.logging.LogUtils;
import games.moegirl.sinocraft.sinocore.test.network.TestNetwork;
import games.moegirl.sinocraft.sinocore.test.registry.TestRegistry;
import org.slf4j.Logger;

public class SinoCore {
    public static final String MODID = "sinocore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void registerAll() {
        TestRegistry.registerAll();
        TestNetwork.registerAll();
    }
}
