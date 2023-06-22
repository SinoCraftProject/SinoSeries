package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinocore.utility.NameUtils;
import net.minecraft.world.food.FoodProperties;
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

    public static final RegistryObject<BlockItem> KETTLE_POT = block(SDBlocks.KETTLE_POT);
    public static final RegistryObject<Item> CHANGE_SOUP = simple(ChangeSoup.class);
    public static final RegistryObject<Item> LIFE_SYMBOL = simple(LifeSymbol.class);
    public static final RegistryObject<SwordItem> STICK_COTINUS = sword("stick_cotinus", Tiers.WOOD, 3, -2.4F, 1);
    public static final RegistryObject<SwordItem> STICK_JUJUBE = sword("stick_jujube", Tiers.IRON, 3, -2.4f, 2);
    public static final RegistryObject<SwordItem> STICK_SOPHORA = sword("stick_sophora", Tiers.WOOD, 3, -2.4F, 2);
    public static final RegistryObject<BlockItem> BELLOWS = block(SDBlocks.BELLOWS);
    public static final RegistryObject<Item> MOXIBUSTION = simple(Moxibustion.class);
    public static final RegistryObject<BlockItem> SILKWORM_PLAQUE = block(SDBlocks.SILKWORM_PLAQUE);
    public static final RegistryObject<Item> SILKWORM_BABY = single("silkworm_baby", 10);
    public static final RegistryObject<Hook> HOOK = simple(Hook.class);
    public static final RegistryObject<Item> SILK = simple("silk");
    public static final RegistryObject<Item> STICK_RICE = simple("stick_rice");

    public static final RegistryObject<BlockItem> ALTAR = block(SDBlocks.ALTAR);
    public static final RegistryObject<BlockItem> TRIPOD = block(SDBlocks.TRIPOD);
    public static final RegistryObject<BlockItem> CARVING_TABLE = block(SDBlocks.CARVING_TABLE);

    public static final RegistryObject<Item> CANG_BI = simple("cang_bi");
    public static final RegistryObject<Item> HUANG_CONG = simple("huang_cong");
    public static final RegistryObject<Item> QING_GUI = simple("qing_gui");
    public static final RegistryObject<Item> CHI_ZHANG = simple("chi_zhang");
    public static final RegistryObject<Item> BAI_HU = simple("bai_hu");
    public static final RegistryObject<Item> XUAN_HUANG = simple("xuan_huang");

    public static final RegistryObject<Item> COPPER_GOBLET = simple("copper_goblet");
    public static final RegistryObject<Item> COPPER_DAGGER_AXE = simple("copper_dagger_axe");
    public static final RegistryObject<Item> COPPER_MIRROR = simple("copper_mirror");
    public static final RegistryObject<Item> COPPER_MASK = simple("copper_mask");
    public static final RegistryObject<Item> COPPER_LAMP = simple("copper_lamp");
    public static final RegistryObject<Item> COPPER_BEAST = simple("copper_beast");

    // =================================================================================================================

    public static RegistryObject<Item> simple(String name) {
        return REGISTRY.register(name, () -> new Item(new Item.Properties()));
    }

    public static RegistryObject<Item> single(String name, int durability) {
        return REGISTRY.register(name, () -> new Item(new Item.Properties().durability(durability)));
    }

    public static <T extends Item> RegistryObject<T> simple(Class<? extends T> aClass) {
        return REGISTRY.register(NameUtils.to_snake_name(aClass.getSimpleName()), defItem(aClass));
    }

    public static RegistryObject<SwordItem> sword(String name, Tier tier, int damageModifier,
                                                  float attackSpeedModifier, int durabilityModifier) {
        return REGISTRY.register(name, () -> new SwordItem(tier, damageModifier, attackSpeedModifier, new Item.Properties()
                .durability(tier.getUses() * durabilityModifier)));
    }

    public static <T extends Block> RegistryObject<BlockItem> block(RegistryObject<T> block) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Item> Supplier<T> defItem(Class<? extends T> aClass) {
        try {
            Function<Item.Properties, ? extends T> constructor = Functions.constructor(aClass, Item.Properties.class);
            return () -> constructor.apply(new Item.Properties());
        } catch (IllegalArgumentException e) {
            return Functions.constructor(aClass);
        }
    }

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);

        // Todo: qyl27: tab registry.
    }
}
