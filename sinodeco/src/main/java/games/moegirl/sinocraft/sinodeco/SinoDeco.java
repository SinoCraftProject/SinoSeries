package games.moegirl.sinocraft.sinodeco;

import com.mojang.logging.LogUtils;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.block.item.SDBlockItems;
import games.moegirl.sinocraft.sinodeco.data.gen.SDData;
import games.moegirl.sinocraft.sinodeco.entity.SDEntities;
import games.moegirl.sinocraft.sinodeco.item.SDItems;
import org.slf4j.Logger;

public class SinoDeco {
    public static final String MODID = "sinodeco";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        LOGGER.info("SinoDeco ver {}, build at {}", SDVersion.VERSION, SDVersion.BUILD_TIME.toString());

        SDBlocks.register();
        SDBlockItems.register();
        SDItems.register();
        SDEntities.register();

        SDData.register();
    }

    public static void initClient() {

    }
}
