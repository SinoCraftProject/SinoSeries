package games.moegirl.sinocraft.sinodivination.blockentity;

import com.mojang.datafixers.util.Function3;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SDBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SinoDivination.MODID);

    public static final RegistryObject<BlockEntityType<SilkwormPlaqueEntity>> SILKWORM_PLAQUE = simple(SilkwormPlaqueEntity::new, SDBlocks.SILKWORM_PLAQUE);

    public static final RegistryObject<BlockEntityType<TripodEntity>> TRIPOD = simple(TripodEntity::new, SDBlocks.TRIPOD);

    public static final RegistryObject<BlockEntityType<AltarEntity>> ALTAR = simple(AltarEntity::new, SDBlocks.ALTAR);

    public static final RegistryObject<BlockEntityType<KettlePotEntity>> KETTLE_POT = simple(KettlePotEntity::new, SDBlocks.KETTLE_POT);

    // =================================================================================================================

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> simple(Function3<BlockEntityType<?>, BlockPos, BlockState, T> supplier, RegistryObject<? extends Block> block) {
        return entity(block.getId().getPath() + "_entity", supplier, block);
    }

    @SafeVarargs
    @SuppressWarnings({"ConstantConditions"})
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> entity(String name, Function3<BlockEntityType<?>, BlockPos, BlockState, T> supplier, Supplier<? extends Block>... blocks) {
        Holder<T> holder = new Holder<>();
        holder.type = REGISTRY.register(name, () -> {
            Block[] bs = new Block[blocks.length];
            for (int i = 0; i < blocks.length; i++) {
                bs[i] = blocks[i].get();
            }
            return BlockEntityType.Builder.of((pos, state) ->
                    supplier.apply(holder.type.get(), pos, state), bs).build(null);
        });
        return holder.type;
    }

    static class Holder<T extends BlockEntity> {
        @SuppressWarnings("NotNullFieldNotInitialized")
        RegistryObject<BlockEntityType<T>> type;
    }

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }
}
