package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFDBlockItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFoundation.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

//    public static final RegistryObject<BlockItem> STOVE = ITEMS.register("stove", () -> new BlockItem(SFDBlocks.STOVE.get(), new Item.Properties().(SinoSeriesTab.FUNCTIONAL_BLOCKS)));

}
