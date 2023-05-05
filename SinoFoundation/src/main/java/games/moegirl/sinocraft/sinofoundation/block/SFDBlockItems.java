package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.item.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
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
        TabsRegistry.get(SinoSeriesTabs.FUNCTIONAL_BLOCKS).add(ITEMS);
    }

    public static final RegistryObject<BlockItem> STOVE = ITEMS.register("stove", () -> new BlockItem(SFDBlocks.STOVE.get(), new Item.Properties()));

}
