package games.moegirl.sinocraft.sinofoundation.item;

import games.moegirl.sinocraft.sinocore.event.BlockStrippingEvent;
import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFDItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFoundation.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);

        TabsRegistry.items(SinoSeriesTabs.TOOLS)
                .addItem(IRON_KNIFE)
                .addItem(GOLD_KNIFE)
                .addItem(DIAMOND_KNIFE);

        TabsRegistry.items(SinoSeriesTabs.WEAPONS)
                .addItem(IRON_KNIFE)
                .addItem(GOLD_KNIFE)
                .addItem(DIAMOND_KNIFE);

        TabsRegistry.items(SinoSeriesTabs.AGRICULTURE)
                .addItem(MILLET)
                .addItem(CHILI_PEPPER)
                .addItem(GREEN_PEPPER)
                .addItem(CABBAGE)
                .addItem(EGGPLANT);

        TabsRegistry.items(SinoSeriesTabs.MATERIALS)
                .addItem(JADE)
                .addItem(NITER)
                .addItem(SULPHUR);
    }

    public static final RegistryObject<Item> IRON_KNIFE = ITEMS.register("iron_knife", () -> new KnifeItem(Tiers.IRON));
    public static final RegistryObject<Item> GOLD_KNIFE = ITEMS.register("gold_knife", () -> new KnifeItem(Tiers.GOLD));
    public static final RegistryObject<Item> DIAMOND_KNIFE = ITEMS.register("diamond_knife", () -> new KnifeItem(Tiers.DIAMOND));

    public static final RegistryObject<Item> ASHES = ITEMS.register("ashes", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TREE_BARK = ITEMS.register("tree_bark", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MILLET = ITEMS.register("millet", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHILI_PEPPER = ITEMS.register("chili_pepper", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> GREEN_PEPPER = ITEMS.register("green_pepper", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> CABBAGE = ITEMS.register("cabbage", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> EGGPLANT = ITEMS.register("eggplant", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));

    public static final RegistryObject<Item> JADE = ITEMS.register("jade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NITER = ITEMS.register("niter", () -> new Niter(new Item.Properties()));
    public static final RegistryObject<Item> SULPHUR = ITEMS.register("sulfur", () -> new Sulfur(new Item.Properties()));

    static {
        BlockStrippingEvent.registerTool(IRON_KNIFE);
        BlockStrippingEvent.registerTool(GOLD_KNIFE);
        BlockStrippingEvent.registerTool(DIAMOND_KNIFE);
    }
}
