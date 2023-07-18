package games.moegirl.sinocraft.sinofoundation.item;

import games.moegirl.sinocraft.sinocore.event.BlockStrippingEvent;
import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinocore.utility.NameUtils;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

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
                .addItem(EGGPLANT)
                .addItem(JUJUBE)
                .addItem(WORMWOOD_LEAF)
                .addItem(RICE)
                .addItem(SEED_RICE)
                .addItem(LUCID_GANODERMAMA)
                .addItem(REHMANNIA)
                .addItem(SEED_REHMANNIA)
                .addItem(DRAGONLIVER_MELON)
                .addItem(SEED_DRAGONLIVER)
                .addItem(SESAME)
                .addItem(SEED_SESAME)
                .addItem(ZHU_CAO)
                .addItem(BRIGHT_STEM_GRASS)
                .addItem(SEED_WORMWOOD);

        TabsRegistry.items(SinoSeriesTabs.MATERIALS)
                .addItem(ASHES)
                .addItem(TREE_BARK)
                .addItem(BLACK_JADE)
                .addItem(GREEN_JADE)
                .addItem(RED_JADE)
                .addItem(WHITE_JADE)
                .addItem(YELLOW_JADE)
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

    public static final RegistryObject<Item> BLACK_JADE = ITEMS.register("black_jade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GREEN_JADE = ITEMS.register("green_jade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RED_JADE = ITEMS.register("red_jade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHITE_JADE = ITEMS.register("white_jade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_JADE = ITEMS.register("yellow_jade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NITER = ITEMS.register("niter", () -> new Niter(new Item.Properties()));
    public static final RegistryObject<Item> SULPHUR = ITEMS.register("sulfur", () -> new Sulfur(new Item.Properties()));

    public static final RegistryObject<Item> JUJUBE = ITEMS.register("jujube", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).build())));
    public static final RegistryObject<Item> WORMWOOD_LEAF = ITEMS.register("wormwood_leaf", () -> new Item(new Item.Properties()));
    public static final RegistryObject<ItemNameBlockItem> SEED_WORMWOOD = seed(SFDBlocks.WORMWOOD);
    public static final RegistryObject<Item> RICE = food("rice", 1, false);
    public static final RegistryObject<ItemNameBlockItem> SEED_RICE = item(SeedRice.class);
    public static final RegistryObject<BlockItem> LUCID_GANODERMAMA = block(SFDBlocks.LUCID_GANODERMA);
    public static final RegistryObject<Item> REHMANNIA = food("rehmannia", 2, false);
    public static final RegistryObject<ItemNameBlockItem> SEED_REHMANNIA = seed(SFDBlocks.REHMANNIA);
    public static final RegistryObject<Item> DRAGONLIVER_MELON = food("dragonliver_melon", 3, false);
    public static final RegistryObject<ItemNameBlockItem> SEED_DRAGONLIVER = seed(SFDBlocks.DRAGONLIVER_MELON);
    public static final RegistryObject<Item> SESAME = food("sesame", 1, true);
    public static final RegistryObject<ItemNameBlockItem> SEED_SESAME = seed(SFDBlocks.SESAME);
    public static final RegistryObject<Item> ZHU_CAO = simple(SFDBlocks.ZHU_CAO);
    public static final RegistryObject<Item> BRIGHT_STEM_GRASS = simple(SFDBlocks.BRIGHT_STEM_GRASS);

    public static final RegistryObject<Item> ICON_BLOCK_INTERACT = ITEMS.register("icon_block_interact", () -> new Item(new Item.Properties()));

    static {
        BlockStrippingEvent.registerTool(IRON_KNIFE);
        BlockStrippingEvent.registerTool(GOLD_KNIFE);
        BlockStrippingEvent.registerTool(DIAMOND_KNIFE);
    }

    public static <T extends Block> RegistryObject<Item> simple(RegistryObject<T> block) {
        return ITEMS.register(block.getId().getPath(), () -> new SimpleBlockItem(block));
    }

    public static <T extends Item> RegistryObject<T> item(Class<? extends T> aClass) {
        Function<Item.Properties, ? extends T> constructor = Functions.constructor(aClass, Item.Properties.class);
        return ITEMS.register(NameUtils.to_snake_name(aClass.getSimpleName()), () -> constructor.apply(new Item.Properties()));
    }

    public static RegistryObject<Item> food(String name, int nutrition, boolean fast) {
        if (fast) {
            return ITEMS.register(name, () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(nutrition).fast().build())));
        } else {
            return ITEMS.register(name, () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(nutrition).build())));
        }
    }

    public static <T extends Block> RegistryObject<BlockItem> block(RegistryObject<T> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static <T extends Block> RegistryObject<ItemNameBlockItem> namedBlock(String name, RegistryObject<T> block) {
        return ITEMS.register(name, () -> new ItemNameBlockItem(block.get(), new Item.Properties()));
    }

    public static <T extends Block> RegistryObject<ItemNameBlockItem> seed(RegistryObject<T> crop) {
        return namedBlock("seed_" + crop.getId().getPath(), crop);
    }
}
