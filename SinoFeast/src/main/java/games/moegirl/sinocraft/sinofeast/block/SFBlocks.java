package games.moegirl.sinocraft.sinofeast.block;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoFeast.MODID);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static final RegistryObject<Block> TEA_TREE_BLOCK = BLOCKS.register("tea_tree", TeaTreeBlock::new);

    public static final RegistryObject<Block> COPPING_BOARD_BLOCK = BLOCKS.register("copping_board", CoppingBoardBlock::new);
}
