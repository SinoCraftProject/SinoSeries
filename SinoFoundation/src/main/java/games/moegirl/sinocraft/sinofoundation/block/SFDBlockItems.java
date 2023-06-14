package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
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

import java.util.function.Supplier;

public class SFDBlockItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFoundation.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static final RegistryObject<BlockItem> STOVE = register("stove", () -> new BlockItem(SFDBlocks.STOVE.get(), new Item.Properties()), SinoSeriesTabs.Generators.FUNCTIONAL);
    public static final RegistryObject<BlockItem> WOOD_DESK = register("wood_desk", () -> new BlockItem(SFDBlocks.WOOD_DESK.get(), new Item.Properties()), SinoSeriesTabs.Generators.FUNCTIONAL);

    public static RegistryObject<BlockItem> WHITE_RADISH = register("white_radish", () -> new BlockItem(SFDBlocks.WHITE_RADISH_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())), SinoSeriesTabs.Generators.AGRICULTURE);
    public static RegistryObject<BlockItem> SUMMER_RADISH = register("summer_radish", () -> new BlockItem(SFDBlocks.SUMMER_RADISH_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())), SinoSeriesTabs.Generators.AGRICULTURE);
    public static RegistryObject<BlockItem> GREEN_RADISH = register("green_radish", () -> new BlockItem(SFDBlocks.GREEN_RADISH_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())), SinoSeriesTabs.Generators.AGRICULTURE);

    public static RegistryObject<BlockItem> CHILI_PEPPER_SEED = register("chili_pepper_seed", () -> new ItemNameBlockItem(SFDBlocks.CHILI_PEPPER_PLANT.get(), new Item.Properties()), SinoSeriesTabs.Generators.AGRICULTURE);
    public static RegistryObject<BlockItem> GREEN_PEPPER_SEED = register("green_pepper_seed", () -> new ItemNameBlockItem(SFDBlocks.GREEN_PEPPER_PLANT.get(), new Item.Properties()), SinoSeriesTabs.Generators.AGRICULTURE);
    public static RegistryObject<BlockItem> EGGPLANT_SEED = register("eggplant_seed", () -> new ItemNameBlockItem(SFDBlocks.EGGPLANT_PLANT.get(), new Item.Properties()), SinoSeriesTabs.Generators.AGRICULTURE);
    public static RegistryObject<BlockItem> CABBAGE_SEED = register("cabbage_seed", () -> new ItemNameBlockItem(SFDBlocks.CABBAGE_PLANT.get(), new Item.Properties()), SinoSeriesTabs.Generators.AGRICULTURE);
    public static RegistryObject<BlockItem> MILLET_SEED = register("millet_seed", () -> new ItemNameBlockItem(SFDBlocks.MILLET_PLANT.get(), new Item.Properties()), SinoSeriesTabs.Generators.AGRICULTURE);
    public static RegistryObject<BlockItem> SOYBEAN = register("soybean", () -> new BlockItem(SFDBlocks.SOYBEAN_PLANT.get(), new Item.Properties()), SinoSeriesTabs.Generators.AGRICULTURE);
    public static RegistryObject<BlockItem> GARLIC = register("garlic", () -> new BlockItem(SFDBlocks.GARLIC_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())), SinoSeriesTabs.Generators.AGRICULTURE);

    public static RegistryObject<BlockItem> MARBLE = register("marble", () -> new BlockItem(SFDBlocks.MARBLE.get(), new Item.Properties()), SinoSeriesTabs.Generators.BUILDING);
    public static RegistryObject<BlockItem> MARBLE_WALL = register("marble_wall", () -> new BlockItem(SFDBlocks.MARBLE_WALL.get(), new Item.Properties()), SinoSeriesTabs.Generators.BUILDING);

    public static final RegistryObject<BlockItem> JADE_ORE = register("jade_ore", () -> new BlockItem(SFDBlocks.JADE_ORE.get(), new Item.Properties()), SinoSeriesTabs.Generators.BUILDING);
    public static final RegistryObject<BlockItem> NITER_ORE = register("niter_ore", () -> new BlockItem(SFDBlocks.NITER_ORE.get(), new Item.Properties()), SinoSeriesTabs.Generators.BUILDING);
    public static final RegistryObject<BlockItem> SULPHUR_ORE = register("sulphur_ore", () -> new BlockItem(SFDBlocks.SULPHUR_ORE.get(), new Item.Properties()), SinoSeriesTabs.Generators.BUILDING);

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item, TabItemGenerator tab) {
        RegistryObject<T> object = ITEMS.register(name, item);
        tab.addItem(object);
        return object;
    }
}
