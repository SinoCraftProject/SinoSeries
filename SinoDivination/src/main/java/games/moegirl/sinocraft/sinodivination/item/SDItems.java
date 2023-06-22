package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinocore.utility.NameUtils;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.util.ItemProperties;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class SDItems {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SinoDivination.MODID);

    public static final RegistryObject<BlockItem> KETTLE_POT = block(SDBlocks.KETTLE_POT, SinoSeriesTabs.FUNCTIONAL_BLOCKS);
    public static final RegistryObject<Item> CHANGE_SOUP = simple(ChangeSoup.class, SinoSeriesTabs.AGRICULTURE);
    public static final RegistryObject<Item> LIFE_SYMBOL = simple(LifeSymbol.class, SinoSeriesTabs.AGRICULTURE);
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

    // =================================================================================================================

    public static RegistryObject<Item> simple(String name, RegistryObject<CreativeModeTab> tab) {
        return REGISTRY.register(name, () -> new Item(new ItemProperties().tab(tab).properties()));
    }

    public static RegistryObject<Item> single(String name, int durability, RegistryObject<CreativeModeTab> tab) {
        return REGISTRY.register(name, () -> new Item(new ItemProperties().durability(durability).tab(tab).properties()));
    }

    public static <T extends Item> RegistryObject<T> simple(Class<? extends T> aClass, RegistryObject<CreativeModeTab> tab) {
        return REGISTRY.register(NameUtils.to_snake_name(aClass.getSimpleName()), defItem(aClass, tab));
    }

    public static RegistryObject<SwordItem> sword(String name, Tier tier, int damageModifier,
                                                  float attackSpeedModifier, int durabilityModifier) {
        return REGISTRY.register(name, () -> new SwordItem(tier, damageModifier, attackSpeedModifier, new ItemProperties()
                .durability(tier.getUses() * durabilityModifier)
                .tab(SinoSeriesTabs.WEAPONS)
                .tab(SinoSeriesTabs.TOOLS).properties()));
    }

    public static <T extends Block> RegistryObject<BlockItem> block(RegistryObject<T> block, RegistryObject<CreativeModeTab> tab) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new ItemProperties().tab(tab).properties()));
    }

    private static <T extends Item> Supplier<T> defItem(Class<? extends T> aClass, RegistryObject<CreativeModeTab> tab) {
        try {
            Function<Item.Properties, ? extends T> constructor = Functions.constructor(aClass, Item.Properties.class);
            return () -> constructor.apply(new ItemProperties().tab(tab).properties());
        } catch (IllegalArgumentException e) {
            Function<RegistryObject, ? extends T> constructor = Functions.constructor(aClass, RegistryObject.class);
            return () -> constructor.apply(tab);
        }
    }

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }
}
