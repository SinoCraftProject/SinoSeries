package games.moegirl.sinocraft.sinofeast.block;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SFBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoFeast.MODID);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }


}
