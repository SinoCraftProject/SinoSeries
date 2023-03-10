package games.moegirl.sinocraft.sinocore.old.item;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoCore.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
