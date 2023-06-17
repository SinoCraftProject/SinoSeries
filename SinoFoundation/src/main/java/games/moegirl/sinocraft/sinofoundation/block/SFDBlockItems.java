package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFDBlockItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFoundation.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);

        TabsRegistry.items(SinoSeriesTabs.FUNCTIONAL_BLOCKS)
                .addItem(STOVE)
                .addItem(WOOD_DESK);

        TabsRegistry.items(SinoSeriesTabs.AGRICULTURE)
                .addItem(WHITE_RADISH)
                .addItem(SUMMER_RADISH)
                .addItem(GREEN_RADISH)
                .addItem(CHILI_PEPPER_SEED)
                .addItem(GREEN_PEPPER_SEED)
                .addItem(EGGPLANT_SEED)
                .addItem(CABBAGE_SEED)
                .addItem(MILLET_SEED)
                .addItem(SOYBEAN)
                .addItem(GARLIC);

        TabsRegistry.items(SinoSeriesTabs.BUILDING_BLOCKS)
                .addItem(MARBLE)
                .addItem(MARBLE_WALL)
                .addItem(JADE_ORE)
                .addItem(NITER_ORE)
                .addItem(SULPHUR_ORE);
    }

    public static final RegistryObject<BlockItem> STOVE = ITEMS.register("stove", () -> new BlockItem(SFDBlocks.STOVE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WOOD_DESK = ITEMS.register("wood_desk", () -> new BlockItem(SFDBlocks.WOOD_DESK.get(), new Item.Properties()));

    public static final RegistryObject<BlockItem> WHITE_RADISH = ITEMS.register("white_radish", () -> new BlockItem(SFDBlocks.WHITE_RADISH_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<BlockItem> SUMMER_RADISH = ITEMS.register("summer_radish", () -> new BlockItem(SFDBlocks.SUMMER_RADISH_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<BlockItem> GREEN_RADISH = ITEMS.register("green_radish", () -> new BlockItem(SFDBlocks.GREEN_RADISH_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));

    public static final RegistryObject<BlockItem> CHILI_PEPPER_SEED = ITEMS.register("chili_pepper_seed", () -> new ItemNameBlockItem(SFDBlocks.CHILI_PEPPER_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> GREEN_PEPPER_SEED = ITEMS.register("green_pepper_seed", () -> new ItemNameBlockItem(SFDBlocks.GREEN_PEPPER_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> EGGPLANT_SEED = ITEMS.register("eggplant_seed", () -> new ItemNameBlockItem(SFDBlocks.EGGPLANT_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> CABBAGE_SEED = ITEMS.register("cabbage_seed", () -> new ItemNameBlockItem(SFDBlocks.CABBAGE_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> MILLET_SEED = ITEMS.register("millet_seed", () -> new ItemNameBlockItem(SFDBlocks.MILLET_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SOYBEAN = ITEMS.register("soybean", () -> new BlockItem(SFDBlocks.SOYBEAN_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> GARLIC = ITEMS.register("garlic", () -> new BlockItem(SFDBlocks.GARLIC_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));

    public static final RegistryObject<BlockItem> MARBLE = ITEMS.register("marble", () -> new BlockItem(SFDBlocks.MARBLE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> MARBLE_WALL = ITEMS.register("marble_wall", () -> new BlockItem(SFDBlocks.MARBLE_WALL.get(), new Item.Properties()));

    public static final RegistryObject<BlockItem> JADE_ORE = ITEMS.register("jade_ore", () -> new BlockItem(SFDBlocks.JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> NITER_ORE = ITEMS.register("niter_ore", () -> new BlockItem(SFDBlocks.NITER_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SULPHUR_ORE = ITEMS.register("sulphur_ore", () -> new BlockItem(SFDBlocks.SULPHUR_ORE.get(), new Item.Properties()));
}
