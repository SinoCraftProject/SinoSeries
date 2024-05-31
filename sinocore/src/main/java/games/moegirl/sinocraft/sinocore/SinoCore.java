package games.moegirl.sinocraft.sinocore;

import com.mojang.logging.LogUtils;
import games.moegirl.sinocraft.sinocore.advancement.criterion.SinoCriteriaTriggers;
import org.slf4j.Logger;

public class SinoCore {
    public static final String MODID = "sinocore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public void init() {
    }

    /**
     * Something must be run in the Main Thread, we called it as setup.
     */
    public void setup() {
        SinoCriteriaTriggers.register();    // qyl27: Register CriteriaTriggers directly, not thread-safe.
    }
}
