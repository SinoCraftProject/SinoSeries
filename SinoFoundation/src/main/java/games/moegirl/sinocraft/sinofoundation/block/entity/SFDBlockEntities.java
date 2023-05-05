package games.moegirl.sinocraft.sinofoundation.block.entity;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFDBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SinoFoundation.MODID);

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }

    public static final RegistryObject<BlockEntityType<?>> STOVE = BLOCK_ENTITIES.register("stove",
            () -> BlockEntityType.Builder.of(StoveBlockEntity::new,
                    SFDBlocks.STOVE.get()).build(null));
}
