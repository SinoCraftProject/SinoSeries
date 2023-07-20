package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinocore.item.BaseChestItem;
import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinocore.utility.NameUtils;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.util.register.DeferredRegisters;
import games.moegirl.sinocraft.sinodivination.util.register.ItemRegister;
import games.moegirl.sinocraft.sinofoundation.item.SimpleBlockItem;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class SDItems {

    public static final ItemRegister REGISTRY = DeferredRegisters.item(SinoDivination.MODID);

    public static final RegistryObject<BlockItem> KETTLE_POT = block(SDBlocks.KETTLE_POT, SinoSeriesTabs.FUNCTIONAL_BLOCKS);
    public static final RegistryObject<Item> CHANGE_SOUP = simple(ChangeSoup.class, SinoSeriesTabs.AGRICULTURE);
    public static final RegistryObject<Item> LIFE_SYMBOL = simple(LifeSymbol.class, SinoSeriesTabs.TOOLS);
    public static final RegistryObject<SwordItem> STICK_COTINUS = sword("stick_cotinus", Tiers.WOOD, 3, -2.4F, 1);
    public static final RegistryObject<SwordItem> STICK_JUJUBE = sword("stick_jujube", Tiers.IRON, 3, -2.4f, 2);
    public static final RegistryObject<SwordItem> STICK_SOPHORA = sword("stick_sophora", Tiers.WOOD, 3, -2.4F, 2);
    public static final RegistryObject<BlockItem> BELLOWS = block(SDBlocks.BELLOWS, SinoSeriesTabs.FUNCTIONAL_BLOCKS);
    public static final RegistryObject<Item> MOXIBUSTION = simple(Moxibustion.class, SinoSeriesTabs.TOOLS);
    public static final RegistryObject<BlockItem> SILKWORM_PLAQUE = block(SDBlocks.SILKWORM_PLAQUE, SinoSeriesTabs.FUNCTIONAL_BLOCKS);
    public static final RegistryObject<Item> SILKWORM_BABY = single("silkworm_baby", 10, SinoSeriesTabs.AGRICULTURE);
    public static final RegistryObject<Hook> HOOK = simple(Hook.class, SinoSeriesTabs.TOOLS);
    public static final RegistryObject<Item> SILK = simple("silk", SinoSeriesTabs.MATERIALS);
    public static final RegistryObject<Item> STICK_RICE = simple("stick_rice", SinoSeriesTabs.AGRICULTURE);

    public static final RegistryObject<BlockItem> ALTAR = block(SDBlocks.ALTAR, SinoSeriesTabs.FUNCTIONAL_BLOCKS);
    public static final RegistryObject<BlockItem> TRIPOD = block(SDBlocks.TRIPOD, SinoSeriesTabs.FUNCTIONAL_BLOCKS);
    public static final RegistryObject<BlockItem> CARVING_TABLE = block(SDBlocks.CARVING_TABLE, SinoSeriesTabs.FUNCTIONAL_BLOCKS);

    public static final RegistryObject<Item> CANG_BI = simple("cang_bi", SinoSeriesTabs.MISC);
    public static final RegistryObject<Item> HUANG_CONG = simple("huang_cong", SinoSeriesTabs.MISC);
    public static final RegistryObject<Item> QING_GUI = simple("qing_gui", SinoSeriesTabs.MISC);
    public static final RegistryObject<Item> CHI_ZHANG = simple("chi_zhang", SinoSeriesTabs.MISC);
    public static final RegistryObject<Item> BAI_HU = simple("bai_hu", SinoSeriesTabs.MISC);
    public static final RegistryObject<Item> XUAN_HUANG = simple("xuan_huang", SinoSeriesTabs.MISC);

    public static final RegistryObject<Item> COPPER_GOBLET = simple("copper_goblet", SinoSeriesTabs.MISC);
    public static final RegistryObject<Item> COPPER_DAGGER_AXE = simple("copper_dagger_axe", SinoSeriesTabs.MISC);
    public static final RegistryObject<Item> COPPER_MIRROR = simple("copper_mirror", SinoSeriesTabs.MISC);
    public static final RegistryObject<Item> COPPER_MASK = simple("copper_mask", SinoSeriesTabs.MISC);
    public static final RegistryObject<Item> COPPER_LAMP = simple("copper_lamp", SinoSeriesTabs.MISC);
    public static final RegistryObject<Item> COPPER_BEAST = simple("copper_beast", SinoSeriesTabs.MISC);

    public static final RegistryObject<Item> REHMANNIA = food("rehmannia", 2);
    public static final RegistryObject<ItemNameBlockItem> SEED_REHMANNIA = seed(SDBlocks.REHMANNIA);
    public static final RegistryObject<Item> DRAGONLIVER_MELON = food("dragonliver_melon", 3);
    public static final RegistryObject<ItemNameBlockItem> SEED_DRAGONLIVER = seed(SDBlocks.DRAGONLIVER_MELON);
    public static final RegistryObject<BlockItem> LUCID_GANODERMA = block(SDBlocks.LUCID_GANODERMA, SinoSeriesTabs.AGRICULTURE);
    public static final RegistryObject<Item> ZHU_CAO = simple(SDBlocks.ZHU_CAO, SinoSeriesTabs.AGRICULTURE);
    public static final RegistryObject<Item> BRIGHT_STEM_GRASS = simple(SDBlocks.BRIGHT_STEM_GRASS, SinoSeriesTabs.AGRICULTURE);

    public static final RegistryObject<BaseChestItem> COTINUS_CHEST = chest(SDBlocks.COTINUS_CHEST, SDBlockEntities.COTINUS_CHEST);
    public static final RegistryObject<BaseChestItem> COTINUS_TRAPPED_CHEST = chest(SDBlocks.COTINUS_TRAPPED_CHEST, SDBlockEntities.COTINUS_TRAPPED_CHEST);
    public static final RegistryObject<BaseChestItem> SOPHORA_CHEST = chest(SDBlocks.SOPHORA_CHEST, SDBlockEntities.SOPHORA_CHEST);
    public static final RegistryObject<BaseChestItem> SOPHORA_TRAPPED_CHEST = chest(SDBlocks.SOPHORA_TRAPPED_CHEST, SDBlockEntities.SOPHORA_TRAPPED_CHEST);

    // =================================================================================================================

    public static RegistryObject<Item> simple(String name, RegistryObject<CreativeModeTab> tab) {
        return REGISTRY.register(name, () -> new Item(new Item.Properties()), tab);
    }

    public static RegistryObject<Item> single(String name, int durability, RegistryObject<CreativeModeTab> tab) {
        return REGISTRY.register(name, () -> new Item(new Item.Properties().durability(durability)), tab);
    }

    public static <T extends Block> RegistryObject<Item> simple(RegistryObject<T> block, RegistryObject<CreativeModeTab> tab) {
        return REGISTRY.register(block.getId().getPath(), () -> new SimpleBlockItem(block), tab);
    }

    public static <T extends Item> RegistryObject<T> simple(Class<? extends T> aClass, RegistryObject<CreativeModeTab> tab) {
        Supplier<T> sup;
        try {
            Function<Item.Properties, ? extends T> constructor = Functions.constructor(aClass, Item.Properties.class);
            sup = () -> constructor.apply(new Item.Properties());
        } catch (IllegalArgumentException e) {
            sup = Functions.constructor(aClass);
        }
        return REGISTRY.register(NameUtils.to_snake_name(aClass.getSimpleName()), sup, tab);
    }

    public static RegistryObject<SwordItem> sword(String name, Tier tier, int damageModifier,
                                                  float attackSpeedModifier, int durabilityModifier) {
        return REGISTRY.register(name, () -> new SwordItem(tier, damageModifier, attackSpeedModifier, new Item.Properties()
                .durability(tier.getUses() * durabilityModifier)), SinoSeriesTabs.WEAPONS, SinoSeriesTabs.TOOLS);
    }

    public static <T extends Block> RegistryObject<BlockItem> block(RegistryObject<T> block, RegistryObject<CreativeModeTab> tab) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()), tab);
    }

    public static RegistryObject<Item> food(String name, int nutrition) {
        return REGISTRY.register(name, () -> new Item(new Item.Properties()
                .food(new FoodProperties.Builder().nutrition(nutrition).build())), SinoSeriesTabs.AGRICULTURE);
    }

    public static <T extends Block> RegistryObject<ItemNameBlockItem> seed(RegistryObject<T> crop) {
        return REGISTRY.register("seed_" + crop.getId().getPath(), () -> new ItemNameBlockItem(crop.get(), new Item.Properties()));
    }

    public static RegistryObject<BaseChestItem> chest(RegistryObject<? extends Block> block, Supplier<? extends BlockEntityType<?>> be) {
        return REGISTRY.register(block.getId().getPath(), () -> BaseChestItem.create(block, be), SinoSeriesTabs.FUNCTIONAL_BLOCKS);
    }

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }
}
