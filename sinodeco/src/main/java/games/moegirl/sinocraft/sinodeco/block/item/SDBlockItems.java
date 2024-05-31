package games.moegirl.sinocraft.sinodeco.block.item;

import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.item.SDItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class SDBlockItems {
    public static IRegistry<Item> ITEMS = RegistryManager.obtain(SinoDeco.MODID, Registries.ITEM);

    public static void register() {
        ITEMS.register();
    }

    public static final Supplier<Item> WOOD_DESK = ITEMS.register("wood_desk", () -> new BlockItem(SDBlocks.WOOD_DESK.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
}
