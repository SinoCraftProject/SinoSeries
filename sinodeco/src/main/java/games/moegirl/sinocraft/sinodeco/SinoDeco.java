package games.moegirl.sinocraft.sinodeco;

import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.block.item.SDBlockItems;
import games.moegirl.sinocraft.sinodeco.data.gen.SDData;
import games.moegirl.sinocraft.sinodeco.item.SDItems;

public class SinoDeco {
    public static final String MODID = "sinodeco";

    public SinoDeco() {
        SDBlocks.register();
        SDBlockItems.register();
        SDItems.register();

        SDData.register();
    }

    public void init() {
    }
}
