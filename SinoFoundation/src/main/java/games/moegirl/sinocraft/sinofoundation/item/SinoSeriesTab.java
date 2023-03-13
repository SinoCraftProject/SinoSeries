package games.moegirl.sinocraft.sinofoundation.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class SinoSeriesTab extends CreativeModeTab {

    public static final SinoSeriesTab BUILDING_BLOCKS = new SinoSeriesTab(CreativeModeTab.builder(Row.BOTTOM, 1));
    public static final SinoSeriesTab FUNCTIONAL_BLOCKS = new SinoSeriesTab(CreativeModeTab.builder(Row.BOTTOM, 1));
    public static final SinoSeriesTab AGRICULTURE = new SinoSeriesTab(CreativeModeTab.builder(Row.BOTTOM, 1));
    public static final SinoSeriesTab TOOLS = new SinoSeriesTab(CreativeModeTab.builder(Row.BOTTOM, 1));
    public static final SinoSeriesTab WEAPONS = new SinoSeriesTab(CreativeModeTab.builder(Row.BOTTOM, 1));
    public static final SinoSeriesTab MISC = new SinoSeriesTab(CreativeModeTab.builder(Row.BOTTOM, 1));

//    public static final SinoSeriesTab BUILDING_BLOCKS = new SinoSeriesTab("building_blocks", null);
//    public static final SinoSeriesTab FUNCTIONAL_BLOCKS = new SinoSeriesTab("functional_blocks", null);
//    public static final SinoSeriesTab AGRICULTURE = new SinoSeriesTab("agriculture", null);
//    public static final SinoSeriesTab TOOLS = new SinoSeriesTab("tools", null);
//    public static final SinoSeriesTab WEAPONS = new SinoSeriesTab("weapons", null);
//    public static final SinoSeriesTab MISC = new SinoSeriesTab("misc", null);

    protected String name;
    protected Supplier<? extends ItemLike> icon;

    protected SinoSeriesTab(CreativeModeTab.Builder builder) {
        super(builder);
    }

//    public SinoSeriesTab(String label, Supplier<? extends ItemLike> iconItem) {
//        super("sinoseries_" + label);
//        name = "sinoseries_" + label;
//        icon = iconItem;
//    }

    public String getLabel() {
        return name;
    }

    @Override
    public ItemStack getIconItem() {
        if (icon == null) {
            return ItemStack.EMPTY;
        }

        return new ItemStack(icon.get());
    }
}
