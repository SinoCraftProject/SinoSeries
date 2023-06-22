package games.moegirl.sinocraft.sinofoundation.block.entity;

import com.mojang.datafixers.util.Function3;
import games.moegirl.sinocraft.sinocore.blockentity.SimpleChestBlockEntity;
import games.moegirl.sinocraft.sinocore.blockentity.SimpleTrappedChestBlockEntity;
import games.moegirl.sinocraft.sinocore.utility.Reference;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.block.entity.tree.CotinusChestEntity;
import games.moegirl.sinocraft.sinofoundation.block.entity.tree.CotinusTrappedChestEntity;
import games.moegirl.sinocraft.sinofoundation.block.entity.tree.SophoraChestEntity;
import games.moegirl.sinocraft.sinofoundation.block.entity.tree.SophoraTrappedChestEntity;
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

public class SFDBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SinoFoundation.MODID);

    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> COTINUS_CHEST = be(CotinusChestEntity::new, SFDBlocks.COTINUS_CHEST);
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> COTINUS_TRAPPED_CHEST = be(CotinusTrappedChestEntity::new, SFDBlocks.COTINUS_TRAPPED_CHEST);
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> JUJUBE_CHEST = be(SimpleChestBlockEntity::new, SFDBlocks.JUJUBE_CHEST);
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> JUJUBE_TRAPPED_CHEST = be(SimpleTrappedChestBlockEntity::new, SFDBlocks.JUJUBE_TRAPPED_CHEST);
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> SOPHORA_CHEST = be(SophoraChestEntity::new, SFDBlocks.SOPHORA_CHEST);
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> SOPHORA_TRAPPED_CHEST = be(SophoraTrappedChestEntity::new, SFDBlocks.SOPHORA_TRAPPED_CHEST);
    public static final RegistryObject<BlockEntityType<?>> STOVE = BLOCK_ENTITIES.register("stove",
            () -> BlockEntityType.Builder.of(StoveBlockEntity::new,
                    SFDBlocks.STOVE.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<? extends T>> be(Function3<BlockEntityType<?>, BlockPos, BlockState, T> factory, RegistryObject<? extends Block> block) {
        Reference<BlockEntityType<?>> type = new Reference<>();
        BlockEntityType.BlockEntitySupplier<T> f = (pos, bs) -> factory.apply(type.get(), pos, bs);
        return BLOCK_ENTITIES.register(block.getId().getPath(), () -> {
            BlockEntityType<T> entityType = BlockEntityType.Builder.of(f, block.get()).build(null);
            type.set(entityType);
            return entityType;
        });
    }
}
