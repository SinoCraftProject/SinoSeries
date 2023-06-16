package games.moegirl.sinocraft.sinocalligraphy.item;

import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import net.minecraft.world.item.Item;

public class InkItem extends Item {
    private InkType type;

    public InkItem(InkType type) {
        super(new Properties()
                .setNoRepair()
                .stacksTo(32));

        this.type = type;
    }

    public InkType getType() {
        return type;
    }
}
