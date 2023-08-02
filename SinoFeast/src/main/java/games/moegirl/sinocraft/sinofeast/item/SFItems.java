package games.moegirl.sinocraft.sinofeast.item;

import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFeast.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);

        TabsRegistry.items(SinoSeriesTabs.AGRICULTURE)
                .addItem(TEA_SEED)
                .addItem(TEA_BUDS)
                .addItem(TEA_FRESH_LEAVES)
                .addItem(CRUSHED_TEA_FRESH_LEAVES);

        TabsRegistry.items(SinoSeriesTabs.TOOLS)
                .addItem(KITCHEN_KNIFE)
                .addItem(SPATULA)
                .addItem(SOUP_LADLE)
                .addItem(CHOPSTICKS)
                .addItem(SPOON)
                .addItem(PORCELAIN_BOWL)
                .addItem(PORCELAIN_PLATE);

        TabsRegistry.items(SinoSeriesTabs.MISC)
                .addItem(SALT);
    }

    public static final RegistryObject<Item> TEA_SEED = ITEMS.register("tea_seed", TeaSeedItem::new);
    public static final RegistryObject<Item> TEA_BUDS = ITEMS.register("tea_buds", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEA_FRESH_LEAVES = ITEMS.register("tea_fresh_leaves", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRUSHED_TEA_FRESH_LEAVES = ITEMS.register("crushed_tea_fresh_leaves", () -> new Item(new Item.Properties()));

    // Placeholder below.
    public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> KITCHEN_KNIFE = ITEMS.register("kitchen_knife", () -> new Item(new Item.Properties().durability(256)));
    public static final RegistryObject<Item> SPATULA = ITEMS.register("spatula", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUP_LADLE = ITEMS.register("soup_ladle", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHOPSTICKS = ITEMS.register("chopsticks", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPOON = ITEMS.register("spoon", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PORCELAIN_BOWL = ITEMS.register("porcelain_bowl", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PORCELAIN_PLATE = ITEMS.register("porcelain_plate", () -> new Item(new Item.Properties()));
}
