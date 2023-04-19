package games.moegirl.sinocraft.sinocalligraphy.item;

import games.moegirl.sinocraft.sinocalligraphy.drawing.PaperType;
import games.moegirl.sinocraft.sinocore.item.tab.ITabItem;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;

public class XuanPaperItem extends Item implements ITabItem {
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

    @Override
    public List<ResourceLocation> getTabs() {
        return List.of(SinoSeriesTabs.MISC);
    }
}
