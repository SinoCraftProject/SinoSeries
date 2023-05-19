package games.moegirl.sinocraft.sinofoundation.item;

import games.moegirl.sinocraft.sinocore.item.tab.TabsRegistry;
import games.moegirl.sinocraft.sinocore.event.BlockStrippingEvent;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFDItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFoundation.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        TabsRegistry.get(SinoSeriesTabs.MISC).add(ITEMS);
    }

    public static final RegistryObject<Item> IRON_KNIFE = ITEMS.register("iron_knife", () -> new KnifeItem(Tiers.IRON));
    public static final RegistryObject<Item> GOLD_KNIFE = ITEMS.register("gold_knife", () -> new KnifeItem(Tiers.GOLD));
    public static final RegistryObject<Item> DIAMOND_KNIFE = ITEMS.register("diamond_knife", () -> new KnifeItem(Tiers.DIAMOND));

    public static final RegistryObject<Item> ASHES = ITEMS.register("ashes", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TREE_BARK = ITEMS.register("tree_bark", () -> new Item(new Item.Properties()));

    static {
        for (var entry : BlockStrippingEvent.getBlockStrippingMap().entrySet()) {
            BlockStrippingEvent.registerStripping(entry.getKey(), entry.getValue().getA(), SFDItems.TREE_BARK::get);
        }
    }
}
