package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.item.TabBlockItemBase;
import games.moegirl.sinocraft.sinocore.item.TabItemBase;
import games.moegirl.sinocraft.sinocore.item.TabNamedBlockItemBase;
import games.moegirl.sinocraft.sinocore.item.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.plant.PlantBlock;
import games.moegirl.sinocraft.sinofoundation.block.plant.PlantType;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFDBlockItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFoundation.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        TabsRegistry.get(SinoSeriesTabs.FUNCTIONAL_BLOCKS).add(ITEMS);
    }

    public static final RegistryObject<BlockItem> STOVE = ITEMS.register("stove", () -> new BlockItem(SFDBlocks.STOVE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WOOD_DESK = ITEMS.register("wood_desk", () -> new BlockItem(SFDBlocks.WOOD_DESK.get(), new Item.Properties()));

    public static RegistryObject<BlockItem> WHITE_RADISH = ITEMS.register("white_radish", () -> new TabBlockItemBase(SFDBlocks.WHITE_RADISH_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build()), SinoSeriesTabs.AGRICULTURE));
    public static RegistryObject<BlockItem> SUMMER_RADISH = ITEMS.register("summer_radish", () -> new TabBlockItemBase(SFDBlocks.SUMMER_RADISH_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build()), SinoSeriesTabs.AGRICULTURE));
    public static RegistryObject<BlockItem> GREEN_RADISH = ITEMS.register("green_radish", () -> new TabBlockItemBase(SFDBlocks.GREEN_RADISH_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build()), SinoSeriesTabs.AGRICULTURE));

    public static RegistryObject<BlockItem> CHILI_PEPPER_SEED = ITEMS.register("chili_pepper_seed", () -> new TabNamedBlockItemBase(SFDBlocks.CHILI_PEPPER_PLANT.get(), new Item.Properties(), SinoSeriesTabs.AGRICULTURE));
    public static RegistryObject<BlockItem> GREEN_PEPPER_SEED = ITEMS.register("green_pepper_seed", () -> new TabNamedBlockItemBase(SFDBlocks.GREEN_PEPPER_PLANT.get(), new Item.Properties(), SinoSeriesTabs.AGRICULTURE));
    public static RegistryObject<BlockItem> EGGPLANT_SEED = ITEMS.register("eggplant_seed", () -> new TabNamedBlockItemBase(SFDBlocks.EGGPLANT_PLANT.get(), new Item.Properties(), SinoSeriesTabs.AGRICULTURE));
    public static RegistryObject<BlockItem> CABBAGE_SEED = ITEMS.register("cabbage_seed", () -> new TabNamedBlockItemBase(SFDBlocks.CABBAGE_PLANT.get(), new Item.Properties(), SinoSeriesTabs.AGRICULTURE));
    public static RegistryObject<BlockItem> MILLET_SEED = ITEMS.register("millet_seed", () -> new TabNamedBlockItemBase(SFDBlocks.MILLET_PLANT.get(), new Item.Properties(), SinoSeriesTabs.AGRICULTURE));
    public static RegistryObject<BlockItem> SOYBEAN = ITEMS.register("soybean", () -> new TabBlockItemBase(SFDBlocks.SOYBEAN_PLANT.get(), new Item.Properties(), SinoSeriesTabs.AGRICULTURE));
    public static RegistryObject<BlockItem> GARLIC = ITEMS.register("garlic", () -> new TabBlockItemBase(SFDBlocks.GARLIC_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build()), SinoSeriesTabs.AGRICULTURE));

    public static RegistryObject<BlockItem> MARBLE = ITEMS.register("marble", () -> new TabBlockItemBase(SFDBlocks.MARBLE.get(), new Item.Properties(), SinoSeriesTabs.BUILDING_BLOCKS));
    public static RegistryObject<BlockItem> MARBLE_WALL = ITEMS.register("marble_wall", () -> new TabBlockItemBase(SFDBlocks.MARBLE_WALL.get(), new Item.Properties(), SinoSeriesTabs.BUILDING_BLOCKS));

}
