package games.moegirl.sinocraft.sinocalligraphy.item;

import games.moegirl.sinocraft.sinocalligraphy.drawing.PaperType;
import net.minecraft.world.item.Item;

public class XuanPaperItem extends Item {
    private PaperType type;

    public XuanPaperItem(PaperType typeIn) {
        super(new Properties()
                .stacksTo(64)
                .setNoRepair());

        type = typeIn;
    }

    public PaperType getType() {
        return type;
    }
}
