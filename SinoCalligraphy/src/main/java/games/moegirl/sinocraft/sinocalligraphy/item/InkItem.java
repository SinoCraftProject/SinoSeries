package games.moegirl.sinocraft.sinocalligraphy.item;

import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.PaperType;
import games.moegirl.sinocraft.sinocore.item.tab.ITabItem;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;

public class InkItem extends Item implements ITabItem {
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

    @Override
    public List<ResourceLocation> getTabs() {
        return List.of(SinoSeriesTabs.MATERIALS);
    }
}
