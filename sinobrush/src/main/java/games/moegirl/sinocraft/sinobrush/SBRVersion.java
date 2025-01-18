package games.moegirl.sinocraft.sinobrush;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Properties;

public class SBRVersion {
    public static final String VERSION;
    public static final ZonedDateTime BUILD_TIME;

    static {
        var properties = new Properties();
        String version;
        ZonedDateTime buildTime;
        try {
            properties.load(SBRVersion.class.getResourceAsStream("/build_info.properties"));
            version = properties.getProperty("full_version");
            buildTime = ZonedDateTime.parse(properties.getProperty("build_time"));
        } catch (Exception ignored) {
            version = "Unknown";
            buildTime = ZonedDateTime.of(-2300, 1, 1, 0, 0, 0, 0, ZoneId.of("Asia/Shanghai"));
        }
        BUILD_TIME = buildTime;
        VERSION = version;
    }
}
