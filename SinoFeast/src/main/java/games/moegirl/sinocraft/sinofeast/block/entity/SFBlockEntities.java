package games.moegirl.sinocraft.sinofeast.block.entity;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.block.SFBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SFBlockEntities {
    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SinoFeast.MODID);

    public static final Supplier<BlockEntityType<CoppingBoardBlockEntity>> COPPING_BOARD_BLOCK_ENTITY = BLOCK_ENTITIES.register("copping_board", () ->
            BlockEntityType
                    .Builder
                    .of(CoppingBoardBlockEntity::new,
                            SFBlocks.COPPING_BOARD_BLOCK.get()).build(null));


}
