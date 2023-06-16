package games.moegirl.sinocraft.sinocalligraphy.block;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCABlockItems {
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoCalligraphy.MODID);

    public static void register(IEventBus bus) {
        BLOCK_ITEMS.register(bus);

        TabsRegistry.items(SinoSeriesTabs.FUNCTIONAL_BLOCKS).addItem(PAPER_DRYING_RACK);
    }

    public static final RegistryObject<BlockItem> PAPER_DRYING_RACK = BLOCK_ITEMS.register("paper_drying_rack", () -> new BlockItem(SCABlocks.PAPER_DRYING_RACK.get(), new Item.Properties()));
}
