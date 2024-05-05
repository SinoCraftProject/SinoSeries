package games.moegirl.sinocraft.sinodeco;

import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.item.SDItems;

public class SinoDeco {
    public static final String MODID = "sinodeco";

    public SinoDeco() {

    }

    public void init() {
        SDItems.register();
        SDBlocks.register();
    }
}
