package games.moegirl.sinocraft.sinofeast.item;

import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFeast.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);

        TabsRegistry.items(SinoSeriesTabs.AGRICULTURE)
                .addItem(TEA_SEED)
                .addItem(TEA_BUDS)
                .addItem(TEA_FRESH_LEAVES)
                .addItem(CRUSHED_TEA_FRESH_LEAVES);
    }

    public static final RegistryObject<Item> TEA_SEED = ITEMS.register("tea_seed", TeaSeedItem::new);
    public static final RegistryObject<Item> TEA_BUDS = ITEMS.register("tea_buds", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEA_FRESH_LEAVES = ITEMS.register("tea_fresh_leaves", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRUSHED_TEA_FRESH_LEAVES = ITEMS.register("crushed_tea_fresh_leaves", () -> new Item(new Item.Properties()));
}
