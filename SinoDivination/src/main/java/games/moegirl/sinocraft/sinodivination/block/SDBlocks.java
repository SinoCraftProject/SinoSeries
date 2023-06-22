package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinocore.utility.NameUtils;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
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

    // =================================================================================================================

    public static <T extends Block> RegistryObject<T> simple(Class<? extends T> aClass) {
        return block(NameUtils.to_snake_name(aClass.getSimpleName()), Functions.constructor(aClass));
    }

    public static <T extends Block> RegistryObject<T> block(String name, Supplier<T> supplier) {
        return REGISTRY.register(name, supplier);
    }

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }
}
