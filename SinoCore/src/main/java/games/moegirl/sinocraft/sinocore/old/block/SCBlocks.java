package games.moegirl.sinocraft.sinocore.old.block;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.Block;

public class SCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoCore.MODID);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
