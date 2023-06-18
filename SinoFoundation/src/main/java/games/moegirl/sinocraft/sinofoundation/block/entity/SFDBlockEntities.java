package games.moegirl.sinocraft.sinofoundation.block.entity;

import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;

public class SFDBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SinoFoundation.MODID);

    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> COTINUS_CHEST = be(CotinusChestEntity.class, SFDBlocks.COTINUS_CHEST);
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> COTINUS_TRAPPED_CHEST = be(CotinusTrappedChestEntity.class, SFDBlocks.COTINUS_TRAPPED_CHEST);
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> JUJUBE_CHEST = be(JujubeChestEntity.class, SFDBlocks.JUJUBE_CHEST);
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> JUJUBE_TRAPPED_CHEST = be(JujubeTrappedChestEntity.class, SFDBlocks.JUJUBE_TRAPPED_CHEST);
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> SOPHORA_CHEST = be(SophoraChestEntity.class, SFDBlocks.SOPHORA_CHEST);
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> SOPHORA_TRAPPED_CHEST = be(SophoraTrappedChestEntity.class, SFDBlocks.SOPHORA_TRAPPED_CHEST);

    public static final RegistryObject<BlockEntityType<?>> STOVE = BLOCK_ENTITIES.register("stove",
            () -> BlockEntityType.Builder.of(StoveBlockEntity::new,
                    SFDBlocks.STOVE.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }

    private static RegistryObject<BlockEntityType<? extends ChestBlockEntity>> be(Class<? extends ChestBlockEntity> entity, RegistryObject<? extends Block> block) {
        BiFunction<BlockPos, BlockState, ChestBlockEntity> constructor = Functions.constructor(entity, BlockPos.class, BlockState.class);
        return BLOCK_ENTITIES.register(block.getId().getPath(), () -> BlockEntityType.Builder.of(constructor::apply, block.get()).build(null));
    }
}
