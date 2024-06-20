package games.moegirl.sinocraft.sinodeco.block.item;

import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.item.SDItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class SDBlockItems {
    public static final IRegistry<Item> ITEMS = RegistryManager.obtain(SinoDeco.MODID, Registries.ITEM);

    public static void register() {
        ITEMS.register();
    }

    public static final Supplier<Item> WOODEN_TABLE = ITEMS.register("wooden_table", () -> new BlockItem(SDBlocks.WOODEN_TABLE.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));

    public static final Supplier<Item> MARBLE_BLOCK = ITEMS.register("marble_block", () -> new BlockItem(SDBlocks.MARBLE_BLOCK.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
    public static final Supplier<Item> CHISELED_MARBLE = ITEMS.register("chiseled_marble", () -> new BlockItem(SDBlocks.CHISELED_MARBLE.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
    public static final Supplier<Item> MARBLE_PILLAR = ITEMS.register("marble_pillar", () -> new BlockItem(SDBlocks.MARBLE_PILLAR.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
    public static final Supplier<Item> MARBLE_STAIRS = ITEMS.register("marble_stairs", () -> new BlockItem(SDBlocks.MARBLE_STAIRS.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
    public static final Supplier<Item> MARBLE_SLAB = ITEMS.register("marble_slab", () -> new BlockItem(SDBlocks.MARBLE_SLAB.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
    public static final Supplier<Item> SMOOTH_MARBLE = ITEMS.register("smooth_marble", () -> new BlockItem(SDBlocks.SMOOTH_MARBLE.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
    public static final Supplier<Item> SMOOTH_MARBLE_STAIRS = ITEMS.register("smooth_marble_stairs", () -> new BlockItem(SDBlocks.SMOOTH_MARBLE_STAIRS.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
    public static final Supplier<Item> SMOOTH_MARBLE_SLAB = ITEMS.register("smooth_marble_slab", () -> new BlockItem(SDBlocks.SMOOTH_MARBLE_SLAB.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
    public static final Supplier<Item> MARBLE_BRICKS = ITEMS.register("marble_bricks", () -> new BlockItem(SDBlocks.MARBLE_BRICKS.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
    public static final Supplier<Item> MARBLE_BALUSTRADE = ITEMS.register("marble_balustrade", () -> new BlockItem(SDBlocks.MARBLE_BALUSTRADE.get(), new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB)));
}
