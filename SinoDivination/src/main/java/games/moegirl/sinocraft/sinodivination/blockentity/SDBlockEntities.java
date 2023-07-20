package games.moegirl.sinocraft.sinodivination.blockentity;

import com.mojang.datafixers.util.Function3;
import games.moegirl.sinocraft.sinocore.utility.Reference;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SDBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SinoDivination.MODID);

    public static final RegistryObject<BlockEntityType<SilkwormPlaqueEntity>> SILKWORM_PLAQUE = simple(SilkwormPlaqueEntity::new, SDBlocks.SILKWORM_PLAQUE);

    public static final RegistryObject<BlockEntityType<TripodEntity>> TRIPOD = simple(TripodEntity::new, SDBlocks.TRIPOD);

    public static final RegistryObject<BlockEntityType<AltarEntity>> ALTAR = simple(AltarEntity::new, SDBlocks.ALTAR);

    public static final RegistryObject<BlockEntityType<KettlePotEntity>> KETTLE_POT = simple(KettlePotEntity::new, SDBlocks.KETTLE_POT);

    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> COTINUS_CHEST = chest(CotinusChestEntity::new, SDBlocks.COTINUS_CHEST);

    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> COTINUS_TRAPPED_CHEST = chest(CotinusTrappedChestEntity::new, SDBlocks.COTINUS_TRAPPED_CHEST);

    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> SOPHORA_CHEST = chest(SophoraChestEntity::new, SDBlocks.SOPHORA_CHEST);

    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> SOPHORA_TRAPPED_CHEST = chest(SophoraTrappedChestEntity::new, SDBlocks.SOPHORA_TRAPPED_CHEST);

    // =================================================================================================================

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> simple(Function3<BlockEntityType<?>, BlockPos, BlockState, T> factory, RegistryObject<? extends Block> block) {
        Reference<BlockEntityType<?>> type = new Reference<>();
        BlockEntityType.BlockEntitySupplier<T> f = (pos, bs) -> factory.apply(type.get(), pos, bs);
        return REGISTRY.register(block.getId().getPath(), () -> {
            BlockEntityType<T> entityType = BlockEntityType.Builder.of(f, block.get()).build(null);
            type.set(entityType);
            return entityType;
        });
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<? extends T>> chest(Function3<BlockEntityType<?>, BlockPos, BlockState, T> factory, RegistryObject<? extends Block> block) {
        Reference<BlockEntityType<?>> type = new Reference<>();
        BlockEntityType.BlockEntitySupplier<T> f = (pos, bs) -> factory.apply(type.get(), pos, bs);
        return REGISTRY.register(block.getId().getPath(), () -> {
            BlockEntityType<T> entityType = BlockEntityType.Builder.of(f, block.get()).build(null);
            type.set(entityType);
            return entityType;
        });
    }

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }
}
