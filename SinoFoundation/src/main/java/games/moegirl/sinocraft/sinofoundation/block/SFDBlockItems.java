package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.item.BaseChestItem;
import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.entity.SFDBlockEntities;
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
                .addItem(WOOD_DESK)
                .addItem(WOOD_CHAIRS)
                .addItem(COTINUS_CHEST)
                .addItem(COTINUS_TRAPPED_CHEST)
                .addItem(JUJUBE_CHEST)
                .addItem(JUJUBE_TRAPPED_CHEST)
                .addItem(SOPHORA_CHEST)
                .addItem(SOPHORA_TRAPPED_CHEST);

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
                .addItem(MARBLE_BLOCK)
                .addItem(SMOOTH_MARBLE)
                .addItem(MARBLE_PILLAR)
                .addItem(CHISELED_MARBLE_BLOCK)
                .addItem(MARBLE_SLAB)
                .addItem(MARBLE_STAIRS)
                .addItem(MARBLE_WALLS)
                .addItem(SMOOTH_MARBLE_SLAB)
                .addItem(SMOOTH_MARBLE_STAIRS)
                .addItem(BLACK_JADE_ORE)
                .addItem(DEEPSLATE_BLACK_JADE_ORE)
                .addItem(GREEN_JADE_ORE)
                .addItem(DEEPSLATE_GREEN_JADE_ORE)
                .addItem(RED_JADE_ORE)
                .addItem(DEEPSLATE_RED_JADE_ORE)
                .addItem(WHITE_JADE_ORE)
                .addItem(DEEPSLATE_WHITE_JADE_ORE)
                .addItem(YELLOW_JADE_ORE)
                .addItem(DEEPSLATE_YELLOW_JADE_ORE)
                .addItem(NITER_ORE)
                .addItem(SULPHUR_ORE)
                .addItem(NETHER_SULPHUR_ORE)
                .addItem(DEEP_SLATE_SULPHUR_ORE);
    }

    public static final RegistryObject<BlockItem> STOVE = ITEMS.register("stove", () -> new BlockItem(SFDBlocks.STOVE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WOOD_DESK = ITEMS.register("wood_desk", () -> new BlockItem(SFDBlocks.WOOD_DESK.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WOOD_CHAIRS = ITEMS.register("wood_chairs", () -> new BlockItem(SFDBlocks.WOOD_CHAIRS.get(), new Item.Properties()));

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

    public static final RegistryObject<BlockItem> MARBLE_BLOCK = ITEMS.register("marble_block", () -> new BlockItem(SFDBlocks.MARBLE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SMOOTH_MARBLE = ITEMS.register("smooth_marble", () -> new BlockItem(SFDBlocks.SMOOTH_MARBLE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> MARBLE_PILLAR = ITEMS.register("marble_pillar", () -> new BlockItem(SFDBlocks.MARBLE_PILLAR.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> CHISELED_MARBLE_BLOCK = ITEMS.register("chiseled_marble_block", () -> new BlockItem(SFDBlocks.CHISELED_MARBLE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> MARBLE_SLAB = ITEMS.register("marble_slab", () -> new BlockItem(SFDBlocks.MARBLE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> MARBLE_STAIRS = ITEMS.register("marble_stairs", () -> new BlockItem(SFDBlocks.MARBLE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> MARBLE_WALLS = ITEMS.register("marble_walls", () -> new BlockItem(SFDBlocks.MARBLE_WALLS.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SMOOTH_MARBLE_SLAB = ITEMS.register("smooth_marble_slab", () -> new BlockItem(SFDBlocks.SMOOTH_MARBLE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SMOOTH_MARBLE_STAIRS = ITEMS.register("smooth_marble_stairs", () -> new BlockItem(SFDBlocks.SMOOTH_MARBLE_STAIRS.get(), new Item.Properties()));

    public static final RegistryObject<BlockItem> BLACK_JADE_ORE = ITEMS.register("black_jade_ore", () -> new BlockItem(SFDBlocks.BLACK_JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DEEPSLATE_BLACK_JADE_ORE = ITEMS.register("deepslate_black_jade_ore", () -> new BlockItem(SFDBlocks.DEEPSLATE_BLACK_JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> GREEN_JADE_ORE = ITEMS.register("green_jade_ore", () -> new BlockItem(SFDBlocks.GREEN_JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DEEPSLATE_GREEN_JADE_ORE = ITEMS.register("deepslate_green_jade_ore", () -> new BlockItem(SFDBlocks.DEEPSLATE_GREEN_JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> RED_JADE_ORE = ITEMS.register("red_jade_ore", () -> new BlockItem(SFDBlocks.RED_JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DEEPSLATE_RED_JADE_ORE = ITEMS.register("deepslate_red_jade_ore", () -> new BlockItem(SFDBlocks.DEEPSLATE_RED_JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WHITE_JADE_ORE = ITEMS.register("white_jade_ore", () -> new BlockItem(SFDBlocks.WHITE_JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DEEPSLATE_WHITE_JADE_ORE = ITEMS.register("deepslate_white_jade_ore", () -> new BlockItem(SFDBlocks.DEEPSLATE_WHITE_JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> YELLOW_JADE_ORE = ITEMS.register("yellow_jade_ore", () -> new BlockItem(SFDBlocks.YELLOW_JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DEEPSLATE_YELLOW_JADE_ORE = ITEMS.register("deepslate_yellow_jade_ore", () -> new BlockItem(SFDBlocks.DEEPSLATE_YELLOW_JADE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> NITER_ORE = ITEMS.register("niter_ore", () -> new BlockItem(SFDBlocks.NITER_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SULPHUR_ORE = ITEMS.register("sulphur_ore", () -> new BlockItem(SFDBlocks.SULPHUR_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> NETHER_SULPHUR_ORE = ITEMS.register("nether_sulphur_ore", () -> new BlockItem(SFDBlocks.NETHER_SULPHUR_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DEEP_SLATE_SULPHUR_ORE = ITEMS.register("deepslate_sulphur_ore", () -> new BlockItem(SFDBlocks.DEEP_SLATE_SULPHUR_ORE.get(), new Item.Properties()));

    public static final RegistryObject<BaseChestItem> COTINUS_CHEST = ITEMS.register("cotinus_chest", () -> BaseChestItem.create(SFDBlocks.COTINUS_CHEST, SFDBlockEntities.COTINUS_CHEST));
    public static final RegistryObject<BaseChestItem> COTINUS_TRAPPED_CHEST = ITEMS.register("cotinus_trapped_chest", () -> BaseChestItem.create(SFDBlocks.COTINUS_TRAPPED_CHEST, SFDBlockEntities.COTINUS_TRAPPED_CHEST));
    public static final RegistryObject<BaseChestItem> JUJUBE_CHEST = ITEMS.register("jujube_chest", () -> BaseChestItem.create(SFDBlocks.JUJUBE_CHEST, SFDBlockEntities.JUJUBE_CHEST));
    public static final RegistryObject<BaseChestItem> JUJUBE_TRAPPED_CHEST = ITEMS.register("jujube_trapped_chest", () -> BaseChestItem.create(SFDBlocks.JUJUBE_TRAPPED_CHEST, SFDBlockEntities.JUJUBE_TRAPPED_CHEST));
    public static final RegistryObject<BaseChestItem> SOPHORA_CHEST = ITEMS.register("sophora_chest", () -> BaseChestItem.create(SFDBlocks.SOPHORA_CHEST, SFDBlockEntities.SOPHORA_CHEST));
    public static final RegistryObject<BaseChestItem> SOPHORA_TRAPPED_CHEST = ITEMS.register("sophora_trapped_chest", () -> BaseChestItem.create(SFDBlocks.SOPHORA_TRAPPED_CHEST, SFDBlockEntities.SOPHORA_TRAPPED_CHEST));
}
