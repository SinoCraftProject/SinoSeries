package games.moegirl.sinocraft.sinofeast.block;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SFBlockItems {
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFeast.MODID);

    public static void register(IEventBus bus) {
        BLOCK_ITEMS.register(bus);
    }

    
}
