package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.block.SimpleCropBlock;
import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinocore.utility.NameUtils;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SDBlocks {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoDivination.MODID);

    public static final RegistryObject<KettlePot> KETTLE_POT = simple(KettlePot.class);
    public static final RegistryObject<Bellows> BELLOWS = simple(Bellows.class);
    public static final RegistryObject<SilkwormPlaque> SILKWORM_PLAQUE = simple(SilkwormPlaque.class);
    public static final RegistryObject<Tripod> TRIPOD = simple(Tripod.class);
    public static final RegistryObject<Altar> ALTAR = simple(Altar.class);
    public static final RegistryObject<CarvingTable> CARVING_TABLE = simple(CarvingTable.class);
    public static final RegistryObject<LucidGanoderma> LUCID_GANODERMA = block(LucidGanoderma.class);
    public static final RegistryObject<SimpleCropBlock<Item>> REHMANNIA = cropRehmannia();
    public static final RegistryObject<SimpleCropBlock<Item>> DRAGONLIVER_MELON = cropDragonliverMelon();
    public static final RegistryObject<ZhuCao> ZHU_CAO = block(ZhuCao.class);
    public static final RegistryObject<BrightStemGrass> BRIGHT_STEM_GRASS = block(BrightStemGrass.class);
    public static final RegistryObject<CotinusChest> COTINUS_CHEST = block(CotinusChest.class);
    public static final RegistryObject<CotinusTrappedChest> COTINUS_TRAPPED_CHEST = block(CotinusTrappedChest.class);
    public static final RegistryObject<SophoraChest> SOPHORA_CHEST = block(SophoraChest.class);
    public static final RegistryObject<SophoraTrappedChest> SOPHORA_TRAPPED_CHEST = block(SophoraTrappedChest.class);

    // =================================================================================================================

    public static <T extends Block> RegistryObject<T> simple(Class<? extends T> aClass) {
        return block(NameUtils.to_snake_name(aClass.getSimpleName()), Functions.constructor(aClass));
    }

    private static <T extends Block> RegistryObject<T> block(Class<T> blockClass) {
        return REGISTRY.register(NameUtils.to_snake_name(blockClass.getSimpleName()), Functions.constructor(blockClass));
    }

    public static <T extends Block> RegistryObject<T> block(String name, Supplier<T> supplier) {
        return REGISTRY.register(name, supplier);
    }

    private static RegistryObject<SimpleCropBlock<Item>> cropDragonliverMelon() {
        return block("dragonliver_melon", () -> new SimpleCropBlock<>(() -> SDItems.DRAGONLIVER_MELON, 7, 0, 1, 1, 1));
    }

    private static RegistryObject<SimpleCropBlock<Item>> cropRehmannia() {
        return block("rehmannia", () -> new SimpleCropBlock<>(() -> SDItems.REHMANNIA, 3, 0, 1, 1, 1));
    }

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }
}
