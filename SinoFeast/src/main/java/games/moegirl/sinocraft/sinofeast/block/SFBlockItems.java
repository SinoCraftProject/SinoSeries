package games.moegirl.sinocraft.sinofeast.block;

import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFBlockItems {
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFeast.MODID);

    public static void register(IEventBus bus) {
        BLOCK_ITEMS.register(bus);

        TabsRegistry.items(SinoSeriesTabs.AGRICULTURE)
                .addItem(TEA_TREE);
    }

    public static final RegistryObject<BlockItem> TEA_TREE = BLOCK_ITEMS.register("tea_tree", () -> new ItemNameBlockItem(SFBlocks.TEA_TREE_BLOCK.get(), new Item.Properties()));
}
