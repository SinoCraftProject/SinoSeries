package games.moegirl.sinocraft.sinocore.old.block;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.item.Item;

public class SCBlockItems {
    public static final DeferredRegister<Item> BLOCK_ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, SinoCore.MODID);

    public static void register(IEventBus bus) {
        BLOCK_ITEM.register(bus);
    }
}
