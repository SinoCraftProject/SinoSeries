package games.moegirl.sinocraft.sinocore;

import com.mojang.logging.LogUtils;
import games.moegirl.sinocraft.sinocore.advancement.criterion.SCCriteriaTriggers;
import org.slf4j.Logger;

import java.time.ZonedDateTime;
import java.util.Properties;

public class SinoCore {
    public static final String MODID = "sinocore";

    public static final String VERSION;
    public static final ZonedDateTime BUILD_TIME;

    private static final Logger LOGGER = LogUtils.getLogger();

    static {
        var properties = new Properties();
        String version;
        ZonedDateTime buildTime;
        try {
            properties.load(SinoCore.class.getResourceAsStream("/build_info.properties"));
            version = properties.getProperty("full_version");
            buildTime = ZonedDateTime.parse(properties.getProperty("build_time"));
        } catch (Exception ignored) {
            version = "Unknown";
            buildTime = null;
        }
        BUILD_TIME = buildTime;
        VERSION = version;
    }

    public SinoCore() {
        LOGGER.info("Initializing SinoCore. Ver: {}, Build at: {}", VERSION, BUILD_TIME != null ? BUILD_TIME : "B.C. 3200");

        SCCriteriaTriggers.register();
    }

    public void init() {
    }

    /**
     * Something must be run in the Main Thread, we called it as setup.
     * In Forge-like, it should be invoked in FMLCommonSetupEvent.
     */
    public void setup() {
    }
}
