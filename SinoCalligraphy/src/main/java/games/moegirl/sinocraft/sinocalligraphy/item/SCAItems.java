package games.moegirl.sinocraft.sinocalligraphy.item;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.PaperType;
import games.moegirl.sinocraft.sinocalligraphy.fluid.SCAFluids;
import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCAItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoCalligraphy.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);

        TabsRegistry.items(SinoSeriesTabs.MATERIALS)
                .addItem(INK)
                .addItem(GOLDEN_INK)
                .addItem(EMPTY_XUAN_PAPER)
                .addItem(EMPTY_XUAN_PAPER_RED)
                .addItem(FILLED_XUAN_PAPER)
                .addItem(GREEN_SANDALWOOD_BARK);

        TabsRegistry.items(SinoSeriesTabs.TOOLS)
                .addItem(BRUSH)
                .addItem(FAN)
                .addItem(FAN_FOLDED);

        TabsRegistry.items(SinoSeriesTabs.WEAPONS)
                .addItem(FAN)
                .addItem(FAN_FOLDED);
    }

    public static final RegistryObject<Item> BRUSH = ITEMS.register("chinese_brush", BrushItem::new);
    public static final RegistryObject<Item> FAN = ITEMS.register("fan", () -> new FanItem(false));
    public static final RegistryObject<Item> FAN_FOLDED = ITEMS.register("fan_folded", () -> new FanItem(true));

    public static final RegistryObject<Item> INK = ITEMS.register("ink", () -> new InkItem(InkType.BLACK));
    public static final RegistryObject<Item> GOLDEN_INK = ITEMS.register("golden_ink", () -> new InkItem(InkType.GOLDEN));

    public static final RegistryObject<Item> EMPTY_XUAN_PAPER = ITEMS.register("empty_xuan_paper", () -> new XuanPaperItem(PaperType.WHITE));
    public static final RegistryObject<Item> EMPTY_XUAN_PAPER_RED = ITEMS.register("empty_xuan_paper_red", () -> new XuanPaperItem(PaperType.RED));
    public static final RegistryObject<Item> EMPTY_XUAN_PAPER_BLACK = ITEMS.register("empty_xuan_paper_black", () -> new XuanPaperItem(PaperType.BLACK));
    public static final RegistryObject<Item> FILLED_XUAN_PAPER = ITEMS.register("filled_xuan_paper", FilledXuanPaperItem::new);

    public static final RegistryObject<Item> GREEN_SANDALWOOD_BARK = ITEMS.register("green_sandalwood_bark", () -> new Item(new Item.Properties()));

    public static final RegistryObject<BucketItem> WOOD_PULP_BUCKET = ITEMS.register("wood_pulp_bucket", () -> new BucketItem(SCAFluids.WOOD_PULP, new Item.Properties().setNoRepair().stacksTo(1)));
}
