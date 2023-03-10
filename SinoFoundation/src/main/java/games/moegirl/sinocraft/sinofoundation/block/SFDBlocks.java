package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFDBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoFoundation.MODID);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

//    public static final RegistryObject<Block> STOVE = BLOCKS.register("stove", StoveBlock::new);

}
