package games.moegirl.sinocraft.sinocore.old.block.blockentity;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SCBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SinoCore.MODID);

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
